package com.example.currencyapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class NBPTable(
    val effectiveDate: String,
    val no: String,
    val rates: @RawValue List<Rate>,
    val table: String
) : Parcelable