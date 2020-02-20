package com.example.currencyapp.data.models

import kotlinx.android.parcel.RawValue

data class Rate(
    val code: String,
    val currency: String,
    val mid: Double
)