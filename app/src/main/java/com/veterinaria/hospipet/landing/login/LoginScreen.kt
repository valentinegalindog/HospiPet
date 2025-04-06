package com.veterinaria.hospipet.landing.login

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.veterinaria.hospipet.R
import com.veterinaria.hospipet.ui.theme.BlackPure
import com.veterinaria.hospipet.ui.theme.WhitePure

@Composable
fun LoginScreen(auth: FirebaseAuth, navigateToHome: () -> Unit = {}, navigateToLogin: () -> Unit = {}, navigateToInit: () -> Unit = {} ) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhitePure),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(0.01f))
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
            contentDescription = "Back",
            modifier = Modifier.clickable { navigateToInit() }
                .size(50.dp) // Ajusta el tamaño si es necesario
                .align(Alignment.Start) // Lo coloca en la esquina superior izquierda
                .padding(start = 12.dp)
        )
        Spacer(modifier = Modifier.weight(0.02f))
        Text(text = "Crea tu cuenta", fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(0.02f))

        Text(text = "Correo electrónico", color = BlackPure, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(20.dp)) // Bajé más el TextField
        TextField(
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.clickable { navigateToLogin() }
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
            onClick = { auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    //Registrado
                    Log.i("Valentin", "Registrado OK")
                    navigateToHome()
                }else{
                    //Error
                    Log.i("Valentin", "Registro KO")
                }

            }},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 70.dp)
        ) {
            Text(text = "Crear cuenta")
        }

        Spacer(modifier = Modifier.weight(0.05f))

    }

}