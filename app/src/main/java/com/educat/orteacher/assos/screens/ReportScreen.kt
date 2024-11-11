package com.educat.orteacher.assos.screens


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.Room
import co.yml.charts.common.model.PlotType
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.educat.orteacher.assos.R
import com.educat.orteacher.assos.database.AppDatabase
import kotlin.math.roundToInt


@SuppressLint("DefaultLocale")
@Composable
fun ReportScreen(navController: NavController, selectedItem : String) {
    val context = LocalContext.current
    val db = remember { Room.databaseBuilder(context, AppDatabase::class.java, "database").build() }
    val mathematicsData = db.mathematicsPointsDao().getAll().collectAsState(initial = emptyList())
    val languageData = db.languagePointsDao().getAll().collectAsState(initial = emptyList())
    val literatureData = db.literaturePointsDao().getAll().collectAsState(initial = emptyList())

    when(selectedItem){
        "Mathematics" -> {
            val studentScores = mathematicsData.value
                .groupBy { it.name }
                .mapValues { entry ->
                    if (entry.value.isNotEmpty()) {
                        val student = entry.value.first()
                        val scores = listOfNotNull(
                            student.mon.toIntOrNull(),
                            student.tue.toIntOrNull(),
                            student.wen.toIntOrNull(),
                            student.thu.toIntOrNull(),
                            student.fri.toIntOrNull(),
                            student.sat.toIntOrNull()
                        )
                        scores.sum().toFloat() / scores.size
                    } else {
                        0f
                    }
                }
            val allCategories = listOf("Low level", "Medium level", "High level")
            val completeCategorizedData = allCategories.associateWith { category ->
                studentScores.values.count { averageScore ->
                    when (category) {
                        "Low level" -> averageScore < 5
                        "Medium level" -> averageScore.toInt() in 5..8
                        "High level" -> averageScore > 8
                        else -> false
                    }
                }
            }
            val badStudents = completeCategorizedData["Low level"] ?: 0
            val averageStudents = completeCategorizedData["Medium level"] ?: 0
            val goodStudents = completeCategorizedData["High level"] ?: 0

            val totalStudents = badStudents + averageStudents + goodStudents
            val pieChartData = PieChartData(
                slices = listOf(
                    PieChartData.Slice("Low level", (badStudents.toFloat() / totalStudents) * 100,
                        Color.Red
                    ),
                    PieChartData.Slice("Medium level", (averageStudents.toFloat() / totalStudents) * 100,
                        Color.Blue
                    ),
                    PieChartData.Slice("High level", (goodStudents.toFloat() / totalStudents) * 100,
                        Color.Green
                    )
                ),
                plotType = PlotType.Pie
            )
            val pieChartConfig = PieChartConfig(
                showSliceLabels = true,
                activeSliceAlpha = 1f,
                labelVisible = true,
                labelType = PieChartConfig.LabelType.PERCENTAGE,
                backgroundColor = Color.Transparent
            )
            Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(
                brush = Brush.verticalGradient(listOf(colorResource(R.color.gradup), colorResource(R.color.graddown)))),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(R.drawable.solarbackmain), contentDescription = "delete",
                        modifier = Modifier.size(60.dp).padding(top = 16.dp, start = 16.dp).clickable {
                            navController.navigate("MainScreen/OcenkiOtcheti")
                        })
                }
                Box(modifier = Modifier.size(350.dp),
                    contentAlignment = Alignment.Center) {
                    PieChart(modifier = Modifier.width(300.dp).height(300.dp)
                        .background(brush = Brush.verticalGradient(listOf(colorResource(R.color.graddown), colorResource(R.color.gradup)))),
                        pieChartData = pieChartData,
                        pieChartConfig = pieChartConfig
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                completeCategorizedData.forEach { (category, count) ->
                    val percentage = if (totalStudents != 0) {
                        (count.toFloat() / totalStudents) * 100
                    } else {
                        0f
                    }
                    Column(modifier = Modifier.fillMaxWidth().height(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly){
                        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = category,
                                color = colorResource(R.color.blacktext),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv))
                            )
                            Text(
                                text = "${percentage.roundToInt()}%",
                                color = colorResource(R.color.blacktext),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv))
                            )
                            Text(
                                text = "$count students",
                                color = colorResource(R.color.blacktext),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv))
                            )
                        }
                    }
                }
            }
        }
        "Language" -> {
            val studentScores = languageData.value
                .groupBy { it.name }
                .mapValues { entry ->
                    if (entry.value.isNotEmpty()) {
                        val student = entry.value.first()
                        val scores = listOfNotNull(
                            student.mon.toIntOrNull(),
                            student.tue.toIntOrNull(),
                            student.wen.toIntOrNull(),
                            student.thu.toIntOrNull(),
                            student.fri.toIntOrNull(),
                            student.sat.toIntOrNull()
                        )
                        scores.sum().toFloat() / scores.size
                    } else {
                        0f
                    }
                }
            val allCategories = listOf("Low level", "Medium level", "High level")
            val completeCategorizedData = allCategories.associateWith { category ->
                studentScores.values.count { averageScore ->
                    when (category) {
                        "Low level" -> averageScore < 5
                        "Medium level" -> averageScore.toInt() in 5..8
                        "High level" -> averageScore > 8
                        else -> false
                    }
                }
            }
            val badStudents = completeCategorizedData["Low level"] ?: 0
            val averageStudents = completeCategorizedData["Medium level"] ?: 0
            val goodStudents = completeCategorizedData["High level"] ?: 0

            val totalStudents = badStudents + averageStudents + goodStudents
            val pieChartData = PieChartData(
                slices = listOf(
                    PieChartData.Slice("Low level", (badStudents.toFloat() / totalStudents) * 100,
                        Color.Red
                    ),
                    PieChartData.Slice("Medium level", (averageStudents.toFloat() / totalStudents) * 100,
                        Color.Blue
                    ),
                    PieChartData.Slice("High level", (goodStudents.toFloat() / totalStudents) * 100,
                        Color.Green
                    )
                ),
                plotType = PlotType.Pie
            )
            val pieChartConfig = PieChartConfig(
                showSliceLabels = true,
                activeSliceAlpha = 1f,
                labelVisible = true,
                labelType = PieChartConfig.LabelType.PERCENTAGE,
                backgroundColor = Color.Transparent
            )
            Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(
                brush = Brush.verticalGradient(listOf(colorResource(R.color.gradup), colorResource(R.color.graddown)))),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(R.drawable.solarbackmain), contentDescription = "delete",
                        modifier = Modifier.size(60.dp).padding(top = 16.dp, start = 16.dp).clickable {
                            navController.navigate("MainScreen/OcenkiOtcheti")
                        })
                }
                Box(modifier = Modifier.size(350.dp),
                    contentAlignment = Alignment.Center) {
                    PieChart(modifier = Modifier.width(300.dp).height(300.dp)
                        .background(brush = Brush.verticalGradient(listOf(colorResource(R.color.graddown), colorResource(R.color.gradup)))),
                        pieChartData = pieChartData,
                        pieChartConfig = pieChartConfig
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                completeCategorizedData.forEach { (category, count) ->
                    val percentage = if (totalStudents != 0) {
                        (count.toFloat() / totalStudents) * 100
                    } else {
                        0f
                    }
                    Column(modifier = Modifier.fillMaxWidth().height(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly){
                        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = category,
                                color = colorResource(R.color.blacktext),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv))
                            )
                            Text(
                                text = "${percentage.roundToInt()}%",
                                color = colorResource(R.color.blacktext),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv))
                            )
                            Text(
                                text = "$count students",
                                color = colorResource(R.color.blacktext),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv))
                            )
                        }
                    }
                }
            }
        }
        "Literature" -> {
            val studentScores = literatureData.value
                .groupBy { it.name }
                .mapValues { entry ->
                    if (entry.value.isNotEmpty()) {
                        val student = entry.value.first()
                        val scores = listOfNotNull(
                            student.mon.toIntOrNull(),
                            student.tue.toIntOrNull(),
                            student.wen.toIntOrNull(),
                            student.thu.toIntOrNull(),
                            student.fri.toIntOrNull(),
                            student.sat.toIntOrNull()
                        )
                        scores.sum().toFloat() / scores.size
                    } else {
                        0f
                    }
                }
            val allCategories = listOf("Low level", "Medium level", "High level")
            val completeCategorizedData = allCategories.associateWith { category ->
                studentScores.values.count { averageScore ->
                    when (category) {
                        "Low level" -> averageScore < 5
                        "Medium level" -> averageScore.toInt() in 5..8
                        "High level" -> averageScore > 8
                        else -> false
                    }
                }
            }
            val badStudents = completeCategorizedData["Low level"] ?: 0
            val averageStudents = completeCategorizedData["Medium level"] ?: 0
            val goodStudents = completeCategorizedData["High level"] ?: 0

            val totalStudents = badStudents + averageStudents + goodStudents
            val pieChartData = PieChartData(
                slices = listOf(
                    PieChartData.Slice("Low level", (badStudents.toFloat() / totalStudents) * 100,
                        Color.Red
                    ),
                    PieChartData.Slice("Medium level", (averageStudents.toFloat() / totalStudents) * 100,
                        Color.Blue
                    ),
                    PieChartData.Slice("High level", (goodStudents.toFloat() / totalStudents) * 100,
                        Color.Green
                    )
                ),
                plotType = PlotType.Pie
            )
            val pieChartConfig = PieChartConfig(
                showSliceLabels = true,
                activeSliceAlpha = 1f,
                labelVisible = true,
                labelType = PieChartConfig.LabelType.PERCENTAGE,
                backgroundColor = Color.Transparent
            )
            Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(
                brush = Brush.verticalGradient(listOf(colorResource(R.color.gradup), colorResource(R.color.graddown)))),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically) {
                    Image(painter = painterResource(R.drawable.solarbackmain), contentDescription = "delete",
                        modifier = Modifier.size(60.dp).padding(top = 16.dp, start = 16.dp).clickable {
                            navController.navigate("MainScreen/OcenkiOtcheti")
                        })
                }
                Box(modifier = Modifier.size(350.dp),
                    contentAlignment = Alignment.Center) {
                    PieChart(modifier = Modifier.width(300.dp).height(300.dp)
                        .background(brush = Brush.verticalGradient(listOf(colorResource(R.color.graddown), colorResource(R.color.gradup)))),
                        pieChartData = pieChartData,
                        pieChartConfig = pieChartConfig
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                completeCategorizedData.forEach { (category, count) ->
                    val percentage = if (totalStudents != 0) {
                        (count.toFloat() / totalStudents) * 100
                    } else {
                        0f
                    }
                    Column(modifier = Modifier.fillMaxWidth().height(48.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly){
                        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = category,
                                color = colorResource(R.color.blacktext),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv))
                            )
                            Text(
                                text = "${percentage.roundToInt()}%",
                                color = colorResource(R.color.blacktext),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv))
                            )
                            Text(
                                text = "$count students",
                                color = colorResource(R.color.blacktext),
                                fontSize = 22.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.interv))
                            )
                        }
                    }
                }
            }
        }
    }
}