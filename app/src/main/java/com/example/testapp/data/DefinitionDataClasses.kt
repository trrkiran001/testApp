package com.example.testapp.data

import com.google.gson.annotations.SerializedName

data class Definition(
    @SerializedName("sf") val sf: String,
    @SerializedName("lfs") val lfs: List<LongFormObject>
)

data class LongFormObject(
    @SerializedName("lf") val lf: String,
    @SerializedName("freq") val frequency: Int,
    @SerializedName("since") val sinceYear: Int,
    @SerializedName("vars") val variations: List<Variation>
)

data class Variation(
    @SerializedName("lf") val lf: String,
    @SerializedName("freq") val frequency: Int,
    @SerializedName("since") val sinceYear: Int,
)
