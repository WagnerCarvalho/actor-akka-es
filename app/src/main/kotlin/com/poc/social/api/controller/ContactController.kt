package com.poc.social.api.controller

import com.poc.social.api.entities.request.ContactRequest
import com.poc.social.api.entities.response.ContactResponse
import com.poc.social.api.routers.ContactRouter
import com.poc.social.api.service.ContactService
import com.poc.social.api.utils.toFutureResponse
import java.util.concurrent.Future
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ContactController {

    @Autowired
    private lateinit var contactService: ContactService

    @PostMapping(ContactRouter.CREATE_CONTACT)
    fun create(
        @PathVariable("user_id") userId: Long,
        @Valid @RequestBody request: ContactRequest
    ): Future<ContactResponse> {

        return contactService.createContact(userId, request).toFutureResponse()
    }

    @GetMapping(ContactRouter.GET_CONTACT)
    fun get(
        @PathVariable("user_id") userId: Long
    ): Future<ContactResponse> {

        return contactService.getContact(userId).toFutureResponse()
    }
}
