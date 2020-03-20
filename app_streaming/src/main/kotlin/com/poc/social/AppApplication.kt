package com.poc.social

import akka.NotUsed
import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.javadsl.ActorContext
import akka.actor.typed.javadsl.Adapter
import akka.actor.typed.javadsl.Behaviors
import akka.http.javadsl.ConnectHttp
import akka.http.javadsl.Http
import akka.http.javadsl.ServerBinding
import akka.http.javadsl.model.HttpRequest
import akka.http.javadsl.model.HttpResponse
import akka.http.javadsl.server.Route
import akka.stream.Materializer
import akka.stream.javadsl.Flow
import com.poc.social.api.entities.UserRegistry
import com.poc.social.api.routers.UserRouters
import java.net.InetSocketAddress
import java.util.concurrent.CompletionStage
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class AppApplication {
    private val logger = LoggerFactory.getLogger(AppApplication::class.java)

    fun startHttpServer(
        route: Route,
        system: ActorSystem<*>
    ) {
        val classicSystem: akka.actor.ActorSystem = Adapter.toClassic(system)
        val http: Http = Http.get(classicSystem)
        val materializer: Materializer = Materializer.matFromSystem(system)
        val routeFlow: Flow<HttpRequest, HttpResponse, NotUsed> = route.flow(classicSystem, materializer)
        val futureBinding: CompletionStage<ServerBinding> =
            http.bindAndHandle(routeFlow, ConnectHttp.toHost("localhost", 3000), materializer)
        futureBinding.whenComplete { binding: ServerBinding?, exception: Throwable? ->
            if (binding != null) {
                val address: InetSocketAddress = binding.localAddress()
                logger.info("Server online at http://${address.hostString}:${address.port}")
            } else {
                logger.error("Failed to bind HTTP endpoint, terminating system : $exception")
                system.terminate()
            }
        }
    }
}

fun main(args: Array<String>) {
    val rootBehavior =
        Behaviors.setup { context: ActorContext<NotUsed?> ->
            val userRegistryActor: ActorRef<UserRegistry.Command?> =
                context.spawn(UserRegistry.create(), "UserRegistry")
            val userRoutes = UserRouters(context.system, userRegistryActor)
            AppApplication().startHttpServer(userRoutes.userRoutes(), context.system)
            Behaviors.empty()
        }

    ActorSystem.create(rootBehavior, "HelloAkkaHttpServer")
}
