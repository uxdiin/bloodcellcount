package com.example.bloodcellcount.dataclass

import java.io.Serializable

data class LoginResponse(
    var userId: String? = null,
    var token: String? = null,
    var non_field_errors: String? = null
):Serializable
