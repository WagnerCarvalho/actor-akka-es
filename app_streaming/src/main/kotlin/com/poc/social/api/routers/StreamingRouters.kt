package com.poc.social.api.routers

import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Scheduler
import akka.actor.typed.javadsl.AskPattern
import akka.http.javadsl.marshallers.jackson.Jackson
import akka.http.javadsl.model.StatusCodes
import akka.http.javadsl.server.Directives.complete
import akka.http.javadsl.server.Directives.concat
import akka.http.javadsl.server.Directives.delete
import akka.http.javadsl.server.Directives.entity
import akka.http.javadsl.server.Directives.get
import akka.http.javadsl.server.Directives.onSuccess
import akka.http.javadsl.server.Directives.path
import akka.http.javadsl.server.Directives.pathEnd
import akka.http.javadsl.server.Directives.pathPrefix
import akka.http.javadsl.server.Directives.post
import akka.http.javadsl.server.Directives.rejectEmptyResponse
import akka.http.javadsl.server.PathMatchers
import akka.http.javadsl.server.Route
import akka.japi.function.Function
import com.poc.social.api.entities.request.FeedRequest
import com.poc.social.api.entities.response.FeedsResponse
import com.poc.social.api.service.StreamingService.ActionPerformed
import com.poc.social.api.service.StreamingService.Command
import com.poc.social.api.service.StreamingService.CreateUser
import com.poc.social.api.service.StreamingService.DeleteUser
import com.poc.social.api.service.StreamingService.GetUser
import com.poc.social.api.service.StreamingService.GetUserResponse
import com.poc.social.api.service.StreamingService.GetUsers
import java.time.Duration
import java.util.concurrent.CompletionStage

class StreamingRouters(
    system: ActorSystem<*>,
    private val userRegistryActor: ActorRef<Command?>
) {
    private val askTimeout: Duration? = Duration.ofMinutes(5)
    private val scheduler: Scheduler? = system.scheduler()

    private fun getUser(id: Long): CompletionStage<GetUserResponse?>? {
        return AskPattern.ask<Command, GetUserResponse?>(
            userRegistryActor,
            Function { ref: ActorRef<GetUserResponse?>? -> GetUser(id, ref) },
            askTimeout,
            scheduler
        )
    }

    private fun deleteUser(id: Long): CompletionStage<ActionPerformed?>? {
        return AskPattern.ask<Command, ActionPerformed?>(
            userRegistryActor,
            Function { ref: ActorRef<ActionPerformed?>? -> DeleteUser(id, ref) },
            askTimeout,
            scheduler
        )
    }

    private fun getUsers(): CompletionStage<FeedsResponse?>? {
        return AskPattern.ask<Command, FeedsResponse?>(
            userRegistryActor,
            Function { replyTo: ActorRef<FeedsResponse?>? -> GetUsers(replyTo) },
            askTimeout,
            scheduler
        )
    }

    private fun createUser(request: FeedRequest): CompletionStage<ActionPerformed?>? {
        return request.friends.map {
            AskPattern.ask<Command, ActionPerformed?>(
                userRegistryActor,
                Function { ref: ActorRef<ActionPerformed?>? -> CreateUser(request.actionSocial(it), ref) },
                askTimeout,
                scheduler
            )
        }.get(0)
    }

    fun userRoutes(): Route {
        return pathPrefix("feeds") {
            concat(
                path("ping") {
                    get {
                        complete(StatusCodes.OK, "pong => social-feed", Jackson.marshaller())
                    }
                },
                pathEnd {
                    concat(
                        get {
                            onSuccess(getUsers()) { feedsResponse: FeedsResponse? ->
                                complete(StatusCodes.OK, feedsResponse, Jackson.marshaller())
                            }
                        },
                        post {
                            entity(Jackson.unmarshaller(FeedRequest::class.java)) { request: FeedRequest? ->
                                onSuccess(createUser(request!!)) { performed: ActionPerformed? ->
                                    complete(StatusCodes.CREATED, performed, Jackson.marshaller())
                                }
                            }
                        })
                },
                path(PathMatchers.segment()) { id: String ->
                    concat(
                        get {
                            rejectEmptyResponse {
                                onSuccess(getUser(id.toLong()!!)) { performed: GetUserResponse? ->
                                    complete(StatusCodes.OK, performed!!.maybeFeedRequest, Jackson.marshaller())
                                }
                            }
                        },
                        delete {
                            onSuccess(deleteUser(id.toLong()!!)) { performed: ActionPerformed? ->
                                complete(StatusCodes.OK, performed, Jackson.marshaller())
                            }
                        }
                    )
                }
            )
        }
    }
}
