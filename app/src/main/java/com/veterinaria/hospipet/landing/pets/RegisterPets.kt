package com.veterinaria.hospipet.landing.pets

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.veterinaria.hospipet.R
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch

// Data class para la mascota
data class Pets(
    val foto: String = "",
    val name: String = "",
    val especie: String = "",
    val sexo: String = "",
    val raza: String = "",
    val fechaDesparacitante: String = "",
    val fechaVacunaRabia: String = "",
    val fechaNacimiento: String = ""
)

// Función de guardado con feedback a UI
fun createPet(
    db: FirebaseFirestore,
    pet: Pets,
    onSuccess: () -> Unit,
    onError: (Exception) -> Unit,

) {
    db.collection("pets")
        .add(pet)
        .addOnSuccessListener {
            Log.i("Veterinaria", "Pet registrada con éxito")
            onSuccess()
        }
        .addOnFailureListener { e ->
            Log.e("Veterinaria", "Error al registrar", e)
            onError(e)
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterPetScreen(db: FirebaseFirestore) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Campos de formulario
    var name by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var foto by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var fechaDesparacitante by remember { mutableStateOf("") }
    var fechaVacunaRabia by remember { mutableStateOf("") }

    // Dropdown state
    val especies = listOf("Perro", "Gato", "Conejo")
    var expandedEspecie by remember { mutableStateOf(false) }

    val sexos = listOf("Macho", "Hembra")
    var expandedSexo by remember { mutableStateOf(false) }

    val razaOptions = when (especie) {
        "Perro"   -> listOf("Mestizo", "Labrador", "Poodle", "Pitbull")
        "Gato"    -> listOf("Mestizo", "Siamés", "Persa", "Maine Coon")
        "Conejo"  -> listOf("Enano", "Angora", "Cabeza de León")
        else       -> emptyList()
    }
    var expandedRaza by remember { mutableStateOf(false) }

    // Launcher para selección de imagen
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { foto = it.toString() } }

    // DatePickerDialog
    val calendar = Calendar.getInstance()
    fun showDatePicker(onDateSelected: (String) -> Unit) {
        DatePickerDialog(
            context,
            { _, y, m, d ->
                calendar.set(y, m, d)
                onDateSelected(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Registro de Mascota", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            // Foto
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .size(240.dp)
                    .clickable { launcher.launch("image/*") }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Agregar foto", style = MaterialTheme.typography.bodyLarge)
                    Spacer(Modifier.height(8.dp))
                    if (foto.isEmpty()) {
                        Image(
                            painter = painterResource(R.drawable.agregar_foto),
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        AsyncImage(
                            model = foto,
                            contentDescription = null,
                            modifier = Modifier
                                .size(140.dp)
                                .clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

            // Nombre
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            // Dropdown Especie
            ExposedDropdownMenuBox(
                expanded = expandedEspecie,
                onExpandedChange = { expandedEspecie = !expandedEspecie },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = especie,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Especie") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedEspecie) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedEspecie,
                    onDismissRequest = { expandedEspecie = false }
                ) {
                    especies.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                especie = option
                                expandedEspecie = false
                                raza = ""
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))

            // Dropdown Sexo
            ExposedDropdownMenuBox(
                expanded = expandedSexo,
                onExpandedChange = { expandedSexo = !expandedSexo },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = sexo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sexo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedSexo) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedSexo,
                    onDismissRequest = { expandedSexo = false }
                ) {
                    sexos.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                sexo = option
                                expandedSexo = false
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))

            // Dropdown Raza
            ExposedDropdownMenuBox(
                expanded = expandedRaza,
                onExpandedChange = { if (especie.isNotEmpty()) expandedRaza = !expandedRaza },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = raza,
                    onValueChange = {},
                    readOnly = true,
                    enabled = especie.isNotEmpty(),
                    label = { Text("Raza") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedRaza) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expandedRaza,
                    onDismissRequest = { expandedRaza = false }
                ) {
                    if (razaOptions.isNotEmpty()) {
                        razaOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    raza = option
                                    expandedRaza = false
                                }
                            )
                        }
                    } else {
                        DropdownMenuItem(
                            text = { Text("Seleccione especie primero") },
                            onClick = {}
                        )
                    }
                }
            }
            Spacer(Modifier.height(12.dp))

            // Fechas
            OutlinedTextField(
                value = fechaNacimiento,
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha de nacimiento") },
                trailingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.clickable { showDatePicker { fechaNacimiento = it } }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker { fechaNacimiento = it } }
            )
            OutlinedTextField(
                value = fechaDesparacitante,
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha desparasitante") },
                trailingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.clickable { showDatePicker { fechaDesparacitante = it } }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker { fechaDesparacitante = it } }
            )
            OutlinedTextField(
                value = fechaVacunaRabia,
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha vacuna rabia") },
                trailingIcon = {
                    Icon(
                        Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier.clickable { showDatePicker { fechaVacunaRabia = it } }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker { fechaVacunaRabia = it } }
            )
            Spacer(Modifier.height(20.dp))

            // Botón Registrar
            Button(onClick = {
                val pet = Pets(
                    foto = foto,
                    name = name,
                    especie = especie,
                    sexo = sexo,
                    raza = raza,
                    fechaDesparacitante = fechaDesparacitante,
                    fechaVacunaRabia = fechaVacunaRabia,
                    fechaNacimiento = fechaNacimiento
                )
                createPet(db, pet,
                    onSuccess = {
                        scope.launch { snackbarHostState.showSnackbar("Mascota registrada con éxito") }
                        // Reset fields
                        name = ""; especie = ""; sexo = ""; raza = ""; foto = ""
                        fechaNacimiento = ""; fechaDesparacitante = ""; fechaVacunaRabia = ""
                    },
                    onError = { e -> scope.launch { snackbarHostState.showSnackbar("Error: ${e.message}") } }
                )
            }) {
                Text("Registrar Mascota")
            }
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val db = FirebaseFirestore.getInstance()
        setContent { MaterialTheme { RegisterPetScreen(db) } }
    }
}