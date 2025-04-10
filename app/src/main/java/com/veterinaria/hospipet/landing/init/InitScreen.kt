package com.veterinaria.hospipet.landing.init

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.veterinaria.hospipet.R
import com.veterinaria.hospipet.ui.theme.BlackPure
import com.veterinaria.hospipet.ui.theme.WhitePure

@Composable
fun InitScreen(auth: FirebaseAuth, navigateToLogin: () -> Unit = {}, navigateToHome: () -> Unit = {}) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhitePure),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.5f))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.weight(0.2f))
        Text(text = "Inicio de Sesión", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(0.3f))

        Text(text = "Correo electrónico", color = BlackPure, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(20.dp)) // Bajé más el TextField
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier
                .width(300.dp) // Fija un ancho máximo
                .border(2.dp, BlackPure, RoundedCornerShape(12.dp)) // Borde negro y esquinas redondeadas
                .clip(RoundedCornerShape(12.dp)),
            maxLines = 1, // Evita que crezca en altura
            singleLine = true // Mantiene el texto en una sola línea
        )

        Spacer(modifier = Modifier.height(20.dp)) // Bajé más el TextField de contraseña

        Text(text = "Contraseña", color = BlackPure, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier
                .width(300.dp) // Fija un ancho máximo
                .border(2.dp, BlackPure, RoundedCornerShape(12.dp)) // Borde negro y esquinas redondeadas
                .clip(RoundedCornerShape(12.dp)),
            maxLines = 1, // Evita que crezca en altura
            singleLine = true, // Mantiene el texto en una sola línea
            visualTransformation = PasswordVisualTransformation() // Oculta el texto como contraseña
        )

        Spacer(modifier = Modifier.height(48.dp)) // Bajé más el botón

        Button(
            onClick = { auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.i("valentin", "LOGIN OK")
                    navigateToHome()
                }else{
                    Log.i("valentin", "KO")
                }
            }},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
        ) {
            Text(text = "Iniciar sesión")
        }

        Spacer(modifier = Modifier.weight(0.05f))
        Text(text = "Crear una cuenta", fontSize = 13.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable { navigateToLogin() })
        Spacer(modifier = Modifier.weight(0.4f))
    }
}