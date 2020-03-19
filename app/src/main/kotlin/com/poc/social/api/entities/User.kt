package com.poc.social.api.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(

    @JsonProperty("name")
    val name: String? = "",

    @JsonProperty("age")
    val age: Int? = null,

    @JsonProperty("countryOfRecidence")
    val countryOfResidence: String? = ""

)