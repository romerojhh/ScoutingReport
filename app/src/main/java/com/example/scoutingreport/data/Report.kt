package com.example.scoutingreport.data

data class Report(
    val fieldName: String,
    val pestType: String,
    val pestName: String,
    val severity: Int,
    val notes: String?,
    val recommendations: String?
)
