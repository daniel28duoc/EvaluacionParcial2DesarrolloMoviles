package com.example.proyectoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectoapp.ui.screens.contact.ContactScreen
import com.example.proyectoapp.ui.screens.contact.ContactViewModel
import com.example.proyectoapp.ui.screens.home.HomeScreen
import com.example.proyectoapp.ui.screens.project.ProjectFormScreen
import com.example.proyectoapp.ui.screens.service.ServiceFormScreen
import com.example.proyectoapp.ui.screens.service.ServiceFormViewModel

@Composable
fun AppNavigation(contactViewModel: ContactViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                onNavigateToContact = {
                    navController.navigate("contact")
                },
                onNavigateToProject = {
                    navController.navigate("project_form")
                },
                onNavigateToService = {
                    navController.navigate("service_form")
                }
            )
        }
        composable("contact") {
            ContactScreen(
                viewModel = contactViewModel,
                onNavigateToProjectForm = {
                    navController.navigate("project_form")
                },
                onNavigateToServiceForm = {
                    navController.navigate("service_form")
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
        composable("service_form") {
            ServiceFormScreen(
                viewModel = androidx.lifecycle.viewmodel.compose.viewModel<ServiceFormViewModel>(),
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

            composable("contact") {
                ContactScreen(
                    viewModel = contactViewModel,
                    onNavigateToProjectForm = {
                        navController.navigate("project_form")
                    },
                    onNavigateToServiceForm = {
                        navController.navigate("service_form")
                    },
                    onNavigateBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
