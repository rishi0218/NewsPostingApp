package com.example.newspostingapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.FirebaseDatabase

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsPostingLogin()
        }
    }
}

@Composable
fun NewsPostingLogin() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(Color.Green),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        )
        {
            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )
            { // Logo (replace with acorn image resource if you have one)
                Image(
                    painter = painterResource(id = R.drawable.news_posting), // Replace with your drawable resource
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Login Title
                Text(
                    text = "Login To News Posting",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF64A70B), // Green color similar to the design
                    fontSize = 20.sp,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center

                )


                Spacer(modifier = Modifier.height(16.dp))

                // Email TextField
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email Address") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Password TextField
                OutlinedTextField(
                    value = password, onValueChange = { password = it },
                    label = { Text(text = "Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 12.dp,
                        )
                )

                Spacer(modifier = Modifier.height(15.dp))

                // Login Button
                Button(
                    onClick = {
                        when {
                        email.isEmpty() -> {
//                            Toast.makeText(context, " Please Enter Mail", Toast.LENGTH_SHORT).show()
                        }

                        password.isEmpty() -> {
//                            Toast.makeText(context, " Please Enter Password", Toast.LENGTH_SHORT)
//                                .show()
                        }

                        else -> {
                            val posterDetails = PosterDetails(
                                "",
                                email,
                                "",
                                password
                            )

                            loginUser(posterDetails,context)
                        }

                    }
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .height(38.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
                ) {
                    Text(text = "Login")
                }

                Spacer(modifier = Modifier.height(15.dp))

                // Forgot Password Text
                Text(
                    text = "Register",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .clickable {
                            context.startActivity(Intent(context, SignUpActivity::class.java))
                            context.finish()
                        }
                        .align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(30.dp))
            }
            Spacer(modifier = Modifier.weight(1f))

        }

    }
}


fun loginUser(posterDetails: PosterDetails, context: Context) {

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("PosterDetails").child(posterDetails.emailid.replace(".", ","))

    databaseReference.get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val newsData = task.result?.getValue(PosterDetails::class.java)
            if (newsData != null) {
                if (newsData.password == posterDetails.password) {

                    NewsPostingData.writeLS(context, true)
                    NewsPostingData.writeMail(context, newsData.emailid)
                    NewsPostingData.writeUserName(context, newsData.name)

                    context.startActivity(Intent(context, NewsHomeActivity::class.java))
                    Toast.makeText(context, "Login Sucessfully", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Seems Incorrect Credentials", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "Your account not found", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(
                context,
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}
