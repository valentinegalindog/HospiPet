package com.veterinaria.hospipet.landing.appointment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// Data class de la cita
data class Cita(
    val fecha: String = "",
    val hora: String = "",
    val motivo: String = "",
    val doctor: String = ""
)

// Función para guardar la cita en Firestore
fun createCita(
    db: FirebaseFirestore,
    cita: Cita,
    onSuccess: () -> Unit,
    onError: (Exception) -> Unit
) {
    db.collection("citas")
        .add(cita)
        .addOnSuccessListener {
            Log.i("Veterinaria", "Cita agendada con éxito")
            onSuccess()
        }
        .addOnFailureListener { e ->
            Log.e("Veterinaria", "Error al agendar cita", e)
            onError(e)
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCitaScreen(db: FirebaseFirestore) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Formulario
    var fecha by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var motivo by remember { mutableStateOf("") }
    var doctor by remember { mutableStateOf("") }

    // Dropdown
    val doctores = listOf("Jessica Yirsa", "Alberto Rios")
    var expandedDoctor by remember { mutableStateOf(false) }

    // Lista de citas
    var listaCitas by remember { mutableStateOf(listOf<Cita>()) }

    fun cargarCitas() {
        db.collection("citas")
            .get()
            .addOnSuccessListener { result ->
                val citas = result.map { doc ->
                    Cita(
                        fecha = doc.getString("fecha") ?: "",
                        hora = doc.getString("hora") ?: "",
                        motivo = doc.getString("motivo") ?: "",
                        doctor = doc.getString("doctor") ?: ""
                    )
                }
                listaCitas = citas
            }
            .addOnFailureListener { e ->
                Log.e("Veterinaria", "Error al cargar citas", e)
            }
    }

    // Cargar al iniciar
    LaunchedEffect(true) {
        cargarCitas()
    }

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

    fun showTimePicker(onTimeSelected: (String) -> Unit) {
        TimePickerDialog(
            context,
            { _, h, m ->
                calendar.set(Calendar.HOUR_OF_DAY, h)
                calendar.set(Calendar.MINUTE, m)
                onTimeSelected(SimpleDateFormat("HH:mm", Locale.getDefault()).format(calendar.time))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
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
            Text("Citas Agendadas", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            listaCitas.forEach { cita ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text("📅 Fecha: ${cita.fecha}")
                        Text("⏰ Hora: ${cita.hora}")
                        Text("📝 Motivo: ${cita.motivo}")
                        Text("👩‍⚕️ Doctor: ${cita.doctor}")
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("Agendar nueva cita", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = fecha,
                onValueChange = {},
                readOnly = true,
                label = { Text("Fecha") },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = null,
                        modifier = Modifier.clickable { showDatePicker { fecha = it } })
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = hora,
                onValueChange = {},
                readOnly = true,
                label = { Text("Hora") },
                trailingIcon = {
                    Icon(Icons.Default.DateRange, contentDescription = null,
                        modifier = Modifier.clickable { showTimePicker { hora = it } })
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = motivo,
                onValueChange = { motivo = it },
                label = { Text("Motivo de la consulta") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            ExposedDropdownMenuBox(
                expanded = expandedDoctor,
                onExpandedChange = { expandedDoctor = !expandedDoctor },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = doctor,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Doctor") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDoctor) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandedDoctor,
                    onDismissRequest = { expandedDoctor = false }
                ) {
                    doctores.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                doctor = it
                                expandedDoctor = false
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val cita = Cita(fecha, hora, motivo, doctor)
                    createCita(db, cita,
                        onSuccess = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Cita agendada con éxito")
                            }
                            fecha = ""; hora = ""; motivo = ""; doctor = ""
                            cargarCitas()
                        },
                        onError = {
                            scope.launch {
                                snackbarHostState.showSnackbar("Error: ${it.message}")
                            }
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agendar Cita")
            }
        }
    }
}

// Activity que carga la pantalla
class Myappointments : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        val db = FirebaseFirestore.getInstance()
        setContent {
            MaterialTheme {
                AgendarCitaScreen(db)
            }
        }
    }
}
