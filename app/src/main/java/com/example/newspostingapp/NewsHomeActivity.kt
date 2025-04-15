package com.example.newspostingapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity

class NewsHomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsHomeScreen()
        }
    }
}

@Composable
fun NewsHomeScreenOld() {
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.Black
                )
                .padding(vertical = 8.dp, horizontal = 12.dp)
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(id = R.drawable.news_posting),
                contentDescription = "back"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "News Posting App",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = Color.White
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 16.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {

                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.news_posting),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Create\nPost",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.news_posting),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))


                    Text(
                        text = "Add\nCollaborators",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.news_posting),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Manage\nPosts",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(top = 16.dp, bottom = 0.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {


                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {

                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.news_posting),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Published Posts",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.news_posting),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))


                    Text(
                        text = "Delete\nPost",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clickable {
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.news_posting),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(36.dp)
                            .width(36.dp)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Manage\nProfile",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                }


            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun NewsHomeScreenP() {
    NewsHomeScreen()
}

@Composable
fun NewsHomeScreen() {

    val context = LocalContext.current as Activity


    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.main_color))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = "News Posting App",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
        }


        Spacer(modifier = Modifier.height(12.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {

            Column(
                modifier = Modifier
                    .clickable {
                        context.startActivity(Intent(context, CreatePostActivity::class.java))
                    }
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.card_c1),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {

                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.news_posting),
                    contentDescription = "News Posting"
                )

                Text(
                    modifier = Modifier,
                    text = "Create Post",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.card_c1),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {

                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.news_posting),
                    contentDescription = "News Posting"
                )

                Text(
                    modifier = Modifier,
                    text = "Add Collaborators",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

            }

        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.card_c1),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {

                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.news_posting),
                    contentDescription = "Manage Posts"
                )

                Text(
                    modifier = Modifier,
                    text = "Manage Posts",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.card_c1),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable {
                        context.startActivity(Intent(context, PostSummaryActivity::class.java))

                    }
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {

                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.news_posting),
                    contentDescription = "News Posting"
                )

                Text(
                    modifier = Modifier,
                    text = "Posts Summary",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

            }

        }

        Spacer(modifier = Modifier.height(12.dp))


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.card_c1),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {

                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.news_posting),
                    contentDescription = "My Profile"
                )

                Text(
                    modifier = Modifier,
                    text = "My Profile",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.card_c1),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 6.dp),


            ) {

                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.news_posting),
                    contentDescription = "News Posting"
                )

                Text(
                    modifier = Modifier,
                    text = "Logout Account",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )

            }

        }


    }
}