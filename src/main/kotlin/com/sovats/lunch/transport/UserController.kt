package com.sovats.lunch.transport

import com.sovats.lunch.api.UserApi
import com.sovats.lunch.model.UserDto
import com.sovats.lunch.service.UserService
import org.springframework.core.convert.ConversionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class UserController(
    private val userService: UserService,
    private val conversionService: ConversionService
): UserApi {

    override fun getUser(id: Long): ResponseEntity<UserDto> {
        val user = this.userService.getUserById(id)
        val userDto: UserDto = this.conversionService.convert(user, UserDto::class.java)
            ?: throw IllegalArgumentException("User $user can't be converted to the UserDto.")

        return ResponseEntity(userDto, HttpStatus.OK)
    }
}