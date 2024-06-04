package com.example.connectingtofirebase

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.connectingtofirebase.ui.theme.ConnectingToFirebaseTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val auth: FirebaseAuth by lazy { Firebase.auth }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }
}

@Composable
fun AppContent(auth: FirebaseAuth) {

    var showSplashScreen by remember { mutableStateOf(true) }

    LaunchedEffect(showSplashScreen){
        delay(2000)
        showSplashScreen = false
    }


    Crossfade(targetState = showSplashScreen, label = "") {isSplashScreenVisible ->

        if(isSplashScreenVisible){
           SplashScreen{
               showSplashScreen = false
           }
        }else{
            //AuthOrMainScreen(auth)
           
        }

    }
}

@Composable
fun SplashScreen(navigateToAuthorMain: () -> Unit){
    
    var rotationState by remember{ mutableStateOf(0f)}

    LaunchedEffect(rotationState){
        delay(2000)
       rotationState += 1f
    }
    
    val scale by animateFloatAsState(targetValue = 1f, animationSpec = TweenSpec(durationMillis = 500), label = "")


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White), contentAlignment = Alignment.Center){
        Image(painter = painterResource(id = R.drawable.ic_launcher_background), contentDescription = "", modifier = Modifier
            .size(150.dp)
            .clip(
                CircleShape
            )
            .scale(scale)
            .rotate(rotationState))
    }
}

@Composable
fun AuthOrMainScreen(auth: FirebaseAuth){
    var user by remember{ mutableStateOf(auth.currentUser)}

    if(user == null){
        AuthScreen(
            onSignedIn = {signedInUser ->
                user = signedInUser

            }
        )
    }else{
        MainScreen(
            user = user!!
            onSignedOut = {
                auth.signOut()
                user = null
            }
        )
    }
}

@Composable
fun AuthScreen(onSignedIn: (FirebaseAuth) -> Unit) {
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    var firstName by remember { mutableStateOf("")}
    var lastName by remember { mutableStateOf("")}
    var isLoading by remember{ mutableStateOf(false)}
    var isSignIn by remember{ mutableStateOf(true)}
    var isPasswordVisible by remember { mutableStateOf(false)}

    var myErrorMessage by remember{ mutableStateOf<String?>(null)}
    
    val imagePainter: Painter = painterResource(id = R.drawable.ic_launcher_background)
    Box(modifier = Modifier.fillMaxSize()){
        Image(painter = imagePainter, contentDescription = "", modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

        //Card
        Card (modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface.copy(0.25f))
            .padding(25.dp)
            .clip(RoundedCornerShape(16.dp)),
            elevation = CardDefaults.cardElevation()
        ){

        }
    }
}






























