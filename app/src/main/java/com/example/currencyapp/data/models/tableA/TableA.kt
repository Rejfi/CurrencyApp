package com.example.currencyapp.data.models.tableA

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class TableA(
    val effectiveDate: String,
    val no: String,
    val rates: @RawValue List<RateA>,
    val table: String
): Parcelable