package com.example.bloodcellcount.dataclass

data class User(
    var id: String? = null,
    var username: String? = null,
    var firstname: String? = null,
    var lastName: String? = null,
    var email: String? = null,
    var userPhoto: UserPhoto? = null
)
