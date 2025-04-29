package mobileapp.newsposting.s3351728sagarbonthu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
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
import com.google.firebase.database.FirebaseDatabase
import kotlin.jvm.java

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RegisterScreen()
        }
    }
}

@Composable
fun RegisterScreen() {
    var posterEmail by remember { mutableStateOf("") }
    var posterPassword by remember { mutableStateOf("") }
    var posterConfirmPassword by remember { mutableStateOf("") }
    var posterFullName by remember { mutableStateOf("") }
    var posterQualification by remember { mutableStateOf("") }

    val context = LocalContext.current as Activity

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(Color.Green)
            .padding(WindowInsets.systemBars.asPaddingValues()),
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
                    painter = painterResource(id = R.drawable.newsposting_ic), // Replace with your drawable resource
                    contentDescription = "App Logo",
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Login Title
                Text(
                    text = "Register To\nNews Posting",
                    color = Color(0xFF64A70B), // Green color similar to the design
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    textAlign = TextAlign.Center
                )


                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = posterFullName,
                    onValueChange = { posterFullName = it },
                    label = { Text("Full Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))


                // Email TextField
                OutlinedTextField(
                    value = posterEmail,
                    onValueChange = { posterEmail = it },
                    label = { Text("Email Address") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Password TextField
                OutlinedTextField(
                    value = posterQualification, onValueChange = { posterQualification = it },
                    label = { Text(text = "Qualification") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 12.dp,
                        )
                )
                OutlinedTextField(
                    value = posterPassword,
                    onValueChange = { posterPassword = it },
                    label = { Text("Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = posterConfirmPassword,
                    onValueChange = { posterConfirmPassword = it },
                    label = { Text("Confirm Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                )
                Spacer(modifier = Modifier.height(14.dp))


                // Login Button
                Button(
                    onClick = {
                        when {
                        posterFullName.isEmpty() -> {
                            Toast.makeText(context, "Oops, the name section is empty!", Toast.LENGTH_SHORT).show()
                        }

                        posterEmail.isEmpty() -> {
                            Toast.makeText(context, "Oops, the email section is empty!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        posterQualification.isEmpty() -> {
                            Toast.makeText(context, "Oops, the qualification section is empty!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        posterPassword.isEmpty() -> {
                            Toast.makeText(context, "Oops, the password section is empty!", Toast.LENGTH_SHORT)
                                .show()
                        }
                        posterConfirmPassword.isEmpty() -> {
                            Toast.makeText(context, "Oops, the confirm password section is empty!", Toast.LENGTH_SHORT)
                                .show()
                        }

                        else -> {
                            val posterDetails = PosterDetails(
                                posterFullName,
                                posterEmail,
                                posterQualification,
                                posterPassword
                            )
                            registerUser(posterDetails,context);
                        }

                    }
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp)
                        .height(38.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)

                ) {
                    Text(text = "Register")
                }

                Spacer(modifier = Modifier.height(15.dp))

                // Forgot Password Text
                Text(
                    text = "Login",
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .clickable {
                            context.startActivity(Intent(context, SignInActivity::class.java))
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


fun registerUser(posterDetails: PosterDetails, context: Context) {

    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("PosterDetails")

    databaseReference.child(posterDetails.emailid.replace(".", ","))
        .setValue(posterDetails)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(context, "You Registered Successfully", Toast.LENGTH_SHORT)
                    .show()

                context.startActivity(Intent(context, SignInActivity::class.java))
                (context as Activity).finish()

            } else {
                Toast.makeText(
                    context,
                    "Registration Failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        .addOnFailureListener { _ ->
            Toast.makeText(
                context,
                "Something went wrong",
                Toast.LENGTH_SHORT
            ).show()
        }
}

data class PosterDetails(
    var name : String = "",
    var emailid : String = "",
    var qualification : String = "",
    var password: String = ""
)