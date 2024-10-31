package com.fttx.partner.ui.screen.account

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.fttx.partner.domain.model.User
import com.fttx.partner.ui.screen.login.LoginActivity
import com.fttx.partner.ui.utils.Constants.BundleKey.USER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val user = intent.getParcelableExtra<User>(USER) ?: User(-1, "", "", "")
        setContent {
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                AccountRoute(
                    user = user,
                    onLogout = {
                        startActivity(Intent(this, LoginActivity::class.java).apply {
                            addFlags(
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            )
                        })
                    },
                    onBackPress = { finish() })
            }
        }
    }
}