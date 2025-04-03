package com.veterinaria.hospipet.landing.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.veterinaria.hospipet.ui.theme.WhitePure

@Composable
fun SingUpScreen(auth: FirebaseAuth) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhitePure),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.weight(0.05f))
        Text(text = "Proxima pantalla! VAMOS BIEN!",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(0.4f))

    }

}