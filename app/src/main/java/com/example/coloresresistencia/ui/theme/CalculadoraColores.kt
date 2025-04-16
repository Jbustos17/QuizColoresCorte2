package com.example.coloresresistencia.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun calculadoraColores(navController: NavController, historial: MutableList<String>) {
    val colores = listOf(
        "Negro" to Color.Black,
        "Marrón" to Color(0xFF8B4513),
        "Rojo" to Color.Red,
        "Naranja" to Color(0xFFFFA500),
        "Amarillo" to Color.Yellow,
        "Verde" to Color.Green,
        "Azul" to Color.Blue,
        "Violeta" to Color(0xFF8A2BE2),
        "Gris" to Color.Gray,
        "Blanco" to Color.White
    )

    val multiplicadores = colores.take(6)
    val tolerancias = listOf(
        "Dorado" to Color(0xFFFFD700),
        "Plateado" to Color(0xFFC0C0C0),
        "Ninguno" to Color.LightGray
    )

    var banda1 by remember { mutableStateOf("LightGray") }
    var banda2 by remember { mutableStateOf("LightGray") }
    var multiplicador by remember { mutableStateOf("Blanco") }
    var tolerancia by remember { mutableStateOf("Ninguno") }
    var resultado by remember { mutableStateOf(0) }
    var toleranciaValor by remember { mutableStateOf("±5%") }
    var botonColor by remember { mutableStateOf(Color.Blue) }

    val mapValores = mapOf(
        "Negro" to 0, "Marrón" to 1, "Rojo" to 2, "Naranja" to 3,
        "Amarillo" to 4, "Verde" to 5, "Azul" to 6, "Violeta" to 7,
        "Gris" to 8, "Blanco" to 9
    )
    val mapMultiplicadores = mapOf(
        "Negro" to 1, "Marrón" to 10, "Rojo" to 100, "Naranja" to 1_000,
        "Amarillo" to 10_000, "Verde" to 100_000
    )
    val mapTolerancias = mapOf(
        "Dorado" to "±5%", "Plateado" to "±10%", "Ninguno" to "±20%"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Calculadora de Resistencia", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DropdownColor("Banda 1", colores, banda1) { banda1 = it }
            DropdownColor("Banda 2", colores, banda2) { banda2 = it }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            DropdownColor("Multiplicador", multiplicadores, multiplicador) { multiplicador = it }
            DropdownTolerancia("Tolerancia", tolerancias, tolerancia) { tolerancia = it }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mueve el resultado aquí arriba
        Text(
            "Resultado: $resultado Ω $toleranciaValor",
            fontSize = 24.sp,  // Aumenta el tamaño del texto
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val valor = (mapValores[banda1]!! * 10 + mapValores[banda2]!!) * mapMultiplicadores[multiplicador]!!
                resultado = valor
                toleranciaValor = mapTolerancias[tolerancia] ?: ""
                historial.add("$valor Ω $toleranciaValor")
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1833C0))
        ) {
            Text("Calcular", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("historial") },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1833C0))
        ) {
            Text("Ver Historial", color = Color.White)
        }
    }
    }


@Composable
fun DropdownColor(label: String, opciones: List<Pair<String, Color>>, seleccion: String, onSeleccion: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, maxLines = 1)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(120.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                .clickable { expanded = true }
                .background(color = opciones.find { it.first == seleccion }?.second ?: Color.LightGray, shape = RoundedCornerShape(10.dp))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = seleccion,
                    color = Color.Black,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White, shape = RoundedCornerShape(10.dp))
        ) {
            opciones.forEach { (nombre, color) ->
                DropdownMenuItem(
                    onClick = {
                        onSeleccion(nombre)
                        expanded = false
                    },
                    leadingIcon = {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(color = color, shape = CircleShape)
                        )
                    },
                    text = {
                        Text(nombre)
                    }
                )
            }
        }
    }
}

@Composable
fun DropdownTolerancia(label: String, opciones: List<Pair<String, Color>>, seleccion: String, onSeleccion: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, maxLines = 1)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .width(120.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))
                .clickable { expanded = true }
                .background(color = opciones.find { it.first == seleccion }?.second ?: Color.LightGray, shape = RoundedCornerShape(10.dp))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = seleccion,
                    color = Color.Black,
                    fontSize = 12.sp,
                    modifier = Modifier.weight(1f)
                )
                Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White, shape = RoundedCornerShape(10.dp))
        ) {
            opciones.forEach { (nombre, color) ->
                DropdownMenuItem(
                    onClick = {
                        onSeleccion(nombre)
                        expanded = false
                    },
                    leadingIcon = {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .background(color = color, shape = CircleShape)
                        )
                    },
                    text = {
                        Text(nombre)
                    }
                )
            }
        }
    }
}
