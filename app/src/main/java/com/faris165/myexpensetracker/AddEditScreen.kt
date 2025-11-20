package com.faris165.myexpensetracker

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun AddEditScreen(
    existing: Transaction?,
    onSave: (Transaction) -> Unit
) {
    val isEdit = existing != null
    val title = if (isEdit) "Edit Transaksi" else "Tambah Transaksi"

    var name by remember { mutableStateOf(existing?.name ?: "") }
    var amount by remember { mutableStateOf(existing?.amount?.toString() ?: "") }
    var category by remember { mutableStateOf(existing?.category ?: "") }
    var date by remember { mutableStateOf(existing?.date ?: "") }

    val ctx = LocalContext.current

    Column(Modifier.padding(20.dp)) {

        // =======================
        //      TITLE
        // =======================
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama Transaksi") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Nominal (Rp)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Kategori") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Tanggal") },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                TextButton(onClick = {
                    val c = Calendar.getInstance()
                    DatePickerDialog(
                        ctx,
                        { _, y, m, d -> date = "$d/${m + 1}/$y" },
                        c[Calendar.YEAR], c[Calendar.MONTH], c[Calendar.DAY_OF_MONTH]
                    ).show()
                }) { Text("Pilih") }
            }
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                onSave(
                    Transaction(
                        id = existing?.id ?: System.currentTimeMillis().toString(),
                        name = name,
                        amount = amount.toIntOrNull() ?: 0,
                        category = category,
                        date = date
                    )
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEdit) "Simpan Perubahan" else "Tambah")
        }
    }
}
