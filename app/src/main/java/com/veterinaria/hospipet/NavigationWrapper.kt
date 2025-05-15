package com.veterinaria.hospipet

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.veterinaria.hospipet.landing.appointment.AgendarCitaScreen
import com.veterinaria.hospipet.landing.home.TarjetasInicio
import com.veterinaria.hospipet.landing.init.InitScreen
import com.veterinaria.hospipet.landing.login.LoginScreen
import com.veterinaria.hospipet.landing.options.TarjetasCustomer
import com.veterinaria.hospipet.landing.options.TarjetasDoctor
import com.veterinaria.hospipet.landing.pets.MyPets
import com.veterinaria.hospipet.landing.pets.RegisterPetScreen
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
                navigateToMenuDoctor = { navHostController.navigate("menuDoctor") },
                navigateToInit = { navHostController.navigate("init") } // << Añadido
            )
        }
        composable("menuCustomer") {
            TarjetasCustomer(
                navigateToInit = { navHostController.navigate("init") },
                navigateToHome = { navHostController.navigate("home") },
                navigateToMenuCustomer = { navHostController.navigate("signUp") },
                navigateToMyPets = { navHostController.navigate("myPets") },
                navigateToCitas = { navHostController.navigate("appointment") }
            )
        }
        composable("menuDoctor") {
            TarjetasDoctor(
                navigateToInit = { navHostController.navigate("init") },
                navigateToHome = { navHostController.navigate("home") },
                navigateToMenuCustomer = { navHostController.navigate("signUp") }
            )
        }
        composable("myPets") {
            MyPets(
                navigateToInit = { navHostController.navigate("init") },
                navigateToMenuCustomer = { navHostController.navigate("menuCustomer")},
                navigateToRegisterPet = { navHostController.navigate("registerPet") }
            )
        }
        composable("registerPet") {
            RegisterPetScreen(db = Firebase.firestore)
        }
        composable("appointment") {
            // Llamamos al composable directamente
            AgendarCitaScreen(db = Firebase.firestore)
        }
    }
}