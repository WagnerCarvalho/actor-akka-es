package com.poc.social.api.controller

import com.poc.social.api.entities.Feed
import com.poc.social.api.entities.response.FeedCreateResponse
import com.poc.social.api.entities.response.FeedResponse
import com.poc.social.api.routers.FeedRouter
import com.poc.social.api.service.FeedService
import com.poc.social.api.utils.toFutureResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.concurrent.Future
import javax.validation.Valid

@RestController
class CreateFeedController {

    @Autowired
    private lateinit var feedService: FeedService

    @PostMapping(FeedRouter.CREATE_FEED)
    fun createFeed(
        @Valid @RequestBody request: Feed
    ): Future<FeedCreateResponse> {

        return feedService.createFeed(request).toFutureResponse()
    }

    @GetMapping(FeedRouter.GET_FEED)
    fun get(
        @PathVariable id: Long
    ): Future<FeedResponse> {

        return feedService.getFeed(id).toFutureResponse()
    }
}

