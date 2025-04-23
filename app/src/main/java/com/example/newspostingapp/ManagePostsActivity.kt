package com.example.newspostingapp

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log.d
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.jvm.java

class ManagePostsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManagePostsScreen()
        }
    }
}


@Composable
fun ManagePostsScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity

    val userEmail = NewsPostingData.readMail(context)
    var newsList by remember { mutableStateOf(listOf<NewsData>()) }
    var loadNews by remember { mutableStateOf(true) }

    LaunchedEffect(userEmail) {
        getNewsDetails() { orders ->
            newsList = orders
            loadNews = false
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.main_color))
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        context.finish()
                    },
                painter = painterResource(id = R.drawable.back_button),
                contentDescription = "Arrow Back"
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = "Manage Posts",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {


            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(newsList.size) { index ->
                    NewsItem(newsList[index])
                }
            }
        }
    }
}

@Composable
fun NewsItem(newsData: NewsData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Column() {

                Image(
                    bitmap = decodeBase64ToBitmap(newsData.imageUrl)!!.asImageBitmap(),
                    contentDescription = "News Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(200.dp),
                    contentScale = ContentScale.FillBounds
                )

            Column(modifier = Modifier
                .padding(12.dp)
            ) {

                Text(
                    text = "Title : ${newsData.newsTitle}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = "Category : ${newsData.newsCategory}", fontSize = 16.sp)
                Text(text = "Content : ${newsData.newsContent}", fontSize = 16.sp)
                Text(text = "Location : ${newsData.newsLocation}", fontSize = 16.sp)
            }
            }
        }

}

fun getNewsDetails(callback: (List<NewsData>) -> Unit) {
    val databaseReference = FirebaseDatabase.getInstance().getReference("PostedNews")

    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val newsList = mutableListOf<NewsData>()

            for (newsSnapshot in snapshot.children) {
                for (newsDataSnapshot in newsSnapshot.children) {
                    val news = newsDataSnapshot.getValue(NewsData::class.java)
                    news?.let { newsList.add(it) }
                }
            }

            callback(newsList)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error: ${error.message}")
            callback(emptyList())
        }
    })
}


fun decodeBase64ToBitmap(base64String: String): Bitmap? {
    val decodedString = Base64.decode(base64String, Base64.DEFAULT)
    val originalBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
    return originalBitmap
}


