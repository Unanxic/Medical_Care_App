package com.example.medicalcareapp.navigation.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.screens.add_medication_screens.AddMedicineScreen
import com.example.medicalcareapp.screens.add_medication_screens.ConditionScreen
import com.example.medicalcareapp.screens.add_medication_screens.FormOfMedicineScreen
import com.example.medicalcareapp.screens.add_medication_screens.SuccessfulMedicineSubmitScreen

fun NavGraphBuilder.registerMedicineNavigation(
    navController: NavController,
    updateCurrentScreen: (Screens) -> Unit
) {
    navigation(
        startDestination = Screens.AddMedicine.route,
        route = NavigationGraphs.RegisterMedicineGraph.navRoute
    ) {
        //add medicine screen
        composable(
            route = Screens.AddMedicine.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            }
        ) {
            updateCurrentScreen(Screens.AddMedicine)
            AddMedicineScreen(navController = navController)
        }
        //form of medicine screen
        composable(
            route = Screens.FormOfMedicine.route + "/{medicationName}",
            arguments = listOf(navArgument("medicationName") { type = NavType.StringType }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(500)
                )
            }
        ) { backStackEntry ->
            val medicationName = backStackEntry.arguments?.getString("medicationName") ?: ""
            updateCurrentScreen(Screens.FormOfMedicine)
            FormOfMedicineScreen(navController = navController, medicationName = medicationName)
        }
    }
    //condition screen
    composable(
        route = Screens.Condition.route + "/{medicationName}/{formOfMedicine}",
        arguments = listOf(
            navArgument("medicationName") { type = NavType.StringType },
            navArgument("formOfMedicine") { type = NavType.StringType }
        ),
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        }
    ) { backStackEntry ->
        val medicationName = backStackEntry.arguments?.getString("medicationName") ?: ""
        val formOfMedicine = backStackEntry.arguments?.getString("formOfMedicine") ?: ""
        updateCurrentScreen(Screens.Condition)
        ConditionScreen(
            navController = navController,
            medicationName = medicationName,
            formOfMedicine = formOfMedicine
        )
    }
    //successful medication submit screen
    composable(
        route = Screens.SuccessfulMedicineRegistration.route,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        }
    ) {
        updateCurrentScreen(Screens.SuccessfulMedicineRegistration)
        SuccessfulMedicineSubmitScreen(navController = navController)
    }
}