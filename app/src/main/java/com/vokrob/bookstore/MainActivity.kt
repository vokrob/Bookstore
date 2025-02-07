package com.vokrob.bookstore

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.vokrob.bookstore.ui.theme.BookstoreTheme
import com.vokrob.bookstore.ui.theme.add_book_screen.AddBookScreen
import com.vokrob.bookstore.ui.theme.data.AddScreenObject
import com.vokrob.bookstore.ui.theme.details_screen.data.DetailsNavObject
import com.vokrob.bookstore.ui.theme.details_screen.ui.DetailsScreen
import com.vokrob.bookstore.ui.theme.login.LoginScreen
import com.vokrob.bookstore.ui.theme.login.data.LoginScreenObject
import com.vokrob.bookstore.ui.theme.login.data.MainScreenDataObject
import com.vokrob.bookstore.ui.theme.main_screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        setContent {
            BookstoreTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = LoginScreenObject
                ) {
                    composable<LoginScreenObject> {
                        LoginScreen { navData -> navController.navigate(navData) }
                    }

                    composable<MainScreenDataObject> { navEntry ->
                        val navData = navEntry.toRoute<MainScreenDataObject>()

                        MainScreen(
                            navData,
                            onBookClick = { bk ->
                                navController.navigate(
                                    DetailsNavObject(
                                        title = bk.title,
                                        description = bk.description,
                                        price = bk.price,
                                        category = bk.category,
                                        imageUrl = bk.imageUrl
                                    )
                                )
                            },
                            onBookEditClick = { book ->
                                navController.navigate(
                                    AddScreenObject(
                                        key = book.key,
                                        title = book.title,
                                        description = book.description,
                                        price = book.price,
                                        category = book.category,
                                        imageUrl = book.imageUrl
                                    )
                                )
                            }
                        ) { navController.navigate(AddScreenObject()) }
                    }

                    composable<AddScreenObject> { navEntry ->
                        val navData = navEntry.toRoute<AddScreenObject>()
                        AddBookScreen(navData) { navController.popBackStack() }
                    }

                    composable<DetailsNavObject> { navEntry ->
                        val navData = navEntry.toRoute<DetailsNavObject>()
                        DetailsScreen(navData)
                    }
                }
            }
        }
    }
}
























