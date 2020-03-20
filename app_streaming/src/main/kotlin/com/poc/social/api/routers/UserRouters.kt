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
import com.poc.social.api.entities.Feed
import com.poc.social.api.entities.Feeds
import com.poc.social.api.entities.UserRegistry.ActionPerformed
import com.poc.social.api.entities.UserRegistry.Command
import com.poc.social.api.entities.UserRegistry.CreateUser
import com.poc.social.api.entities.UserRegistry.DeleteUser
import com.poc.social.api.entities.UserRegistry.GetUser
import com.poc.social.api.entities.UserRegistry.GetUserResponse
import com.poc.social.api.entities.UserRegistry.GetUsers
import java.time.Duration
import java.util.concurrent.CompletionStage

class UserRouters(
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

    private fun getUsers(): CompletionStage<Feeds?>? {
        return AskPattern.ask<Command, Feeds?>(
            userRegistryActor,
            Function { replyTo: ActorRef<Feeds?>? -> GetUsers(replyTo) },
            askTimeout,
            scheduler
        )
    }

    private fun createUser(request: Feed): CompletionStage<ActionPerformed?>? {
        return AskPattern.ask<Command, ActionPerformed?>(
            userRegistryActor,
            Function { ref: ActorRef<ActionPerformed?>? -> CreateUser(request, ref) },
            askTimeout,
            scheduler
        )
    }

    fun userRoutes(): Route {
        return pathPrefix("feeds") {
            concat(
                pathEnd {
                    concat(
                        get {
                            onSuccess(getUsers()) { feeds: Feeds? ->
                                complete(StatusCodes.OK, feeds, Jackson.marshaller())
                            }
                        },
                        post {
                            entity(Jackson.unmarshaller(Feed::class.java)) { request: Feed? ->
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
                                    complete(StatusCodes.OK, performed!!.maybeFeed, Jackson.marshaller())
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
