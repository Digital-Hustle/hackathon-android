package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.auth.presentation.AuthContract
import com.example.auth.presentation.AuthViewModel
import com.example.auth.presentation.screens.AuthScreen
import com.example.chat.presentation.ChatContract
import com.example.chat.presentation.ChatViewModel
import com.example.chat.presentation.ui.screen.ChatScreen
import com.example.core.mLog
import com.example.main.presentation.MainContract
import com.example.main.presentation.MainViewModel
import com.example.main.presentation.ui.screen.MainScreen
import com.example.profile.presentation.ProfileContract
import com.example.profile.presentation.ProfileViewModel
import com.example.profile.presentation.ui.screen.ProfileScreen
import com.example.send.presentation.SendContract
import com.example.send.presentation.SendViewModel
import com.example.send.presentation.ui.screen.SendScreen

//import androidx.compose.material3.


//import org.koin.androidx.compose.getViewModel

@Composable
fun AuthScreenDestination(navController: NavController) {
    val viewModel: AuthViewModel = hiltViewModel()

    AuthScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is AuthContract.Effect.Navigation.toMain) {
                navController.navigateToMain()
            }
        }
    )
}

@Composable
fun SendScreenDestination(navController: NavController) {
    val viewModel: SendViewModel = hiltViewModel()

    SendScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is SendContract.Effect.Navigation.ToMain) {
                navController.navigateToMain()
            }
            if (navigationEffect is SendContract.Effect.Navigation.ToAuth) {
                navController.navigateToAuth()
            }
            if (navigationEffect is SendContract.Effect.Navigation.ToProfile) {
                navController.navigateToProfile()
            }
        }
    )
}


@Composable
fun MainScreenDestination(navController: NavController) {
    val viewModel: MainViewModel = hiltViewModel()
    mLog("TITLE","TYT")
    MainScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->

            if (navigationEffect is MainContract.Effect.Navigation.ToSend) {
                navController.navigateToSend()
            }
            if (navigationEffect is MainContract.Effect.Navigation.ToProfile) {
                navController.navigateToProfile()
            }
            if (navigationEffect is MainContract.Effect.Navigation.ToAuth) {
                mLog("ЯТ ТУТУ")
                navController.navigateToAuth()
            }

        }
    )

}

@Composable
fun ProfileDestination(navController: NavController) {
    val viewModel: ProfileViewModel = hiltViewModel()
    ProfileScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is ProfileContract.Effect.Navigation.ToMain) {
                navController.navigateToMain()
            }
            if (navigationEffect is ProfileContract.Effect.Navigation.ToChat) {
                navController.navigateToChat(navigationEffect.chatId)
            }

        },
    )
}


@Composable
fun ChatScreenDestination(chatId: Int, navController: NavController) {

    val viewModel: ChatViewModel = hiltViewModel()

//    viewModel.setChatID(chatId)
    DisposableEffect(chatId) {
//        viewModel.subscribeToMessages(chatId)

        onDispose {
            viewModel.closedChat()
        }
    }
    LaunchedEffect(chatId) {
        viewModel.setChatID(chatId)
    }
    ChatScreen(
        state = viewModel.viewState.collectAsState().value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigationEffect ->
            if (navigationEffect is ChatContract.Effect.Navigation.ToProfile) {
                navController.navigateToProfile()
            }

        }
    )
}


fun NavController.navigateToMain() {
    navigate(route = Navigation.Routes.MAIN)
}

fun NavController.navigateToProfile() {
    navigate(route = Navigation.Routes.PROFILE)
}


fun NavController.navigateToAuth() {
    navigate(route = Navigation.Routes.AUTH)
}

fun NavController.navigateToSend(){
    navigate(route = Navigation.Routes.SEND)
}

fun NavController.navigateToChat(chatId: Int) {
    navigate(
        Navigation.Routes.CHAT.replace(
            "{${Navigation.Args.CHAT_ID}}", chatId.toString()
        )
    )
}