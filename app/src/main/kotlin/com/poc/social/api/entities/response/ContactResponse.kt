package com.poc.social.api.entities.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.poc.social.api.entities.request.ContactRequest

@JsonIgnoreProperties(ignoreUnknown = true)
data class ContactResponse(

    @JsonProperty("_id")
    val id: Long? = null,

    @JsonProperty("_source")
    val data: SourceContact = SourceContact()

) {
    fun get(userId: Long, request: ContactRequest): ContactResponse {

        return ContactResponse(
            id=userId,
            data = SourceContact(ActorContact(
                Friends(request.actor.friends.user_id.toList()),
                Contact(request.actor.contact.user_id.toList())
            ))
        )
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
data class SourceContact(

    @JsonProperty("actor")
    val actor: ActorContact = ActorContact()

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class ActorContact(

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
