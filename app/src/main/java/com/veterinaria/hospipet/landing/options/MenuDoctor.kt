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
import androidx.compose.ui.unit.dp
import com.veterinaria.hospipet.R
import com.veterinaria.hospipet.landing.loader.Loader
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.Text


@Composable
fun MenuDoctor(title: String, imageRes: Int, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
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
fun TarjetasDoctor(navigateToMenuCustomer: (String) -> Unit, navigateToInit: () -> Unit, navigateToHome: () -> Unit) {
    var isLoading by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) } // 🔔 Modal de confirmación
    val coroutineScope = rememberCoroutineScope()

    val navigate = { destination: String ->
        isLoading = true
        coroutineScope.launch {
            delay(3000)
            isLoading = false
            navigateToMenuCustomer(destination)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF5F5F5))
            .padding(vertical = 32.dp)
    ) {
        // 🧭 Fila superior con los iconos izquierda y derecha
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Simula el espacio del icono izquierdo si está comentado

            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                contentDescription = "Atrás",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        // Acción para atrás
                        navigateToHome()
                    }
            )

            Spacer(modifier = Modifier.size(30.dp)) // Ocupa el mismo lugar que el ícono izquierdo

            // Icono derecha (cerrar sesión con modal)
            Icon(
                painter = painterResource(id = R.drawable.icon_out),
                contentDescription = "Salir",
                modifier = Modifier
                    .size(30.dp)
                    .clickable {
                        showLogoutDialog = true
                    }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (!isLoading) {
            Text(
                text = "🐾¡Bienvenido/a doctor!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (isLoading) {
            Loader(modifier = Modifier.fillMaxSize())
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MenuDoctor(
                    title = "Hospitalizar Mascota",
                    imageRes = R.drawable.mascota_hospitalizada,
                    modifier = Modifier.clickable { navigate("signUp") }
                )
                MenuDoctor(
                    title = "Animales Hospitalizados",
                    imageRes = R.drawable.animales_en_hospital,
                    modifier = Modifier.clickable { navigate("signUp") }
                )
            }
        }

        // 🧼 Modal de confirmación de cierre de sesión
        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text(text = "¿Cerrar sesión?") },
                text = { Text("¿Estás seguro que quieres cerrar sesión?") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showLogoutDialog = false
                            navigateToInit() // Confirmar cierre de sesión
                        }
                    ) {
                        Text("Aceptar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showLogoutDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}
