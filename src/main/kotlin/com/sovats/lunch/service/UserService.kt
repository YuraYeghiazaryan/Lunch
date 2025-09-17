package com.sovats.lunch.service

import com.sovats.lunch.persistence.entity.User
import com.sovats.lunch.persistence.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun createUser(username: String, password: String, firstName: String, lastName: String, email: String): User {
        val user = User(
            username = username,
            password = password,
            firstName = firstName,
            lastName = lastName,
            email = email
        )

        return this.userRepository.save(user)
    }

    fun getUser(id: Long): User {
        return this.userRepository.findById(id).get()
    }
}