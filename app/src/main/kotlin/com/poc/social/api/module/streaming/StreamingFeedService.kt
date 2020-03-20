package com.poc.social.api.module.streaming

import com.poc.social.api.entities.response.FeedContact
import com.poc.social.api.entities.response.FeedCreateResponse
import com.poc.social.api.utils.getError
import io.reactivex.Single
import io.reactivex.Single.just
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StreamingFeedService {
    private val logger = LoggerFactory.getLogger(StreamingFeedService::class.java)

    @Autowired
    private lateinit var streamingFeed: StreamingFeed

    fun sendFeedSocial(feed: FeedContact): Single<FeedContact> {
        logger.info("Start createFeed with feed: $feed")

        return streamingFeed.sendFeedSocial(feed)
            .flatMap {
                just(feed)
            }.doOnSuccess {
                logger.info("End createFeed with response: $it")
            }.doOnError {
                logger.info("Error createFeed with error: ${it.getError()}")
            }
    }
}