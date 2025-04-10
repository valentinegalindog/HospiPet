package com.veterinaria.hospipet.landing.options

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.veterinaria.hospipet.R

@Composable
fun MenuScreen(
    title: String,
    imageRes: Int,
    modifier: Modifier = Modifier,
    navController: (String) -> Unit, // Se espera que sea una función que navegue
    click: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
            .clickable {
                // Navegar a otra pantalla
                navController(click) // Usar 'click' para navegar
            }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            )
        }
    }
}

@Composable
fun TarjetasMenuCustomer(
    navigateToInit: () -> Unit = {},
    navigateToMenuCustomer: (String) -> Unit = {}, // Cambiar para recibir String
    navigateToOptions: () -> Unit = {}
) {
    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = { Text("Cerrar sesión") },
            text = { Text("¿Deseas cerrar sesión?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog.value = false
                        navigateToInit() // Acción de cerrar sesión
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog.value = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // ✅ Scroll para pantallas pequeñas
            .background(Color(0xFFF5F5F5))
            .padding(vertical = 32.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "BIENVENIDO",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MenuScreen(
                title = "Cliente",
                imageRes = R.drawable.cliente_veterinario,
                navController = navigateToMenuCustomer,
                click = "menuOptions" // Pasar el valor del click
            )
            MenuScreen(
                title = "Veterinario",
                imageRes = R.drawable.medico,
                navController = navigateToMenuCustomer,
                click = "menuOptions" // Pasar el valor del click
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewTarjetasInicio() {
    TarjetasMenuCustomer()
}