package com.faris165.myexpensetracker

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PrefsManager(context: Context) {

    private val prefs = context.getSharedPreferences("expense_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun loadTransactions(): List<Transaction> {
        val json = prefs.getString("transactions", null) ?: return emptyList()
        val type = object : TypeToken<List<Transaction>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveTransactions(list: List<Transaction>) {
        val json = gson.toJson(list)
        prefs.edit().putString("transactions", json).apply()
    }

    fun deleteTransaction(id: String) {
        val list = loadTransactions()
        val newList = list.filter { it.id != id }
        saveTransactions(newList)
    }
}
