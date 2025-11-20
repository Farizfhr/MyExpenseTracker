package com.faris165.myexpensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import androidx.navigation.NavType
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val prefs = PrefsManager(this)

            // LIST TRANSAKSI
            val transactions = remember { mutableStateOf(prefs.loadTransactions()) }

            NavHost(
                navController = navController,
                startDestination = "home"
            ) {

                // =======================
                // HOME
                // =======================
                composable("home") {
                    HomeScreen(
                        transactions = transactions.value,
                        onDelete = { id ->
                            prefs.deleteTransaction(id)
                            transactions.value = prefs.loadTransactions()
                        },
                        onAdd = {
                            navController.navigate("addEdit")
                        },
                        onEdit = { trx ->
                            navController.navigate("addEdit?id=${trx.id}")
                        }
                    )
                }

                // =======================
                // ADD / EDIT SCREEN
                // =======================
                composable(
                    route = "addEdit?id={id}",
                    arguments = listOf(
                        navArgument("id") {
                            type = NavType.StringType
                            nullable = true
                            defaultValue = null
                        }
                    )
                ) { backStackEntry ->

                    val trxId = backStackEntry.arguments?.getString("id")
                    val existing = transactions.value.find { it.id == trxId }

                    AddEditScreen(
                        existing = existing,
                        onSave = { savedTrx ->

                            val list = prefs.loadTransactions().toMutableList()

                            val index = list.indexOfFirst { it.id == savedTrx.id }
                            if (index >= 0) {
                                list[index] = savedTrx
                            } else {
                                list.add(savedTrx)
                            }

                            prefs.saveTransactions(list)
                            transactions.value = prefs.loadTransactions()

                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
