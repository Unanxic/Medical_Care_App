package com.example.medicalcareapp.navigation.graphs

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.medicalcareapp.navigation.Screens
import com.example.medicalcareapp.screens.add_contacts_screen.screen.AddContactsScreen
import com.example.medicalcareapp.screens.add_contacts_screen.screen.SuccessfulContactSubmitScreen

fun NavGraphBuilder.addContactsNavigation(
    navController: NavController,
    updateCurrentScreen: (Screens) -> Unit
) {
    navigation(
        startDestination = Screens.AddContacts.route,
        route = NavigationGraphs.AddContactsGraph.navRoute
    ) {
        //add contacts screen
        composable(
            route = Screens.AddContacts.route,
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
            updateCurrentScreen(Screens.AddContacts)
            AddContactsScreen(navController = navController)
        }
        //success submit screen
        composable(
            route = Screens.SuccessfulAddContacts.route,
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
            updateCurrentScreen(Screens.SuccessfulAddContacts)
            SuccessfulContactSubmitScreen(navController = navController)
        }
    }
}