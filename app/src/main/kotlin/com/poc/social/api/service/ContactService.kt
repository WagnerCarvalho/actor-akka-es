package com.poc.social.api.service

import com.poc.social.api.entities.request.ContactRequest
import com.poc.social.api.entities.response.ContactResponse
import com.poc.social.api.module.es.ElasticSearchService
import com.poc.social.api.utils.getError
import io.reactivex.Single
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ContactService {
    val logger = LoggerFactory.getLogger(ContactService::class.java)

    @Autowired
    private lateinit var elasticSearchService: ElasticSearchService

    fun createContact(userId: Long, request: ContactRequest): Single<ContactResponse> {
        logger.info("Start createContact by userId: $userId with request: $request")

        return elasticSearchService.createContact(userId, request)
            .doOnSuccess {
                logger.info("End createContact by userId: $userId with response: $it")
            }.doOnError {
                logger.error("Error createContact by userId: $userId with error: ${it.getError()}")
            }
    }

    fun getContact(userId: Long): Single<ContactResponse> {
        logger.info("Start getContact by userId: $userId")

        return elasticSearchService.getContact(userId)
            .doOnSuccess {
                logger.info("End getContact by userId: $userId with response: $it")
            }.doOnError {
                logger.error("Error getContact by userId: $userId with error: ${it.getError()}")
            }
    }
}
