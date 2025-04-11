package com.example.coloresresistencia.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview (showBackground = true)
@Composable
fun calculadoraColores() {
    val coloresValor = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
    val coloresMultiplicador = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo")
    val coloresTolerancia = listOf("Dorado", "Plateado", "Ninguno")

    var banda1 by remember { mutableStateOf("Negro") }
    var banda2 by remember { mutableStateOf("Negro") }
    var banda3 by remember { mutableStateOf("Negro") }
    var banda4 by remember { mutableStateOf("Dorado") }

    var resultado by remember { mutableStateOf("") }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Calculadora de Resistencia", fontSize = 20.sp)


            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ColorDropdown("Banda 1", coloresValor, banda1) { banda1 = it }
                ColorDropdown("Banda 2", coloresValor, banda2) { banda2 = it }
            }


            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ColorDropdown("Multiplicador", coloresMultiplicador, banda3) { banda3 = it }
                ColorDropdown("Tolerancia", coloresTolerancia, banda4) { banda4 = it }
            }

            Button(
                onClick = {
                    val valor1 = coloresValor.indexOf(banda1)
                    val valor2 = coloresValor.indexOf(banda2)
                    val multiplicadores = listOf(1, 10, 100, 1_000, 10_000)
                    val tolerancias = mapOf("Dorado" to "±5%", "Plateado" to "±10%", "Ninguno" to "±20%")
                    val valorTotal = ((valor1 * 10) + valor2) * (multiplicadores[coloresMultiplicador.indexOf(banda3)])
                    resultado = "$valorTotal Ω ${tolerancias[banda4]}"
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E14A8))
            ) {
                Text("Calcular", color = Color.White)
            }

            if (resultado.isNotEmpty()) {
                Text("Resultado: $resultado", fontSize = 18.sp)
            }
        }
    }
}
@Composable
fun ColorDropdown(
    label: String,
    opciones: List<String>,
    seleccionActual: String,
    onSeleccionCambio: (String) -> Unit
) {
    var expandido by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label)
        Box {
            OutlinedButton(onClick = { expandido = true }) {
                Text(seleccionActual)
            }
            DropdownMenu(
                expanded = expandido,
                onDismissRequest = { expandido = false }
            ) {
                opciones.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            onSeleccionCambio(opcion)
                            expandido = false
                        }
                    )
                }
            }
        }
    }
}