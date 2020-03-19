package com.poc.social.api.service

import com.poc.social.api.entities.Feed
import com.poc.social.api.entities.response.FeedContact
import com.poc.social.api.entities.response.FeedCreateResponse
import com.poc.social.api.entities.response.FeedResponse
import com.poc.social.api.module.ContactResponse
import com.poc.social.api.module.ElasticSearchService
import com.poc.social.api.utils.getError
import io.reactivex.Single
import io.reactivex.Single.just
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FeedService {
    private val logger = LoggerFactory.getLogger(FeedService::class.java)

    @Autowired
    private lateinit var elasticSearchService: ElasticSearchService

    fun createSocialFeed(request: Feed): Single<FeedContact> {
        logger.info("Start createSocialFeed with request: $request")

        return createFeed(request)
            .flatMap {
                getContact(request.id!!)
            }.flatMap {
                just(FeedContact().merge(it, request))
            }.doOnSuccess {
                logger.info("End createSocialFeed with response: $it")
            }.doOnError {
                logger.error("Error createSocialFeed with error: ${it.getError()}")
            }
    }

    fun getContact(id: Long): Single<ContactResponse> {
        logger.info("Start getContact with id: $id")

        return elasticSearchService.getContact(id)
            .doOnSuccess {
                logger.info("End getContact with response: $it")
            }.doOnError {
                logger.info("Error getContact with error: ${it.getError()}")
            }
    }

    fun createFeed(request: Feed): Single<FeedCreateResponse> {
        logger.info("Start create with request: $request")

        return elasticSearchService.createFeed(request)
            .doOnSuccess {
                logger.info("end create with response: $it")
            }.doOnError {
                logger.error("Error create with error: ${it.getError()}")
            }
    }

    fun getFeed(id: Long): Single<FeedResponse> {
        logger.info("Start get with id: $id")

        return elasticSearchService.getFeed(id)
            .doOnSuccess {
                logger.info("End get with response: $it")
            }.doOnError {
                logger.error("Error get with error: ${it.getError()}")
            }
    }
}
