package com.veterinaria.hospipet.landing.loader

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun Loader(
    text: String = "Cargando...",
    modifier: Modifier = Modifier // Añade este parámetro
) {
    var dotCount by remember { mutableStateOf(1) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            dotCount = if (dotCount == 3) 1 else dotCount + 1
        }
    }

    val dots = ".".repeat(dotCount)

    // Aplica el modificador al Text
    Text(
        text = "$text$dots",
        fontSize = 24.sp,
        color = Color.White,
        modifier = modifier
            .fillMaxSize() // Esto hace que ocupe toda la pantalla
            .wrapContentSize(Alignment.Center) // Centra el texto
    )
}
