package com.vokrob.bookstore.ui.theme.main_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vokrob.bookstore.R

@Composable
fun BookListItemUi() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = R.drawable.cover,
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "History of hedgehogs",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "Decsription",
            color = Color.Gray,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(
            text = "50$",
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}


























