package com.veterinaria.hospipet

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.veterinaria.hospipet.landing.init.InitScreen
import com.veterinaria.hospipet.landing.login.LoginScreen
import com.veterinaria.hospipet.landing.signup.SingUpScreen

@Composable

fun NavigationWrapper(navHostController: NavHostController, auth: FirebaseAuth) {

    NavHost(navController = navHostController, startDestination = "init" ){
        composable("init") {
            InitScreen(auth = auth,
                        navigateToLogin = {navHostController.navigate("logIn")},
                        navigateToSignUp = {navHostController.navigate("signUp")})
        }
        composable("logIn"){
            LoginScreen(auth = auth,
                navigateToSignUp = {navHostController.navigate("signUp")})
        }
        composable("signUp"){
            SingUpScreen(auth)
        }
    }
}


