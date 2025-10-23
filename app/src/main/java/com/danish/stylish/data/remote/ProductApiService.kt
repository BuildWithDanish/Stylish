package com.danish.stylish.data.remote

import io.ktor.client.HttpClient
import javax.inject.Inject

class ProductApiService @Inject constructor(private val client: HttpClient){

}