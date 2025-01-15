package com.vokrob.bookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vokrob.bookstore.ui.theme.BookstoreTheme
import com.vokrob.bookstore.ui.theme.login.LoginScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            BookstoreTheme {
                LoginScreen()
            }
        }
    }
}

























