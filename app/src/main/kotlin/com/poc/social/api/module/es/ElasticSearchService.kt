package com.poc.social.api.module.es

import com.poc.social.api.entities.Feed
import com.poc.social.api.entities.response.ContactResponse
import com.poc.social.api.entities.response.FeedCreateResponse
import com.poc.social.api.entities.response.FeedResponse
import com.poc.social.api.utils.getError
import io.reactivex.Single
import java.util.UUID
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ElasticSearchService {
    private val logger = LoggerFactory.getLogger(ElasticSearchService::class.java)

    @Autowired
    private lateinit var elasticSearch: ElasticSearch

    fun createFeed(request: Feed): Single<FeedCreateResponse> {
        logger.info("Start createFeed with request: $request")

        return elasticSearch.createFeed(UUID.randomUUID().toString(), request)
            .doOnSuccess {
                logger.info("End createFeed with response: $it")
            }.doOnError {
                logger.error("Error createFeed with error: ${it.getError()}")
            }
    }

    fun getFeed(id: Long): Single<FeedResponse> {
        logger.info("Start getFeed with id: $id")

        return elasticSearch.getFeed(id)
            .doOnSuccess {
                logger.info("End getFeed with response: $it")
            }.doOnError {
                logger.error("Error getFeed with error: ${it.getError()}")
            }
    }

    fun getContact(id: Long): Single<ContactResponse> {
        logger.info("Start getFeed with id: $id")

        return elasticSearch.getContact(id)
            .doOnSuccess {
                logger.info("End getFeed with response: $it")
            }.doOnError {
                logger.error("Error getFeed with error: ${it.getError()}")
            }
    }
}
