package com.veterinaria.hospipet.landing.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veterinaria.hospipet.R
import com.veterinaria.hospipet.ui.theme.WhitePure

@Composable
fun SingUpScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhitePure),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.weight(0.05f))
        Text(text = "Proxima pantalla!",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(0.4f))

    }

}