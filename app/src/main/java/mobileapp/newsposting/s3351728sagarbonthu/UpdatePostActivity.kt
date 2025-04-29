package mobileapp.newsposting.s3351728sagarbonthu

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

class UpdatePostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UpdatePostScreen(newsData = SelectedNewsArticle.newsData)
        }
    }
}

object SelectedNewsArticle {
    lateinit var newsData: NewsData
}

@Composable
fun UpdatePostScreen(newsData: NewsData) {
    val context = LocalContext.current as Activity

    var updatedTitle by remember { mutableStateOf(newsData.newsTitle) }
    var updatedCategory by remember { mutableStateOf(newsData.newsCategory) }
    var updatedContent by remember { mutableStateOf(newsData.newsContent) }
    var updatedImageUrl by remember { mutableStateOf(newsData.imageUrl) }
    val userEmail = NewsPostPrefs.getReporterEmail(context)

    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) .padding(WindowInsets.systemBars.asPaddingValues())
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.main_color))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable { context.finish() },
                painter = painterResource(id = R.drawable.back_button),
                contentDescription = "Back Button"
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Update Post",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {


            Spacer(modifier = Modifier.height(16.dp))

            if (selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(selectedImageUri),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(newsData.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }


            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = updatedTitle,
                onValueChange = { updatedTitle = it },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))



            SelectNewsCategoryDD(
                selectedType = updatedCategory,
                onTypeSelected = { updatedCategory = it }
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = updatedContent,
                onValueChange = { updatedContent = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                minLines = 4
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val updatedNews = newsData.copy(
                        newsTitle = updatedTitle,
                        newsCategory = updatedCategory,
                        newsContent = updatedContent,
                        lat = newsData.lat,
                        lng = newsData.lng
                    )

                    updatedNews.imageUrl = newsData.imageUrl


                    updatePost(updatedNews, userEmail) {
                        Toast.makeText(context, "Post updated successfully!", Toast.LENGTH_SHORT)
                            .show()
                        context.finish()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_color))
            ) {
                Text(text = "Update Post", color = Color.White)
            }
        }
    }
}


