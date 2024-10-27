package com.codewithfk.travenor.ui.feature.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codewithfk.travenor.ui.navigation.NavRouts
import io.ktor.websocket.Frame
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = koinViewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {

        val state = viewModel.uiState.collectAsState()

        Column(
            Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            when (val registerState = state.value) {
                LoginState.Loading -> {
                    CircularProgressIndicator()
                    Frame.Text("Loading")
                }

                is LoginState.Error -> {
                    Text(registerState.message)
                    Button(onClick = {
                        viewModel.retry()
                    }) {
                        Text("Retry")
                    }
                }

                LoginState.Success -> {
                    LaunchedEffect(Unit) {
                        navController.navigate(NavRouts.Home.route){
                            popUpTo(NavRouts.Login.route){
                                inclusive = true
                            }
                        }
                    }
                }

                LoginState.Nothing -> {
                    Column(
                        Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Login",
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        )
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password") },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            visualTransformation = PasswordVisualTransformation()
                        )

                        Button(
                            onClick = { viewModel.login(email, password) },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            enabled = email.isNotEmpty() && password.isNotEmpty()
                        ) {
                            Text("Login")
                        }
                        TextButton(onClick = { navController.navigate(NavRouts.Register.route) }) {
                            Text("Don't have an account? Register")
                        }
                    }
                }
            }
        }


    }
}