package com.veterinaria.hospipet

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.veterinaria.hospipet.landing.home.TarjetasInicio
import com.veterinaria.hospipet.landing.init.InitScreen
import com.veterinaria.hospipet.landing.login.LoginScreen
import com.veterinaria.hospipet.landing.options.TarjetasCustomer
import com.veterinaria.hospipet.landing.signup.SingUpScreen

@Composable
fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth) {
    NavHost(navController = navHostController, startDestination = "init") {
        composable("init") {
            InitScreen(
                auth = auth,
                navigateToLogin = { navHostController.navigate("logIn") },
                navigateToHome = { navHostController.navigate("home") }
            )
        }
        composable("logIn") {
            LoginScreen(
                auth = auth,
                navigateToInit = { navHostController.navigate("init") },
                navigateToHome = { navHostController.navigate("home") }
            )
        }
        composable("signUp") {
            SingUpScreen(auth)
        }
        composable("home") {
            TarjetasInicio(
                navigateToMenuCustomer = { navHostController.navigate("menuCustomer") },
                navigateToInit = { navHostController.navigate("init") }
            )
        }
        composable("menuCustomer") {
            TarjetasCustomer(
                navigateToInit = { navHostController.navigate("init") },
                navigateToMenuCustomer = { navHostController.navigate("signUp") }
            )
        }
    }
}