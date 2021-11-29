package com.example.core.utils

import java.text.SimpleDateFormat
import java.util.*

fun getStringTime(timeInMillis: Long): String =
    SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timeInMillis))