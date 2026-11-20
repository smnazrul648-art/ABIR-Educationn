package com.abir.education

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Blue = Color(0xFF155EEF)
private val Pale = Color(0xFFF3F7FF)
private val Ink = Color(0xFF172554)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AbirApp() }
    }
}

@Composable
fun AbirApp() {
    var page by remember { mutableStateOf("হোম") }
    var selectedClass by remember { mutableStateOf("৯") }
    var showLoginInfo by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Blue,
            background = Pale,
            surface = Color.White,
            onPrimary = Color.White
        )
    ) {
        Scaffold(
            containerColor = Pale,
            bottomBar = {
                NavigationBar(containerColor = Color.White) {
                    val items = listOf(
                        Triple("হোম", Icons.Default.Home, "হোম"),
                        Triple("কোর্স", Icons.Default.MenuBook, "কোর্স"),
                        Triple("পরীক্ষা", Icons.Default.Quiz, "পরীক্ষা"),
                        Triple("প্রোফাইল", Icons.Default.Person, "প্রোফাইল")
                    )
                    items.forEach { (label, icon, route) ->
                        NavigationBarItem(
                            selected = page == route,
                            onClick = { page = route },
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label) }
                        )
                    }
                }
            }
        ) { padding ->
            Column(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
            ) {
                Header()
                Spacer(Modifier.height(16.dp))
                when (page) {
                    "হোম" -> HomeScreen(selectedClass) { selectedClass = it }
                    "কোর্স" -> CoursesScreen(selectedClass)
                    "পরীক্ষা" -> ExamScreen()
                    "প্রোফাইল" -> ProfileScreen { showLoginInfo = true }
                    "অ্যাডমিন" -> AdminScreen()
                }
            }
            if (showLoginInfo) {
                AlertDialog(
                    onDismissRequest = { showLoginInfo = false },
                    title = { Text("Google দিয়ে লগইন") },
                    text = {
                        Text("এই স্টার্টার প্রজেক্টে Google Sign-In এখনো যুক্ত করা হয়নি। Firebase Console-এ Android app নিবন্ধন, google-services.json এবং নিরাপদ Firebase Authentication সেটআপ করতে হবে।")
                    },
                    confirmButton = {
                        TextButton(onClick = { showLoginInfo = false }) { Text("ঠিক আছে") }
                    }
                )
            }
        }
    }
}

@Composable
fun Header() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(color = Blue, shape = RoundedCornerShape(14.dp)) {
            Icon(
                Icons.Default.School, contentDescription = null,
                tint = Color.White, modifier = Modifier.padding(10.dp).size(28.dp)
            )
        }
        Spacer(Modifier.width(10.dp))
        Column {
            Text("ABIR", color = Blue, fontSize = 23.sp, fontWeight = FontWeight.ExtraBold)
            Text("LEARN • GROW • SUCCEED", color = Color.Gray, fontSize = 9.sp)
        }
        Spacer(Modifier.weight(1f))
        Icon(Icons.Default.NotificationsNone, contentDescription = "নোটিফিকেশন", tint = Ink)
    }
}

@Composable
fun HomeScreen(selectedClass: String, onClassChange: (String) -> Unit) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            Surface(color = Blue, shape = RoundedCornerShape(22.dp)) {
                Column(Modifier.fillMaxWidth().padding(20.dp)) {
                    Text("স্বাগতম শিক্ষার্থী!", color = Color(0xFFDCE8FF))
                    Spacer(Modifier.height(5.dp))
                    Text("আজই শেখা শুরু করো", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text("তোমার পড়াশোনার সঙ্গী — ABIR", color = Color.White.copy(alpha = .85f))
                }
            }
        }
        item { Text("তোমার শ্রেণি নির্বাচন করো", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink) }
        item {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                (1..4).forEach { row ->
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        (1..3).forEach { col ->
                            val n = ((row - 1) * 3 + col).toString()
                            val isSelected = selectedClass == n
                            Surface(
                                modifier = Modifier.weight(1f).clickable { onClassChange(n) },
                                color = if (isSelected) Blue else Color.White,
                                shape = RoundedCornerShape(12.dp),
                                border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDCE6F5))
                            ) {
                                Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(n, color = if (isSelected) Color.White else Ink, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                    Text("শ্রেণি", color = if (isSelected) Color.White else Color.Gray, fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }
        }
        item { Text("জনপ্রিয় বিষয়সমূহ", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Ink) }
        item { SubjectGrid(selectedClass) }
        item {
            Surface(color = Color.White, shape = RoundedCornerShape(16.dp)) {
                Column(Modifier.padding(16.dp)) {
                    Text("তোমার পড়াশোনা", color = Ink, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    LinearProgressIndicator(progress = { .6f }, modifier = Modifier.fillMaxWidth(), color = Blue)
                    Text("ডেমো অগ্রগতি: ৬০%", color = Color.Gray, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun SubjectGrid(className: String) {
    val subjects = listOf("বাংলা", "ইংরেজি", "গণিত", "বিজ্ঞান", "আইসিটি", "সাধারণ জ্ঞান")
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        subjects.chunked(2).forEach { pair ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                pair.forEach { subject ->
                    Surface(
                        modifier = Modifier.weight(1f),
                        color = Color.White,
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Column(Modifier.padding(14.dp)) {
                            Icon(Icons.Default.MenuBook, contentDescription = null, tint = Blue)
                            Spacer(Modifier.height(7.dp))
                            Text(subject, color = Ink, fontWeight = FontWeight.SemiBold)
                            Text("ক্লাস $className • পাঠ ও নোট", color = Color.Gray, fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CoursesScreen(className: String) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("আমার কোর্স", color = Ink, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Text("ক্লাস $className-এর বিষয় বেছে নাও", color = Color.Gray)
        SubjectGrid(className)
        Text("প্রতিটি কোর্সে পাঠ, নোট, PDF ও অনুশীলন যুক্ত করা যাবে।", color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun ExamScreen() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("পরীক্ষা কেন্দ্র", color = Ink, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Surface(color = Color(0xFFEAF1FF), shape = RoundedCornerShape(18.dp)) {
            Column(Modifier.fillMaxWidth().padding(18.dp)) {
                Text("মডেল টেস্ট", color = Blue, fontWeight = FontWeight.Bold)
                Text("MCQ পরীক্ষা", color = Ink, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("নিজের জ্ঞান যাচাই করো", color = Color.Gray)
            }
        }
        InfoRow("মোট প্রশ্ন", "২০টি")
        InfoRow("সময়", "৩০ মিনিট")
        InfoRow("ফলাফল", "পরীক্ষা জমা দেওয়ার পর")
        Text("এটি এখন নমুনা স্ক্রিন। বাস্তব প্রশ্ন, টাইমার ও ফলাফল ডেটাবেস যুক্ত করতে হবে।", color = Color.Gray, fontSize = 12.sp)
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Surface(color = Color.White, shape = RoundedCornerShape(12.dp)) {
        Row(Modifier.fillMaxWidth().padding(14.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label, color = Color.Gray)
            Text(value, color = Ink, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProfileScreen(onGoogleClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Text("আমার প্রোফাইল", color = Ink, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(22.dp))
        Surface(color = Color(0xFFE8F0FF), shape = RoundedCornerShape(50)) {
            Icon(Icons.Default.Person, contentDescription = null, tint = Blue, modifier = Modifier.padding(22.dp).size(48.dp))
        }
        Spacer(Modifier.height(12.dp))
        Text("শিক্ষার্থীর প্রোফাইল", color = Ink, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text("Google অ্যাকাউন্টের নাম ও ছবি", color = Color.Gray, fontSize = 12.sp)
        Spacer(Modifier.height(20.dp))
        Button(onClick = onGoogleClick, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.Default.AccountCircle, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Google দিয়ে লগইন")
        }
        Spacer(Modifier.height(12.dp))
        InfoRow("শ্রেণি", "নির্বাচিত শ্রেণি")
        Spacer(Modifier.height(8.dp))
        InfoRow("পরীক্ষার ফলাফল", "এখনো নেই")
    }
}

@Composable
fun AdminScreen() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Admin Dashboard", color = Ink, fontSize = 23.sp, fontWeight = FontWeight.Bold)
        Text("শুধু অনুমোদিত অ্যাডমিনের জন্য", color = Color.Gray)
        listOf("শিক্ষার্থী পরিচালনা", "কোর্স ও নোট যোগ", "প্রশ্ন তৈরি", "ফলাফল দেখা", "ক্লাস পরিচালনা").forEach {
            Surface(color = Color.White, shape = RoundedCornerShape(12.dp)) {
                Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AdminPanelSettings, contentDescription = null, tint = Blue)
                    Spacer(Modifier.width(12.dp))
                    Text(it, color = Ink, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Blue)
                }
            }
        }
        Text("নিরাপত্তার জন্য Admin role সার্ভার/Firebase Security Rules দিয়ে যাচাই করতে হবে।", color = Color.Gray, fontSize = 12.sp)
    }
}
