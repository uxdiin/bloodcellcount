package com.example.bloodcellcount.models

data class BloodPage(
    var count: Int? = null,
    var next: String? = null,
    var previous: String? = null,
    var results: MutableList<BloodCell>? = null
) {
}