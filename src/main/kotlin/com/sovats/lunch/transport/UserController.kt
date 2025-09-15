package com.sovats.lunch.transport

import com.sovats.lunch.api.UserApi
import com.sovats.lunch.model.UserDto
import com.sovats.lunch.model.UserRoleDto
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class UserController : UserApi {
    override fun getUser(id: Long): ResponseEntity<UserDto> {
        val user = UserDto(
            id = id,
            role = UserRoleDto.ADMIN,
            firstName = "asdasd",
            lastName = "iwegfwef",
            username = "iuwweghuiw",
        )

        return ResponseEntity(user, HttpStatus.OK)
    }
}