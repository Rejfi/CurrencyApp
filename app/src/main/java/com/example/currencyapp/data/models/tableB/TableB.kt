package com.example.currencyapp.data.models.tableB

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class TableB(
    val effectiveDate: String,
    val no: String,
    val rates: @RawValue List<RateB>,
    val table: String
): Parcelable