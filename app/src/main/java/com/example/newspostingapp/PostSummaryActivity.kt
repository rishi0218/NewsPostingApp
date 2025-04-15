package com.example.newspostingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

class PostSummaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPostedNewsScreen()
        }
    }
}

data class NewsItem(
    val title: String,
    val category: String,
    val content: String,
    val location: String,
    val status: String
)

@Composable
fun MyPostedNewsScreen() {
    var searchQuery by remember { mutableStateOf("") }

    val newsList = remember {
        listOf(
            NewsItem(
                "City Marathon 2025",
                "Sports",
                "City marathon concludes with record turnout...",
                "New York",
                "Published"
            ),
            NewsItem(
                "Election Debate Highlights",
                "Politics",
                "Key moments from the mayoral debate...",
                "Los Angeles",
                "Published"
            ),
            NewsItem(
                "Museum Launches New Exhibit",
                "Culture",
                "Modern art pieces showcased from...",
                "Chicago",
                "Under Review"
            ),
            NewsItem(
                "Armed Robbery in Downtown",
                "Crime",
                "Investigation continues on the recent armed robbery...",
                "Houston",
                "Rejected"
            ),
            NewsItem(
                "Forest Fire Near Hillside",
                "Environment",
                "Firefighters still battling wildfires...",
                "San Diego",
                "Published"
            ),
            NewsItem(
                "Beach Cleanup Drive",
                "Environment",
                "Volunteers gather to clean up the coastline...",
                "Miami",
                "Published"
            ),
            NewsItem(
                "Local Book Fair Opens",
                "Culture",
                "Annual book fair kicks off with hundreds of stalls...",
                "Boston",
                "Under Review"
            ),
            NewsItem(
                "Cybersecurity Breach at Tech Firm",
                "Tech",
                "Sensitive data exposed in a recent breach...",
                "San Francisco",
                "Rejected"
            ),
            NewsItem(
                "Rising Cost of Living",
                "Economy",
                "Residents face higher rent and food costs...",
                "Seattle",
                "Published"
            ),
            NewsItem(
                "Storm Warning Issued",
                "Weather",
                "Heavy rain expected in the next 48 hours...",
                "Denver",
                "Published"
            ),
            NewsItem(
                "Public Transport Fare Hike",
                "Politics",
                "New fare rates spark commuter protests...",
                "Atlanta",
                "Under Review"
            ),
            NewsItem(
                "New Startup Incubator",
                "Tech",
                "Tech Hub opens for early-stage startups...",
                "Austin",
                "Published"
            ),
            NewsItem(
                "Crime Rate Drops by 15%",
                "Crime",
                "Latest statistics show improvement in safety...",
                "Philadelphia",
                "Published"
            ),
            NewsItem(
                "Community Garden Inaugurated",
                "Local",
                "Mayor opens community garden in downtown area...",
                "Cleveland",
                "Published"
            ),
            NewsItem(
                "Scholarship Program Launched",
                "Education",
                "New scholarship to support underprivileged youth...",
                "Portland",
                "Under Review"
            )
        )
    }

    val filteredList = newsList.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
                it.category.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
                    .size(36.dp),
                painter = painterResource(id = R.drawable.back_button),
                contentDescription = "back"
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = "Post Summary",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {


            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search by title or category") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(filteredList.size) { news ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {


                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                filteredList[news].title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                "Category: ${filteredList[news].category}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                "Location: ${filteredList[news].location}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                "Status: ${filteredList[news].status}",
                                style = MaterialTheme.typography.bodySmall,
                                color =
                                when (filteredList[news].status) {
                                    "Published" -> Color(0xFF388E3C)
                                    "Under Review" -> Color(0xFF1976D2)
                                    "Rejected" -> Color(0xFFD32F2F)
                                    else -> Color.Gray
                                }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                filteredList[news].content.take(100) + "...",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
