package com.example.currencyapp.data.models.tableC

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class TableC(
    val effectiveDate: String,
    val no: String,
    val rates: @RawValue List<RateC>,
    val table: String,
    val tradingDate: String
): Parcelable