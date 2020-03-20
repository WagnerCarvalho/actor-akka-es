package com.poc.social.api.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class NameUser(

    @JsonProperty("first")
    val first: String = "",

    @JsonProperty("last")
    val last: String = ""

)
