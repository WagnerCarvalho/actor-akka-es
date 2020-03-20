package com.poc.social.api.entities.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ContactRequest(

    @JsonIgnoreProperties("actor")
    var actor: ActorContact = ActorContact()

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ActorContact(

    @JsonProperty("friends")
    val friends: UsersContact = UsersContact(),

    @JsonProperty("contact")
    val contact: UsersContact = UsersContact()

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class UsersContact(

    @JsonProperty("user_id")
    val user_id: List<Long> = listOf()

)
