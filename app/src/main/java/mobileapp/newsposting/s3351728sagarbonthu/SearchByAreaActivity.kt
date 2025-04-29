package mobileapp.newsposting.s3351728sagarbonthu

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

class SearchByAreaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsMapScreen(userMail = NewsPostPrefs.getReporterEmail(this))
        }
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
fun NewsMapScreen(userMail: String) {
    val context = LocalContext.current
    var newsList by remember { mutableStateOf<List<NewsData>>(emptyList()) }
    var selectedNews by remember { mutableStateOf<NewsData?>(null) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(52.109631, -0.948604), 8f)
    }

    LaunchedEffect(userMail) {
        getNewsDetails(userMail) { fetchedNewsList ->
            newsList = fetchedNewsList
            if (fetchedNewsList.isNotEmpty()) {
                val firstNews =
                    fetchedNewsList.firstOrNull { it.lat.isNotEmpty() && it.lng.isNotEmpty() }
//                if (firstNews != null) {
//                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
//                        LatLng(firstNews.lat.toDouble(), firstNews.lng.toDouble()), 5f
//                    )
//                }
            }
        }
    }

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

            Image(
                modifier = Modifier
                    .size(36.dp)
                    .clickable {
                        (context as Activity).finish()
                    },
                painter = painterResource(id = R.drawable.back_button),
                contentDescription = "back"
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = "Search News by Area",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
        }


        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {
                newsList.forEach { news ->
                    if (news.lat.isNotEmpty() && news.lng.isNotEmpty()) {
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    news.lat.toDouble(),
                                    news.lng.toDouble()
                                )
                            ),
                            title = news.newsTitle,
                            snippet = news.newsCategory,
                            onClick = {
                                selectedNews = news
                                false // return false so default behavior (show InfoWindow) happens
                            }
                        )
                    }
                }
            }

            // Popup News Dialog
            selectedNews?.let { news ->
                AlertDialog(
                    onDismissRequest = { selectedNews = null },
                    title = {
                        Text(text = news.newsTitle, fontWeight = FontWeight.Bold)
                    },
                    text = {
                        Column {
                            Text(text = "Category: ${news.newsCategory}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = news.newsContent)
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { selectedNews = null }) {
                            Text("Close")
                        }
                    }
                )
            }
        }
    }
}
