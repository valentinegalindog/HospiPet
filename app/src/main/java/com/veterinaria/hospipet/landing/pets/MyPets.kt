package com.veterinaria.hospipet.landing.pets

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.veterinaria.hospipet.R

// --- Modelo de datos renombrado a 'Pet' ---
data class Pet(
    val id: String = "",
    val name: String = "",
    val especie: String = "",
    val raza: String = "",
    val sexo: String = "",
    val fechaNacimiento: String = "",
    val fechaDesparacitante: String = "",
    val fechaVacunaRabia: String = "",
    val foto: String = ""
)

@Composable
fun MyPets(
    navigateToMenuCustomer: () -> Unit,
    navigateToInit: () -> Unit,
    navigateToRegisterPet: () -> Unit
) {
    val db = Firebase.firestore
    val storageRef = Firebase.storage.reference
    val pets = remember { mutableStateListOf<Pet>() }

    var showLogoutDialog by remember { mutableStateOf(false) }
    var petToDelete by remember { mutableStateOf<Pet?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // 1) Cargo desde Firestore
        val snap = db.collection("pets").get().await()
        val fetched = snap.documents.mapNotNull { doc ->
            doc.toObject(Pet::class.java)?.copy(id = doc.id)
        }
        pets.clear()
        pets.addAll(fetched)

        // 2) Descargo imágenes y actualizo URL
        fetched.forEachIndexed { idx, pet ->
            if (pet.foto.isNotBlank()) {
                try {
                    val url = storageRef.child(pet.foto).downloadUrl.await().toString()
                    pets[idx] = pet.copy(foto = url)
                } catch (e: Exception) {
                    Log.e("MyPets", "No pude cargar imagen de ${pet.name}", e)
                }
            }
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp, vertical = 16.dp)
        ) {
            // Encabezado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Atrás",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { navigateToMenuCustomer() }
                )
                Icon(
                    painter = painterResource(R.drawable.icon_out),
                    contentDescription = "Salir",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { showLogoutDialog = true }
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "🐾 ¡Aquí están tus mejores amigos!",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(Modifier.height(32.dp))

            if (pets.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No tienes mascotas registradas.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(pets) { pet ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Column {
                                // Imagen
                                Box(
                                    modifier = Modifier
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                                ) {
                                    AsyncImage(
                                        model = pet.foto,
                                        contentDescription = pet.name,
                                        placeholder = painterResource(R.drawable.agregar_foto),
                                        error = painterResource(R.drawable.agregar_foto),
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Crop
                                    )
                                }

                                // Nombre + botón Eliminar
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        pet.name,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    TextButton(onClick = { petToDelete = pet }) {
                                        Text("Eliminar")
                                    }
                                }

                                // Detalles
                                Column(Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
                                    Text("Especie: ${pet.especie}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Raza: ${pet.raza}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Sexo: ${pet.sexo}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Nacimiento: ${pet.fechaNacimiento}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Desparacitante: ${pet.fechaDesparacitante}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Vacuna Rabia: ${pet.fechaVacunaRabia}", style = MaterialTheme.typography.bodyMedium)
                                }
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = navigateToRegisterPet,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("+ Añadir Mascota")
                }
            }

            // Diálogo de logout
            if (showLogoutDialog) {
                AlertDialog(
                    onDismissRequest = { showLogoutDialog = false },
                    title = { Text("¿Cerrar sesión?") },
                    text = { Text("¿Estás seguro que quieres cerrar sesión?") },
                    confirmButton = {
                        TextButton(onClick = {
                            showLogoutDialog = false
                            navigateToInit()
                        }) { Text("Aceptar") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showLogoutDialog = false }) { Text("Cancelar") }
                    }
                )
            }

            // Diálogo de confirmación de eliminación
            petToDelete?.let { pet ->
                AlertDialog(
                    onDismissRequest = { petToDelete = null },
                    title = { Text("Eliminar mascota") },
                    text = { Text("¿Eliminar a ${pet.name}?") },
                    confirmButton = {
                        TextButton(onClick = {
                            coroutineScope.launch {
                                try {
                                    db.collection("pets").document(pet.id).delete().await()
                                    pets.remove(pet)
                                } catch (e: Exception) {
                                    Log.e("MyPets", "Error borrando ${pet.name}", e)
                                } finally {
                                    petToDelete = null
                                }
                            }
                        }) { Text("Eliminar") }
                    },
                    dismissButton = {
                        TextButton(onClick = { petToDelete = null }) { Text("Cancelar") }
                    }
                )
            }
        }
    }
}
