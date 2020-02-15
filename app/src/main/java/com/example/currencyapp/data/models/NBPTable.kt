package com.example.currencyapp.data.models

data class NBPTable(
    val effectiveDate: String,
    val no: String,
    val rates: List<Rate>,
    val table: String
)