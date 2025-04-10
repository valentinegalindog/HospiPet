package com.veterinaria.hospipet.landing.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
fun HomeScreen(title: String, imageRes: Int, modifier: Modifier = Modifier) {
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
fun TarjetasInicio(navigateToMenuCustomer: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()) // ✅ Scroll para pantallas pequeñas
            .background(Color(0xFFF5F5F5))
            .padding(vertical = 32.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Cuidemos juntos a tu mascota 🐾\n¿Eres Cliente o Médico veterinario?",
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
            HomeScreen(title = "Cliente", imageRes = R.drawable.cliente_veterinario, modifier = Modifier.clickable { navigateToMenuCustomer("menuCustomer") })
            HomeScreen(title = "Veterinario", imageRes = R.drawable.medico, modifier = Modifier.clickable { navigateToMenuCustomer("menuCustomer") })
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun PreviewTarjetasInicio() {
    TarjetasInicio()
}*/