package com.poc.social.api.entities

import akka.actor.typed.ActorRef
import akka.actor.typed.Behavior
import akka.actor.typed.javadsl.AbstractBehavior
import akka.actor.typed.javadsl.ActorContext
import akka.actor.typed.javadsl.Behaviors
import akka.actor.typed.javadsl.Receive
import java.util.*

class UserRegistry
    private constructor(context: ActorContext<Command?>?): AbstractBehavior<UserRegistry.Command?>(context) {

    interface Command

    class GetUsers(val replyTo: ActorRef<Feeds?>?) :
        Command

    class CreateUser(val feed: Feed?, val replyTo: ActorRef<ActionPerformed?>?) :
        Command

    class GetUserResponse(val maybeFeed: Feed)

    class GetUser(val id: Long?, val replyTo: ActorRef<GetUserResponse?>?) :
        Command

    class DeleteUser(val id: Long?, val replyTo: ActorRef<ActionPerformed?>?) :
        Command

    class ActionPerformed(val description: String?) : Command

    private val feeds: MutableList<Feed?>? = ArrayList()
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
            Feeds(
                Collections.unmodifiableList(
                    ArrayList(feeds)
                )
            )
        )
        return this
    }

    private fun onGetUser(command: GetUser?): Behavior<Command?>? {
        val maybeFeed = feeds!!.stream()
            .filter { user: Feed? -> user!!.id == command!!.id }
            .findFirst()
        command!!.replyTo!!.tell(
            when (maybeFeed.isPresent) {
                true -> GetUserResponse(maybeFeed.get())
                else -> GetUserResponse(Feed("",0L, "", Actor( NameUser())))
            }

        )
        return this
    }

    private fun onCreateUser(command: CreateUser?): Behavior<Command?>? {
        feeds!!.add(command!!.feed)
        command.replyTo!!.tell(
            ActionPerformed(
                String.format(
                    "User %s created.",
                    command.feed!!.actor.nameUser
                )
            )
        )
        return this
    }

    private fun onDeleteUser(command: DeleteUser?): Behavior<Command?>? {
        feeds!!.removeIf { feed: Feed? -> feed!!.id == command!!.id }
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
                UserRegistry(
                    context
                )
            }
        }
    }
}