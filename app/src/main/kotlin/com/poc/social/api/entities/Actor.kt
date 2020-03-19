package com.poc.social.api.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Actor(

    @JsonProperty("id")
    val id: Long = 0,

    @JsonProperty("name")
    val name: Name = Name()

)