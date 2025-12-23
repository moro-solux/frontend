package com.solux.moro.core.util

fun Int.toStringWithFormat(): String = java.text.DecimalFormat("#,###").format(this)
