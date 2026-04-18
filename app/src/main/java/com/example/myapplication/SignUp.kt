package com.example.myapplication

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SignUp(navController: NavController) {
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val bannerImageUrl = "https://images.unsplash.com/photo-1464349095431-e9a21285b5f3?w=800&q=80"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(
                colors = listOf(Color(0xFFFCE4EC), Color(0xFFF8BBD0))))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(220.dp)) {
                AsyncImage(
                    model = bannerImageUrl,
                    contentDescription = "Cake Banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xFFF8BBD0)))))
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🎂 CakeApp", fontSize = 28.sp,
                        fontWeight = FontWeight.Bold, color = Color(0xFF880E4F))
                    Text(text = "Tạo tài khoản mới", fontSize = 14.sp,
                        color = Color(0xFFAD1457))
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Card(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Đăng Ký", fontSize = 24.sp,
                        fontWeight = FontWeight.Bold, color = Color(0xFF880E4F))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Điền thông tin để tạo tài khoản", fontSize = 14.sp,
                        color = Color(0xFF9E9E9E), textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(20.dp))

                    OutlinedTextField(
                        value = email, onValueChange = { email = it },
                        label = { Text("Email") },
                        leadingIcon = { Icon(Icons.Default.Email, null, tint = Color(0xFFE91E8C)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFE91E8C),
                            focusedLabelColor = Color(0xFFE91E8C)),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = password, onValueChange = { password = it },
                        label = { Text("Mật khẩu") },
                        leadingIcon = { Icon(Icons.Default.Lock, null, tint = Color(0xFFE91E8C)) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(if (passwordVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff, null, tint = Color(0xFF9E9E9E))
                            }
                        },
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFE91E8C),
                            focusedLabelColor = Color(0xFFE91E8C)),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    OutlinedTextField(
                        value = confirmPassword, onValueChange = { confirmPassword = it },
                        label = { Text("Xác nhận mật khẩu") },
                        leadingIcon = { Icon(Icons.Default.Lock, null, tint = Color(0xFFE91E8C)) },
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                Icon(if (confirmPasswordVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff, null, tint = Color(0xFF9E9E9E))
                            }
                        },
                        visualTransformation = if (confirmPasswordVisible)
                            VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        isError = confirmPassword.isNotEmpty() && password != confirmPassword,
                        supportingText = {
                            if (confirmPassword.isNotEmpty() && password != confirmPassword)
                                Text("Mật khẩu không khớp", color = Color.Red)
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFFE91E8C),
                            focusedLabelColor = Color(0xFFE91E8C)),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            when {
                                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ->
                                    Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
                                password != confirmPassword ->
                                    Toast.makeText(context, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show()
                                password.length < 6 ->
                                    Toast.makeText(context, "Mật khẩu phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show()
                                else -> {
                                    isLoading = true
                                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                                        .addOnCompleteListener {
                                            isLoading = false
                                            if (it.isSuccessful) {
                                                firebaseAuth.currentUser?.sendEmailVerification()
                                                Toast.makeText(context,
                                                    "Đăng ký thành công! Kiểm tra email xác thực.",
                                                    Toast.LENGTH_LONG).show()
                                                navController.navigate(Screen.Signin.rout) {
                                                    popUpTo(Screen.Signup.rout) { inclusive = true }
                                                }
                                            } else {
                                                Toast.makeText(context,
                                                    "Lỗi: ${it.exception?.message}",
                                                    Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E8C)),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White,
                                modifier = Modifier.size(24.dp))
                        } else {
                            Text("ĐĂNG KÝ", fontSize = 16.sp,
                                fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Đã có tài khoản? ", color = Color(0xFF757575), fontSize = 14.sp)
                TextButton(onClick = { navController.popBackStack() }) {
                    Text("Đăng nhập", color = Color(0xFFE91E8C),
                        fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}