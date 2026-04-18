package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth

data class CakeItem(val name: String, val price: String, val imageUrl: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val firebaseAuth = FirebaseAuth.getInstance()
    val userEmail = firebaseAuth.currentUser?.email ?: "Khách"

    val cakeList = listOf(
        CakeItem("Bánh Sinh Nhật", "250.000đ",
            "https://images.unsplash.com/photo-1558636508-e0db3814bd1d?w=400&q=80"),
        CakeItem("Bánh Kem Dâu", "180.000đ",
            "https://images.unsplash.com/photo-1565958011703-44f9829ba187?w=400&q=80"),
        CakeItem("Bánh Chocolate", "220.000đ",
            "https://images.unsplash.com/photo-1606890737304-57a1ca8a5b62?w=400&q=80"),
        CakeItem("Bánh Macaron", "150.000đ",
            "https://images.unsplash.com/photo-1569864358642-9d1684040f43?w=400&q=80"),
        CakeItem("Bánh Tiramisu", "280.000đ",
            "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400&q=80"),
        CakeItem("Bánh Cupcake", "80.000đ",
            "https://images.unsplash.com/photo-1587668178277-295251f900ce?w=400&q=80"),
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("🎂 CakeApp", fontWeight = FontWeight.Bold,
                        color = Color.White, fontSize = 20.sp)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE87722)),
                actions = {
                    IconButton(onClick = {
                        firebaseAuth.signOut()
                        navController.navigate(Screen.Signin.rout) {
                            popUpTo(Screen.Home.rout) { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.ExitToApp, "Sign Out", tint = Color.White)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFFF8F0))
        ) {
            // Header chào mừng
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(
                        colors = listOf(Color(0xFFE87722), Color(0xFFFF8F00))))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = userEmail.first().uppercaseChar().toString(),
                            fontSize = 22.sp, fontWeight = FontWeight.Bold,
                            color = Color(0xFFE87722)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text("Xin chào! 👋", color = Color.White, fontSize = 14.sp)
                        Text(userEmail, color = Color.White,
                            fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("🍰 Thực đơn hôm nay", fontSize = 20.sp,
                fontWeight = FontWeight.Bold, color = Color(0xFF5D4037),
                modifier = Modifier.padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(cakeList) { cake -> CakeCard(cake = cake) }
            }
        }
    }
}

@Composable
fun CakeCard(cake: CakeItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            AsyncImage(
                model = cake.imageUrl,
                contentDescription = cake.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(cake.name, fontWeight = FontWeight.Bold,
                    fontSize = 14.sp, color = Color(0xFF5D4037))
                Spacer(modifier = Modifier.height(4.dp))
                Text(cake.price, fontSize = 13.sp,
                    color = Color(0xFFE87722), fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(34.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE87722)),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("Đặt mua", fontSize = 12.sp, color = Color.White)
                }
            }
        }
    }
}