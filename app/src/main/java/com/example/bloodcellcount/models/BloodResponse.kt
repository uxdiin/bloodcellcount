package com.example.bloodcellcount.models

import java.io.Serializable

data class BloodResponse(
    var bloodCell: BloodCell? = null,
    val bboxes: MutableList<bbox>
):Serializable{
}