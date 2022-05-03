package com.example.bloodcellcount.dataclass

import java.io.Serializable

data class BloodCell(
    var id: String? = null,
    var name: String? = null,
    var photo: String? = null,
    var backbone: String? = null,
    var numOfBloodCell: Int? = null,
    val bboxes: MutableList<bbox>? = null
) :Serializable{
}