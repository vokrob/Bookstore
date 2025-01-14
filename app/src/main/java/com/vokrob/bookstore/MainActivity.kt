package com.vokrob.bookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vokrob.bookstore.ui.theme.BookstoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fs = Firebase.firestore
        fs.collection("books").document().set(mapOf("name" to "My fav book"))

        enableEdgeToEdge()

        setContent {
            BookstoreTheme {

            }
        }
    }
}


























