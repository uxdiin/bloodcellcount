package com.example.bloodcellcount.dataclass

data class BackboneStatisticPage(
    var count: Int? = null,
    var next: String? = null,
    var previous: String? = null,
    var results: MutableList<BackboneStatistic>? = null
)
