package com.poc.social.api.module

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class ContactResponse(

    @JsonProperty("_source")
    val data: Source = Source()

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Source(

    @JsonProperty("actor")
    val actor: ActorContact = ActorContact()

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ActorContact(

    @JsonProperty("id")
    val id: Long? = null,

    @JsonProperty("friends")
    val friends: Friends = Friends(),

    @JsonProperty("contact")
    val contact: Contact = Contact()

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Friends(

    @JsonProperty("user_id")
    val user_id: List<Long> = listOf()

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Contact(

    @JsonProperty("user_id")
    val user_id: List<Long> = listOf()

)
