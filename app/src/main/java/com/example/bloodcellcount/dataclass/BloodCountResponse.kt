package com.example.bloodcellcount.dataclass

import java.io.Serializable

data class BloodCountResponse(
    var bloodCell: BloodCell? = null,
    val bboxes: MutableList<bbox>
):Serializable{
}