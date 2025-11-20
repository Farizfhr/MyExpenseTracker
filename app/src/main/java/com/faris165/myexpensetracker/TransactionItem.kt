package com.faris165.myexpensetracker

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color

@Composable
fun TransactionItem(
    transaction: Transaction,
    onDelete: () -> Unit,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(transaction.name)
            Text("Kategori: ${transaction.category}")
            Text("Tanggal: ${transaction.date}")
            Text("Nominal: Rp ${transaction.amount}")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    "Edit",
                    color = Color.Blue,
                    modifier = Modifier.clickable { onEdit() }
                )
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    "Hapus",
                    color = Color.Red,
                    modifier = Modifier.clickable { onDelete() }
                )
            }
        }
    }
}
