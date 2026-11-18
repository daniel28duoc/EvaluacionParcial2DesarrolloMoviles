package com.example.proyectoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectoapp.ui.screens.contact.ContactScreen
import com.example.proyectoapp.ui.screens.contact.ContactViewModel
import com.example.proyectoapp.ui.screens.project.ProjectFormScreen

@Composable
fun AppNavigation(contactViewModel: ContactViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "contact") {
        composable("contact") {
            ContactScreen(
                viewModel = contactViewModel,
                onNavigateToProjectForm = {
                    navController.navigate("project_form")
                }
            )
        }
        composable("project_form") {
            ProjectFormScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}