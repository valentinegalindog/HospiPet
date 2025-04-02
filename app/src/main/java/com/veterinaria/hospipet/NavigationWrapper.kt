package com.veterinaria.hospipet

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.veterinaria.hospipet.landing.init.InitScreen
import com.veterinaria.hospipet.landing.login.LoginScreen
import com.veterinaria.hospipet.landing.signup.SingUpScreen

@Composable

fun NavigationWrapper(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = "init" ){
        composable("init"){
            InitScreen(
                navigateToSignUp = {navHostController.navigate("signUp")}


            )
        }
        composable("logIn"){
            LoginScreen()
        }
        composable("signUp"){
            SingUpScreen()
        }
    }
}


