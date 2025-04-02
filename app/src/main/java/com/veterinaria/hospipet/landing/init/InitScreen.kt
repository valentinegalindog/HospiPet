package com.veterinaria.hospipet.landing.init

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veterinaria.hospipet.R
import com.veterinaria.hospipet.ui.theme.WhitePure

@Preview
@Composable
fun InitScreen( navigateToSignUp: () -> Unit = {}){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhitePure),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.weight(0.2f))
        Image(painter = painterResource(id = R.drawable.logo),
                        contentDescription = "",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(RoundedCornerShape(12.dp)))

        Spacer(modifier = Modifier.weight(0.09f))
        Text(text = "Inicio de Sesión",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(0.9f))

        Button(onClick = { navigateToSignUp() }, modifier = Modifier
            .fillMaxWidth()
            .padding(70.dp)
         ) {
        Text(text = "Iniciar sesión")

        }
        Spacer(modifier = Modifier.weight(0.05f))
        Text(text = "¿No puedes iniciar sesión?",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(0.4f))

    }

}