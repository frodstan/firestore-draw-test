package com.example.nrk_firebase_test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nrk_firebase_test.ui.theme.NrkfirebasetestTheme
import com.google.firebase.Timestamp

class MainActivity : ComponentActivity() {
    private val firebaseRepository: FirebaseRepository by lazy { FirebaseRepository() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NrkfirebasetestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val clickPoints by firebaseRepository.getClickPoints().collectAsState(initial = emptyList())
                    ClickCanvas(
                        clicks = clickPoints,
                        onClick = { x, y ->
                            val clickPoint = ClickPoint(x = x, y = y, timestamp = Timestamp.now())
                            firebaseRepository.addClickPoint(clickPoint)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NrkfirebasetestTheme {
        Greeting("Android")
    }
}