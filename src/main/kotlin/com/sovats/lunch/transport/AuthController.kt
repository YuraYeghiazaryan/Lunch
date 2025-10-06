package com.sovats.lunch.transport

import com.sovats.lunch.api.AuthApi
import com.sovats.lunch.model.SignInDto
import com.sovats.lunch.model.SignupDto
import com.sovats.lunch.model.UserDto
import com.sovats.lunch.persistence.entity.User
import com.sovats.lunch.service.UserService
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val userService: UserService,
    private val conversionService: ConversionService
): AuthApi {

    override fun signup(signupDto: SignupDto): ResponseEntity<UserDto> {
        val savedUser: User = this.userService.createUser(
            signupDto.email,
            signupDto.password,
            signupDto.firstName,
            signupDto.lastName,
        )

        // Map User Entity -> UserDto
        val savedUserDto: UserDto = conversionService.convert(savedUser, UserDto::class.java)
            ?: throw IllegalArgumentException("User $savedUser can't be converted to the UserDto.")

        return ResponseEntity.status(201).body(savedUserDto)
    }

    override fun singIn(signInDto: SignInDto): ResponseEntity<UserDto> {
        val user: User = this.userService.findUserByEmail(signInDto.email)
            ?: return ResponseEntity.badRequest().body(null)

        if (user.password != signInDto.password) {
            return ResponseEntity.badRequest().body(null)
        }

        val userDto: UserDto = conversionService.convert(user, UserDto::class.java)
            ?: throw IllegalArgumentException("User can't be converted to the UserDto.")

        return ResponseEntity.ok().body(userDto)
     }
}
