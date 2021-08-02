package com.example.bloodcellcount.models

import java.io.Serializable

data class BloodCell(
    var id: String? = null,
    var name: String? = null,
    var photo: String? = null,
    var numOfBloodCell: Int? = null
) :Serializable{
}