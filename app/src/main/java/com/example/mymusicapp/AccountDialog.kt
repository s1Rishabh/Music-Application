package com.example.mymusicapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties


//This dialog open will hold the state of if it is open or not
@Composable
fun AccountDialog(dialogopen: MutableState<Boolean>) {
    if (dialogopen.value) {
        //Displayalltheinformation
        AlertDialog(onDismissRequest = { dialogopen.value = false }, confirmButton =
        {
            TextButton(onClick = { dialogopen.value = false }) {
                Text("Confirm")
            }

        }, dismissButton =
        {
            TextButton(onClick = { dialogopen.value = false }) {
                Text("Dismiss")
            }

        },
            title = { Text("Add Account") },
            text = {
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(16.dp), verticalArrangement = Arrangement.Center
                )
                {
                    TextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.padding(16.dp),
                        label = { Text(text = "Email") })
                    TextField(
                        value = "",
                        onValueChange = {},
                        modifier = Modifier.padding(8.dp),
                        label = { Text(text = "Password") })
                }
            },
            modifier = Modifier.fillMaxWidth().background(MaterialTheme.colors.primarySurface).padding(8.dp) ,
            shape = RoundedCornerShape(5.dp) ,
            backgroundColor = Color.White ,
            properties =  DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        )

    }
}