package com.vokrob.bookstore.ui.theme.main_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vokrob.bookstore.ui.theme.login.data.MainScreenDataObject
import com.vokrob.bookstore.ui.theme.main_screen.bottom_menu.BottomMenu

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(navData: MainScreenDataObject) {
    val drawerState = rememberDrawerState(DrawerValue.Open)

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth(),
        drawerContent = {
            Column(modifier = Modifier.fillMaxWidth(0.7f)) {
                DrawerHeader(navData.email)
                DrawerBody()
            }
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomMenu() }
        ) { }
    }
}




























