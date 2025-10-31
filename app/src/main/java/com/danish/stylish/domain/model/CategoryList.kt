package com.danish.stylish.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CategoryList(
    val slug: String,
    val name: String,
    val url: String
)