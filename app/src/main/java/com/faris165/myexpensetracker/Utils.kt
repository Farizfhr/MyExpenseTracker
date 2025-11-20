package com.faris165.myexpensetracker

fun formatRupiah(value: Int): String {
    return "Rp " + "%,d".format(value).replace(",", ".")
}
