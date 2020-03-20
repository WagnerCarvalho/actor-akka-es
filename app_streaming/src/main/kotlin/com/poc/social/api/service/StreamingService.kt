package com.poc.social.api.service

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.javadsl.AbstractBehavior
import akka.actor.typed.javadsl.ActorContext
import akka.actor.typed.javadsl.Behaviors
import akka.actor.typed.javadsl.Receive
import com.poc.social.api.entities.Actor
import com.poc.social.api.entities.NameUser
import com.poc.social.api.entities.request.FeedRequest
import com.poc.social.api.entities.response.FeedsResponse
import java.util.Collections

class StreamingService
    private constructor(context: ActorContext<Command?>?) : AbstractBehavior<StreamingService.Command?>(context) {

    interface Command

    class GetUsers(val replyTo: ActorRef<FeedsResponse?>?) :
        Command

    class CreateUser(val feedRequest: FeedRequest?, val replyTo: ActorRef<ActionPerformed?>?) :
        Command

    class GetUserResponse(val maybeFeedRequest: FeedRequest)

    class GetUser(val id: Long?, val replyTo: ActorRef<GetUserResponse?>?) :
        Command

    class DeleteUser(val id: Long?, val replyTo: ActorRef<ActionPerformed?>?) :
        Command

    class ActionPerformed(val description: String?) : Command

    private val feedRequests: MutableList<FeedRequest?>? = ArrayList()
    override fun createReceive(): Receive<Command?>? {
        return newReceiveBuilder()
            .onMessage(
                GetUsers::class.java) {
                command: GetUsers? -> onGetUsers(command) }
            .onMessage(
                CreateUser::class.java) {

                command: CreateUser? -> onCreateUser(command) }
            .onMessage(GetUser::class.java) {
                command: GetUser? -> onGetUser(command) }
            .onMessage(
                DeleteUser::class.java) {
                command: DeleteUser? -> onDeleteUser(command) }
            .build()
    }

    private fun onGetUsers(command: GetUsers?): Behavior<Command?>? {
        command!!.replyTo!!.tell(
            FeedsResponse(
                Collections.unmodifiableList(
                    ArrayList(feedRequests)
                )
            )
        )
        return this
    }

    private fun onGetUser(command: GetUser?): Behavior<Command?>? {
        val maybeFeed = feedRequests!!.stream()
            .filter { user: FeedRequest? -> user!!.id == command!!.id }
            .findFirst()
        command!!.replyTo!!.tell(
            when (maybeFeed.isPresent) {
                true -> GetUserResponse(maybeFeed.get())
                else -> GetUserResponse(FeedRequest(0, 0, Actor(NameUser()), "", "", listOf(), listOf()))
            }

        )
        return this
    }

    private fun onCreateUser(command: CreateUser?): Behavior<Command?>? {
        feedRequests!!.add(command!!.feedRequest)
        command.replyTo!!.tell(
            ActionPerformed(
                String.format(
                    "User %s created.",
                    command.feedRequest!!.actor.nameUser
                )
            )
        )
        return this
    }

    private fun onDeleteUser(command: DeleteUser?): Behavior<Command?>? {
        feedRequests!!.removeIf { feedRequest: FeedRequest? -> feedRequest!!.id == command!!.id }
        command!!.replyTo!!.tell(
            ActionPerformed(
                String.format(
                    "User %s deleted.",
                    command.id
                )
            )
        )
        return this
    }

    companion object {
        fun create(): Behavior<Command?>? {
            return Behaviors.setup { context: ActorContext<Command?>? ->
                StreamingService(
                    context
                )
            }
        }
    }
}
