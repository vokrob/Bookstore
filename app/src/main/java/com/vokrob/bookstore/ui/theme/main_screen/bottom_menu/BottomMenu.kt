package com.vokrob.bookstore.ui.theme.main_screen.bottom_menu

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource

@Composable
fun BottomMenu() {
    val items = listOf(
        BottomMenuItem.Home,
        BottomMenuItem.Favs,
        BottomMenuItem.Settings
    )

    val selectedItem = remember { mutableStateOf("Home") }

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem.value == item.title,
                onClick = { selectedItem.value = item.title },
                icon = {
                    Icon(
                        painter = painterResource(item.iconId),
                        contentDescription = null
                    )
                },
                label = { Text(item.title) }
            )
        }
    }
}




























