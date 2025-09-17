package com.sovats.lunch.convertor

import com.sovats.lunch.model.UserDto
import com.sovats.lunch.persistence.entity.User
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component


@Component
class UserToDtoConverter() : Converter<User, UserDto> {

    override fun convert(from: User): UserDto {
        return UserDto(
            id = from.id!!,
            firstName = from.firstName,
            lastName = from.lastName,
            username = from.username,
            email = from.email,
        )
    }
}