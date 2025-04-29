package mobileapp.newsposting.s3351728sagarbonthu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
    val context = LocalContext.current as Activity
    val userEmail = NewsPostPrefs.getReporterEmail(context)

    var newsList by remember { mutableStateOf(listOf<NewsData>()) }
    var loadNews by remember { mutableStateOf(true) }


    LaunchedEffect(userEmail) {
        getNewsDetails(userEmail) { news ->
            newsList = news
            loadNews = false
        }
    }

    Column(modifier = Modifier.fillMaxSize() .padding(WindowInsets.systemBars.asPaddingValues())) {
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
                    .clickable { context.finish() },
                painter = painterResource(id = R.drawable.back_button),
                contentDescription = "Arrow Back"
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
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

            if (loadNews) {
                LinearProgressIndicator()
            } else {
                if (newsList.isNotEmpty()) {
                    LazyColumn {
                        items(newsList) { news ->
                            NewsItem(news,
                                onDeleteClick = {
                                    deletePost(news.newsId, userEmail) {
                                        // Refresh the list after delete
                                        getNewsDetails(userEmail) { newsList = it }
                                    }
                                },
                                onUpdateClick = {
                                    context.startActivity(
                                        Intent(
                                            context,
                                            UpdatePostActivity::class.java
                                        )
                                    )

                                }
                            )
                        }
                    }
                } else {
                    Text(text = "No News Posted")
                }
            }
        }
    }
}

@Composable
fun NewsItem(newsData: NewsData, onDeleteClick: () -> Unit, onUpdateClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {

            Image(
                painter = rememberAsyncImagePainter(newsData.imageUrl),
                contentDescription = "News Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "Title: ${newsData.newsTitle}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Category: ${newsData.newsCategory}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = newsData.newsContent,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            SelectedNewsArticle.newsData = newsData
                            onUpdateClick()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_color))
                    ) {
                        Text(text = "Update", color = Color.White)
                    }

                    Button(
                        onClick = { onDeleteClick() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text(text = "Delete", color = Color.White)
                    }
                }
            }
        }
    }
}

fun getNewsDetails(userMail: String, callback: (List<NewsData>) -> Unit) {

    val emailKey = userMail.replace(".", ",")
    val databaseReference = FirebaseDatabase.getInstance().getReference("NewsPosts/$emailKey")

    databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val newsList = mutableListOf<NewsData>()
            for (newsSnapshot in snapshot.children) {
                val news = newsSnapshot.getValue(NewsData::class.java)
                news?.let { newsList.add(it) }
            }
            callback(newsList)
        }

        override fun onCancelled(error: DatabaseError) {
            println("Error: ${error.message}")
            callback(emptyList())
        }
    })
}

fun deletePost(newsId: String, userMail: String, callback: () -> Unit) {
    val emailKey = userMail.replace(".", ",")
    val databaseReference = FirebaseDatabase.getInstance().getReference("NewsPosts/$emailKey")

    databaseReference.child(newsId)
        .removeValue()
        .addOnSuccessListener {
            callback()
        }
        .addOnFailureListener {
            Log.e("Firebase", "Delete failed: ${it.message}")
        }
}

fun updatePost(newsData: NewsData, userMail: String, callback: () -> Unit) {

    val emailKey = userMail.replace(".", ",")
    val databaseReference = FirebaseDatabase.getInstance().getReference("NewsPosts/$emailKey")

    databaseReference.child(newsData.newsId)
        .setValue(newsData)
        .addOnSuccessListener {
            callback()
        }
        .addOnFailureListener {
            Log.e("Firebase", "Update failed: ${it.message}")
        }
}




