package com.fttx.partner.ui.screen.backgroundlocation

// LocationService.kt
import android.app.*
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import java.util.Calendar
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class LocationService : Service() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())
    private var isTracking = false
    private val scheduler = Executors.newSingleThreadScheduledExecutor()

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
            ACTION_START_TRACKING -> startTracking()
            ACTION_STOP_TRACKING -> stopTracking()
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
            interval = TimeUnit.MINUTES.toMillis(1) // Update every hour
            fastestInterval = TimeUnit.SECONDS.toMillis(55) // Fastest update interval
            priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        }

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            // Handle permission error
        }
        checkTime()
    }

    private fun checkTime() {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)

        Log.e("Test","check time $currentHour $currentMinute")

        if (currentHour == 21 && currentMinute == 0) { // 9:00 PM
            stopTracking()
        }
    }

    private fun stopTracking() {
        isTracking = false
        fusedLocationClient.removeLocationUpdates(locationCallback)
        stopForeground(true)
        stopSelf()
    }

    private suspend fun sendLocationToBackend(location: Location) {
        // Implementation for sending location to your backend
        // Example using Retrofit:
        try {
            val locationData = LocationData(
                latitude = location.latitude,
                longitude = location.longitude,
                timestamp = System.currentTimeMillis()
            )
            // api.updateLocation(locationData)
            Log.e(
                "Test",
                "Location ${locationData.latitude} ${locationData.longitude} ${locationData.timestamp}"
            )
        } catch (e: Exception) {
            // Handle error, maybe store locally for retry
            Log.e("Test", "Error")
        }
    }

    private fun createNotificationChannel() {
        Log.e("Test","createNotificationChannel")
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Location Service Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        Log.e("Test","createNotification")
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Location Tracking Active")
            .setContentText("Tracking your location for attendance")
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setOngoing(true)
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
    }
}

// LocationData.kt
data class LocationData(
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long
)