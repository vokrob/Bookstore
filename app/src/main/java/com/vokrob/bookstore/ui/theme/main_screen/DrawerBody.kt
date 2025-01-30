package com.vokrob.bookstore.ui.theme.main_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.vokrob.bookstore.R
import com.vokrob.bookstore.ui.theme.ButtonColorBlue
import com.vokrob.bookstore.ui.theme.DarkTransparentBlue
import com.vokrob.bookstore.ui.theme.GrayLight

@Composable
fun DrawerBody(
    onAdmin: (Boolean) -> Unit = { },
    onAdminClick: () -> Unit = { },
    onFavClick: () -> Unit = { },
    onCategoryClick: (String) -> Unit = { }
) {
    val categoriesList = listOf(
        "Favorites",
        "Fantasy",
        "Drama",
        "Bestsellers"
    )

    val isAdminState = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isAdmin { isAdmin ->
            isAdminState.value = isAdmin
            onAdmin(isAdmin)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ButtonColorBlue)
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.background),
            contentDescription = "",
            alpha = 0.2f,
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Categories",
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(GrayLight)
            )

            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(categoriesList) { item ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (categoriesList[0] == item) onFavClick()
                                else onCategoryClick(item)
                            }
                    ) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = item,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(GrayLight)
                        )
                    }
                }
            }

            if (isAdminState.value) {
                Button(
                    onClick = { onAdminClick() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DarkTransparentBlue)
                ) {
                    Text("Admin panel")
                }
            }
        }
    }
}

fun isAdmin(onAdmin: (Boolean) -> Unit) {
    val uid = Firebase.auth.currentUser!!.uid
    Firebase.firestore.collection("admin").document(uid).get().addOnSuccessListener {
        onAdmin(it.get("isAdmin") as Boolean)
    }
}


























