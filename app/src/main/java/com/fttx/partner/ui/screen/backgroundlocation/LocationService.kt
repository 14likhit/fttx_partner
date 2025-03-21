package com.fttx.partner.ui.screen.backgroundlocation

// LocationService.kt
import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.fttx.partner.data.network.util.SemaaiResult
import com.fttx.partner.data.source.local.datastore.DataStorePreferences
import com.fttx.partner.domain.model.Action
import com.fttx.partner.domain.usecase.location.UpdateLocationUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())
    private var isTracking = false

    @Inject
    lateinit var dataStorePreferences: DataStorePreferences

    @Inject
    lateinit var updateLocationUseCase: UpdateLocationUseCase

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationCallback()
        createNotificationChannel()
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    serviceScope.launch {
                        sendLocationToBackend(location)
                    }
                }
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START_TRACKING -> {
                startTracking()
            }

            ACTION_STOP_TRACKING -> {
                stopTracking()
            }

            ACTION_UPDATE_TRACKING -> {
                startTracking()
            }
        }
        return START_STICKY
    }

    private fun startTracking() {
        if (isTracking) return
        isTracking = true
        startForeground(NOTIFICATION_ID, createNotification())
        requestLocationUpdates()
    }

    private fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.HOURS.toMillis(1) // Update every hour
            fastestInterval = TimeUnit.MINUTES.toMillis(55) // Fastest update interval
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            // Handle permission error
            e.printStackTrace()
        }
        checkTime()
    }

    private fun checkTime() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        if (currentHour == 21 && currentMinute == 0) { // 9:00 PM
            stopTracking()
        }
    }

    private fun stopTracking() {
        isTracking = false
        serviceScope.launch {
            try {
                // Get the last known location
                val lastLocation = getLastKnownLocation()
                if (lastLocation != null) {
                    sendLocationToBackend(lastLocation, isCheckout = true)
                } else {
                    sendLocationToBackend(Location("provider").apply {
                        latitude = 0.0
                        longitude = 0.0
                    }, isCheckout = true)
                }
                withContext(Dispatchers.Main) {
                    fusedLocationClient.removeLocationUpdates(locationCallback)
                    stopForeground(true)
                    stopSelf()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    fusedLocationClient.removeLocationUpdates(locationCallback)
                    stopForeground(true)
                    stopSelf()
                }
            }
        }
    }

    private suspend fun getLastKnownLocation(): Location? {
        return suspendCancellableCoroutine { continuation ->
            try {
                if (ActivityCompat.checkSelfPermission(
                        this, Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    fusedLocationClient.lastLocation
                        .addOnSuccessListener { location ->
                            continuation.resume(location) {}
                        }
                        .addOnFailureListener { exception ->
                            continuation.resume(null) {}
                            exception.printStackTrace()
                        }
                } else {
                    continuation.resume(null) {}
                }
            } catch (e: Exception) {
                continuation.resume(null) {}
                e.printStackTrace()
            }
        }
    }

    private suspend fun sendLocationToBackend(location: Location, isCheckout: Boolean = false) {
        // Implementation for sending location to your backend
        // Example using Retrofit:
        try {
            val locationData = LocationData(
                latitude = location.latitude,
                longitude = location.longitude,
                timestamp = System.currentTimeMillis()
            )

            val result = updateLocationUseCase(
                dataStorePreferences.getUserId() ?: 0,
                locationData.latitude to locationData.longitude,
                if (dataStorePreferences.isUserCheckedIn()
                        .not()
                ) Action.CheckIn else if (isCheckout) Action.CheckOut else Action.LocationUpdate
            )
            when (result) {
                is SemaaiResult.Error -> {
                    Log.e("Test","Error Sending BroadCast")
                    val broadcastIntent = Intent(BROADCAST_TRACKING_UPDATES)
                    LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
                }
                is SemaaiResult.Success -> {
                    Log.e("Test","Sending Success")
                    if (isCheckout) {
                        Log.e("Test","Sending CheckoutBroadcast")
                        dataStorePreferences.saveUserCheckedIn(false)
                        val broadcastIntent = Intent(BROADCAST_TRACKING_STOPPED)
                        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
                        dataStorePreferences.saveCheckedInTimeStamp(0)
                    } else if (!dataStorePreferences.isUserCheckedIn()) {
                        Log.e("Test","Sending CheckInBroadcast")
                        dataStorePreferences.saveUserCheckedIn(true)
                        val broadcastIntent = Intent(BROADCAST_TRACKING_STARTED)
                        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
                        dataStorePreferences.saveCheckedInTimeStamp(System.currentTimeMillis())
                    } else {
                        Log.e("Test","Sending UpdateBroadcast")
                        val broadcastIntent = Intent(BROADCAST_TRACKING_UPDATES)
                        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent)
                    }
                }
            }
        } catch (e: Exception) {
            // Handle error, maybe store locally for retry
            Log.e("LocationService", "Error sending location to backend", e)
            e.printStackTrace()
        }
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Location Service Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Location Tracking Active")
            .setContentText("Tracking your location for attendance")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val CHANNEL_ID = "location_service_channel"
        const val NOTIFICATION_ID = 1
        const val ACTION_START_TRACKING = "start_tracking"
        const val ACTION_STOP_TRACKING = "stop_tracking"
        const val ACTION_UPDATE_TRACKING = "update_tracking"
        const val BROADCAST_TRACKING_STARTED = "com.fttx.partner.TRACKING_STARTED"
        const val BROADCAST_TRACKING_STOPPED = "com.fttx.partner.TRACKING_STOPPED"
        const val BROADCAST_TRACKING_UPDATES = "com.fttx.partner.TRACKING_UPDATES"
    }
}

// LocationData.kt
data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long
)