package com.poc.social.api.entities.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.poc.social.api.entities.Actor
import com.poc.social.api.entities.Feed
import com.poc.social.api.entities.NameUser
import com.poc.social.api.module.ContactResponse

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeedCreateResponse(

    @JsonProperty("_index")
    val _index: String = "",

    @JsonProperty("_id")
    val _id: String = "",

    @JsonProperty("result")
    val result: String = ""

)

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeedContact(

    @JsonProperty("id")
    val id: Long? = null,

    @JsonProperty("actor")
    val actor: Actor = Actor(),

    @JsonProperty("region")
    val region: String = "",

    @JsonProperty("verb")
    val verb: String = "",

    @JsonProperty("friends")
    val friends: List<Long> = listOf(),

    @JsonProperty("contact")
    val contact: List<Long> = listOf()

) {
    fun merge(response: ContactResponse, request: Feed): FeedContact {
        response.data.actor.contact.user_id
        return FeedContact(
            id = request.id,
            actor = Actor( nameUser = NameUser(first = request.actor.nameUser.first, last = request.actor.nameUser.last)),
            region = request.region,
            verb = request.verb,
            friends = response.data.actor.friends.user_id,
            contact = response.data.actor.contact.user_id
        )
    }
}