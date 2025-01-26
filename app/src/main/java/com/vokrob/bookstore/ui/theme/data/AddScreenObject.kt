package com.vokrob.bookstore.ui.theme.data

import kotlinx.serialization.Serializable

@Serializable
data class AddScreenObject(
    val key: String = "",
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val imageUrl: String = ""
)