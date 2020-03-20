package com.poc.social.api.entities.request

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.poc.social.api.entities.Actor

@JsonIgnoreProperties(ignoreUnknown = true)
data class FeedRequest(

    @JsonProperty("id")
    val id: Long? = null,

    @JsonProperty("actor_id")
    val actorId: Long? = null,

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
    fun actionSocial(id: Long): FeedRequest {

        return FeedRequest(
            id = id,
            actorId = this.actorId,
            actor = this.actor,
            region = this.region,
            verb = this.verb
        )
    }
}
