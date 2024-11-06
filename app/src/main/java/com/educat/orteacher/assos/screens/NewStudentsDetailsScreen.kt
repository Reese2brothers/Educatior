package com.educat.orteacher.assos.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.educat.orteacher.assos.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewStudentsDetailsScreen(navController: NavController){
    var name by rememberSaveable { mutableStateOf("") }
    var data by rememberSaveable { mutableStateOf("") }
    var office by rememberSaveable { mutableStateOf("") }
    var performance by rememberSaveable { mutableStateOf("") }
    var behaviour by rememberSaveable { mutableStateOf("") }
    var hobbies by rememberSaveable { mutableStateOf("") }
    var pet by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = Modifier.fillMaxSize().systemBarsPadding().background(brush = Brush.verticalGradient(listOf(
        colorResource(R.color.gradup), colorResource(R.color.graddown)))).verticalScroll(rememberScrollState())) {
        Card(modifier = Modifier.fillMaxWidth().height(300.dp),
            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize().background(colorResource(R.color.rozoviy))) {
                Image(painter = painterResource(R.drawable.baseline_face), contentDescription = "bigavatar",
                    modifier = Modifier.fillMaxSize())
                Column(modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start) {
                        Image(painter = painterResource(R.drawable.solarbackmain), contentDescription = "backtostudentslist",
                            modifier = Modifier.size(60.dp).padding(top = 20.dp, start = 20.dp).clickable {
                                navController.navigate("StudentsScreen")
                            })
                    }
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End) {
                        Box(modifier = Modifier.size(60.dp).padding(end = 20.dp, bottom = 20.dp).clickable {
                           //Choise Photo
                        },
                            contentAlignment = Alignment.Center) {
                            Image(painter = painterResource(R.drawable.ellipse), contentDescription = "ellips",
                                modifier = Modifier.size(60.dp)
                            )
                            Image(painter = painterResource(R.drawable.add), contentDescription = "add",
                                modifier = Modifier.size(25.dp)
                            )
                        }
                    }
                }
            }
        }
        Column(modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 8.dp)) {
            TextField(value = name, onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.rozoviy),
                    cursorColor = colorResource(id = R.color.continuetext),
                    unfocusedIndicatorColor = colorResource(id = R.color.continuetext),
                    focusedIndicatorColor = colorResource(id = R.color.red),
                    focusedTextColor = colorResource(id = R.color.continuetext),
                    unfocusedTextColor = colorResource(id = R.color.continuetext)),
                trailingIcon = { Icon(imageVector = Icons.Default.Close,
                        contentDescription = "Clear text",
                        modifier = Modifier.size(24.dp).clickable { name = "" },
                        tint = colorResource(R.color.continuetext))
                },
                placeholder = {
                    Text("enter name...", color = colorResource(id = R.color.continuetext))
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
            TextField(value = data, onValueChange = { data = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.rozoviy),
                    cursorColor = colorResource(id = R.color.continuetext),
                    unfocusedIndicatorColor = colorResource(id = R.color.continuetext),
                    focusedIndicatorColor = colorResource(id = R.color.red),
                    focusedTextColor = colorResource(id = R.color.continuetext),
                    unfocusedTextColor = colorResource(id = R.color.continuetext)),
                trailingIcon = { Icon(imageVector = Icons.Default.Close,
                    contentDescription = "Clear text",
                    modifier = Modifier.size(24.dp).clickable { data = "" },
                    tint = colorResource(R.color.continuetext))
                },
                placeholder = {
                    Text("enter data...", color = colorResource(id = R.color.continuetext))
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
            TextField(value = office, onValueChange = { office = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.rozoviy),
                    cursorColor = colorResource(id = R.color.continuetext),
                    unfocusedIndicatorColor = colorResource(id = R.color.continuetext),
                    focusedIndicatorColor = colorResource(id = R.color.red),
                    focusedTextColor = colorResource(id = R.color.continuetext),
                    unfocusedTextColor = colorResource(id = R.color.continuetext)),
                trailingIcon = { Icon(imageVector = Icons.Default.Close,
                    contentDescription = "Clear text",
                    modifier = Modifier.size(24.dp).clickable { office = "" },
                    tint = colorResource(R.color.continuetext))
                },
                placeholder = {
                    Text("enter office...", color = colorResource(id = R.color.continuetext))
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
            TextField(value = performance, onValueChange = { performance = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.rozoviy),
                    cursorColor = colorResource(id = R.color.continuetext),
                    unfocusedIndicatorColor = colorResource(id = R.color.continuetext),
                    focusedIndicatorColor = colorResource(id = R.color.red),
                    focusedTextColor = colorResource(id = R.color.continuetext),
                    unfocusedTextColor = colorResource(id = R.color.continuetext)),
                trailingIcon = { Icon(imageVector = Icons.Default.Close,
                    contentDescription = "Clear text",
                    modifier = Modifier.size(24.dp).clickable { performance = "" },
                    tint = colorResource(R.color.continuetext))
                },
                placeholder = {
                    Text("enter performance...", color = colorResource(id = R.color.continuetext))
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
            TextField(value = behaviour, onValueChange = { behaviour = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.rozoviy),
                    cursorColor = colorResource(id = R.color.continuetext),
                    unfocusedIndicatorColor = colorResource(id = R.color.continuetext),
                    focusedIndicatorColor = colorResource(id = R.color.red),
                    focusedTextColor = colorResource(id = R.color.continuetext),
                    unfocusedTextColor = colorResource(id = R.color.continuetext)),
                trailingIcon = { Icon(imageVector = Icons.Default.Close,
                    contentDescription = "Clear text",
                    modifier = Modifier.size(24.dp).clickable { behaviour = "" },
                    tint = colorResource(R.color.continuetext))
                },
                placeholder = {
                    Text("enter behaviour...", color = colorResource(id = R.color.continuetext))
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
            TextField(value = hobbies, onValueChange = { hobbies = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.rozoviy),
                    cursorColor = colorResource(id = R.color.continuetext),
                    unfocusedIndicatorColor = colorResource(id = R.color.continuetext),
                    focusedIndicatorColor = colorResource(id = R.color.red),
                    focusedTextColor = colorResource(id = R.color.continuetext),
                    unfocusedTextColor = colorResource(id = R.color.continuetext)),
                trailingIcon = { Icon(imageVector = Icons.Default.Close,
                    contentDescription = "Clear text",
                    modifier = Modifier.size(24.dp).clickable { hobbies = "" },
                    tint = colorResource(R.color.continuetext))
                },
                placeholder = {
                    Text("enter hobbies...", color = colorResource(id = R.color.continuetext))
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
            TextField(value = pet, onValueChange = { pet = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = colorResource(id = R.color.rozoviy),
                    cursorColor = colorResource(id = R.color.continuetext),
                    unfocusedIndicatorColor = colorResource(id = R.color.continuetext),
                    focusedIndicatorColor = colorResource(id = R.color.red),
                    focusedTextColor = colorResource(id = R.color.continuetext),
                    unfocusedTextColor = colorResource(id = R.color.continuetext)),
                trailingIcon = { Icon(imageVector = Icons.Default.Close,
                    contentDescription = "Clear text",
                    modifier = Modifier.size(24.dp).clickable { pet = "" },
                    tint = colorResource(R.color.continuetext))
                },
                placeholder = {
                    Text("enter pet...", color = colorResource(id = R.color.continuetext))
                },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
        }
        Column {
            Button(onClick = {  },
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, top = 16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(R.color.buttoncontinue)),
                shape = RoundedCornerShape(24.dp),
                elevation = ButtonDefaults.elevation(defaultElevation = 4.dp)){
                Text(text = "Save",
                    color = colorResource(R.color.blacktext),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}