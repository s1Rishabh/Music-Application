package com.example.mymusicapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mymusicapp.AccountDialog
import com.example.mymusicapp.AccountView
import com.example.mymusicapp.MainViewModel
import com.example.mymusicapp.Screen
import com.example.mymusicapp.screenInDrawer
import com.example.mymusicapp.subscriptionView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainView() {
    val scaffoldstate: ScaffoldState = rememberScaffoldState()
    //open and clossing the drawer will be a coroutinescope
    val scope: CoroutineScope = rememberCoroutineScope()

    // This is needed to check where my current route is at that moment to compare it with item.dRoute
    //Allows us to find at which view we really are.
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val viewModel: MainViewModel = viewModel()

    val currentScreen = remember {
        viewModel.currentScreen.value
    }

    val title = remember {
        mutableStateOf(currentScreen.title)
    }

    val dialogopen = remember {
        mutableStateOf(false)
    }

    Scaffold( bottomBar = {},
        topBar = {
            TopAppBar(title = { Text(title.value) },
                navigationIcon = {
                    IconButton(onClick = {
                        //open the drawer
                        scope.launch {
                            scaffoldstate.drawerState.open()
                        }

                    }) {
                        Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "Menu")
                    }
                }
            )
        },
        scaffoldState = scaffoldstate,
        drawerContent = {
            LazyColumn(Modifier.padding(16.dp)) {
                items(screenInDrawer) { item ->
                    DrawItem(selected = currentRoute == item.dRoute, item = item) {
                        scope.launch {
                            //To close our drawer
                            scaffoldstate.drawerState.close()
                        }
                        if (item.dRoute == "add_account") {
                            dialogopen.value = true
                        } else {
                            controller.navigate(item.dRoute)
                        }
                    }
                }
            }
        }
    ) {
        Navigation(navController = controller, viewModel = viewModel, pd = it)
        AccountDialog(dialogopen = dialogopen)
    }
}

//Check item: Screen.DrawerScreen we are just taking 3 options and setting them up using below code.
@Composable
fun DrawItem(
    selected: Boolean,
    item: Screen.DrawerScreen,
    onDrawerItemClicked: () -> Unit
) {
    //To hightlight the one that is selected
    val background = if (selected) Color.DarkGray else Color.White

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp)
            .background(background)
            .clickable
            {
                onDrawerItemClicked()
            }) {

        // Icon first then the text..

        Icon(
            painter = painterResource(id = item.icon), contentDescription = item.dTitle,
            Modifier.padding(end = 8.dp, top = 4.dp)

        )
        Text(
            text = item.dTitle,
            style = MaterialTheme.typography.h5
        )
    }
}


@Composable
fun Navigation(navController: NavController, viewModel: MainViewModel, pd: PaddingValues) {

    NavHost(
        navController = navController as NavHostController,
        startDestination = Screen.DrawerScreen.AddAccount.route,
        Modifier.padding(pd)
    ) {

        composable(Screen.DrawerScreen.Account.route) {
            AccountView()
        }

        composable(Screen.DrawerScreen.Subscription.route) {
            subscriptionView()
        }

    }
}