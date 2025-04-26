package com.example.newspostingapp

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CreatePostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreatePostScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreatePostScreenP() {
    CreatePostScreen()
}

@Composable
fun CreatePostScreen() {

    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity

    var locationText by remember { mutableStateOf("Select News Area...") }

    var locLat by remember { mutableDoubleStateOf(0.0) }
    var locLng by remember { mutableDoubleStateOf(0.0) }


    var isPuckUpSelected by remember { mutableStateOf(false) }

    var selectedCategory by remember { mutableStateOf("") }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val lat = data?.getDoubleExtra("LAT", 0.0) ?: 0.0
            val lng = data?.getDoubleExtra("LNG", 0.0) ?: 0.0
            locLat = lat
            locLng = lng
            locationText = "Lat: $lat, Lng: $lng"
            isPuckUpSelected = true
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
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
                        context.finish()
                    },
                painter = painterResource(id = R.drawable.back_button),
                contentDescription = "back"
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                text = "News Posting App",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall.copy(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        UploadPostImage()

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("News Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))


        SelectNewsCategoryDD(
            selectedType = selectedCategory,
            onTypeSelected = { selectedCategory = it }
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            minLines = 4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

//        OutlinedTextField(
//            value = location,
//            onValueChange = { location = it },
//            label = { Text("Location") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 12.dp)
//        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(
                        3.dp
                    )
                )
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(3.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            if (isPuckUpSelected) {
                val address = getAddressFromLatLng(context, LatLng(locLat, locLng))
                Text(modifier = Modifier.weight(1f), text = address, maxLines = 2)
            } else {
                Text(text = locationText)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Select",
                color = Color.White,
                modifier = Modifier
                    .wrapContentHeight()
                    .clickable {
//                        context.startActivity(Intent(context, PickupLocationActivity::class.java))

                        isPuckUpSelected = false
                        val intent = Intent(context, SelectLocationActivity::class.java)
                        launcher.launch(intent)
                    }
                    .background(
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(
                            6.dp
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.black),
                        shape = RoundedCornerShape(6.dp)
                    )
                    .padding(
                        horizontal = 12.dp,
                        vertical = 4.dp
                    )
            )
        }

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = {

                val newsData = NewsData(
                    newsTitle = title,
                    newsCategory = selectedCategory,
                    newsContent = content,
                    lat = locLat.toString(),
                    lng = locLng.toString()
                )


                uploadNewsWithImage(newsData,NewsPhoto.selImageUri,context)

            },
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .height(38.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.main_color))
        ) {
            Text(text = "Publish", color = Color.Black)
        }

    }
}

fun uploadNewsWithImage(
    newsData: NewsData,
    selectedImageUri: Uri,
    context: Context
) {
    val storageRef = FirebaseStorage.getInstance().reference
    val databaseRef = FirebaseDatabase.getInstance().reference

    var userName = NewsPostingData.readUserName(context)

    val userEmail = NewsPostingData.readMail(context).replace(".",",")

    val newsId = databaseRef.child("NewsPosts").push().key ?: return

    val imageRef = storageRef.child("NewsPosts/$newsId/news_image.jpg")
    imageRef.putFile(selectedImageUri)
        .continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            imageRef.downloadUrl
        }
        .addOnSuccessListener { downloadUri ->
            // Image uploaded successfully, now save the news data
            val newsMap = mapOf(
                "newsId" to newsId,
                "newsTitle" to newsData.newsTitle,
                "newsCategory" to newsData.newsCategory,
                "newsContent" to newsData.newsContent,
                "imageUrl" to downloadUri.toString(),
                "lat" to newsData.lat,
                "lng" to newsData.lng,
                "timestamp" to System.currentTimeMillis(),
                "author" to userName
            )

            databaseRef.child("NewsPosts/$userEmail").child(newsId)
                .setValue(newsMap)
                .addOnSuccessListener {
                    Toast.makeText(context, "Posted News Successfully.", Toast.LENGTH_SHORT).show()

                }
                .addOnFailureListener {
                    Toast.makeText(context, "Failed to save news.", Toast.LENGTH_SHORT).show()
                }
        }
        .addOnFailureListener {
            Toast.makeText(context, "Failed to upload image.", Toast.LENGTH_SHORT).show()
        }
}



@Composable
fun UploadPostImage() {
    val activityContext = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val captureImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                imageUri = getImageUri(activityContext)
                NewsPhoto.selImageUri = imageUri as Uri
                NewsPhoto.isImageSelected = true
            } else {
                NewsPhoto.isImageSelected = false
                Toast.makeText(activityContext, "Capture Failed", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Toast.makeText(activityContext, "Permission Granted", Toast.LENGTH_SHORT).show()
                captureImageLauncher.launch(getImageUri(activityContext)) // Launch the camera
            } else {
                Toast.makeText(activityContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    Column(
        modifier = Modifier.size(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = if (imageUri != null) {
                rememberAsyncImagePainter(model = imageUri)
            } else {
                painterResource(id = R.drawable.upload_image)
            },
            contentDescription = "Captured Image",
            modifier = Modifier
                .clickable {
                    if (ContextCompat.checkSelfPermission(
                            activityContext,
                            android.Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        captureImageLauncher.launch(getImageUri(activityContext))
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (imageUri == null) {
            Text(text = "Tap the image to capture")
        }
    }
}

fun getImageUri(activityContext: Context): Uri {
    val file = File(activityContext.filesDir, "captured_image.jpg")
    return FileProvider.getUriForFile(
        activityContext,
        "${activityContext.packageName}.fileprovider",
        file
    )
}


object NewsPhoto {
    lateinit var selImageUri: Uri
    var isImageSelected = false
}

data class NewsData(
    val newsTitle: String = "",
    val newsCategory: String = "",
    val newsContent: String = "",
    var newsId: String = "",
    var lat: String = "",
    var lng: String = "",
    var imageUrl: String = "",
    var author:String = ""
)

fun getAddressFromLatLng(context: Context, latLng: LatLng): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
        addresses?.get(0)?.getAddressLine(0) ?: "Unknown Location"
    } catch (e: Exception) {
        "Unknown Location"
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectNewsCategoryDD(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    val types = listOf("Politics","Health","Sports","Disaster")
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedType,
            onValueChange = {},
            readOnly = true,
            label = { Text("Select Category") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor() // Important for anchoring the dropdown
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            types.forEach { type ->

                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}



