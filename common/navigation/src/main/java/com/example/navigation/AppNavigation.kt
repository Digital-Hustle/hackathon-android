package com.example.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigation.Navigation.Args.CHAT_ID
import com.example.network.presentation.TokenViewModel
import kotlinx.coroutines.launch

@Composable
fun AppNavigation(startDestination: String) {

    val navController = rememberNavController()
    val tokenViewModel: TokenViewModel = hiltViewModel()



    LaunchedEffect(Unit) {
        launch {
            tokenViewModel.token.collect { newToken ->
                if (newToken == null) {
                    navController.navigate(Navigation.Routes.AUTH) {
                        popUpTo(0)
                    }
                }
            }
        }
    }



    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(
            route = Navigation.Routes.PROFILE
        ) {
            SelfProfileDestination(navController)
        }


        composable(route = Navigation.Routes.MAIN) {
            MainScreenDestination(navController)
        }
        composable(route = Navigation.Routes.AUTH) {
            AuthScreenDestination(navController)

        }
        composable(route = Navigation.Routes.SEND) {
//            AuthScreenDestination(navController)

        }
        composable(route = Navigation.Routes.REPORT) {
//            AuthScreenDestination(navController)

        }






        composable(
            Navigation.Routes.CHAT,
            arguments = listOf(
                navArgument(name = CHAT_ID) {
                    type = NavType.IntType
                })
        ) { backStackEntry ->
            val chatId = backStackEntry.arguments?.getInt(CHAT_ID) ?: 0

            ChatScreenDestination(chatId, navController)
        }
    }
}

object Navigation {

    object Args {
        const val CHAT_ID = "chat_id"
    }

    object Routes {
        const val MAIN = "main"
        const val AUTH = "auth"
        const val PROFILE = "profile"
        const val CHAT = "chat/{$CHAT_ID}"
        const val SEND = "send"
        const val REPORT  = "report"

    }


}

