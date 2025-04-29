package mobileapp.newsposting.s3351728sagarbonthu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class NewsHomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsHomeScreen()
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
        modifier = Modifier.fillMaxSize() .padding(WindowInsets.systemBars.asPaddingValues())
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
                    .weight(1f)
                    .border(
                        width = 2.dp,
                        color = colorResource(id = R.color.card_c1),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable {
                        context.startActivity(Intent(context, AllNewsActivity::class.java))
                    }
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {

                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.news_posting),
                    contentDescription = "News Feed"
                )

                Text(
                    modifier = Modifier,
                    text = "News Feed",
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
                        context.startActivity(Intent(context, SearchByAreaActivity::class.java))

                    }
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {

                Image(
                    modifier = Modifier
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.news_posting),
                    contentDescription = "News by area"
                )

                Text(
                    modifier = Modifier,
                    text = "News by area",
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
                    .clickable {
                        context.startActivity(Intent(context, ManagePostsActivity::class.java))
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
                    .clickable {
                        context.startActivity(Intent(context, WriterProfileActivity::class.java))
                    }
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
                    .clickable {
                        context.startActivity(Intent(context, SupportActivity::class.java))
                    }
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
                    text = "Contact Us",
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