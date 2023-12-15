package com.example.societyapp.domain.model

data class UserDetails(
    var uid: String = "",
    var fullname: String = "",
    var email: String = "",
    var password: String = "",
    var mobileNo: String = "",
    var photoProfileUrl: String = ""
)