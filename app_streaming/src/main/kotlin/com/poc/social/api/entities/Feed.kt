package com.poc.social.api.entities

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Feed(

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
    fun actionSocial(id: Long): Feed {

        return Feed(
            id = id,
            actorId = this.actorId,
            actor = this.actor,
            region = this.region,
            verb = this.verb
        )
    }
}
