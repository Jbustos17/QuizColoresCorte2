package com.example.coloresresistencia.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavegacionApp() {
    val navController = rememberNavController()
    val historial = remember { mutableListOf<String>() }

    NavHost(navController = navController, startDestination = "calculadora") {
        composable("calculadora") {
            calculadoraColores(navController = navController, historial = historial)
        }
        composable("historial") {
            Historial(navController = navController, historial = historial)
        }
    }
}
