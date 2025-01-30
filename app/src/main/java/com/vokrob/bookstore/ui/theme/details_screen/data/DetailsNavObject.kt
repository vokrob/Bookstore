package com.vokrob.bookstore.ui.theme.details_screen.data

import kotlinx.serialization.Serializable

@Serializable
data class DetailsNavObject(
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val category: String = "",
    val imageUrl: String = "",
)
