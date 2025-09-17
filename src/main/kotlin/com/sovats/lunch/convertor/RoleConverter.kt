package com.sovats.lunch.convertor

import com.sovats.lunch.model.UserRole
import com.sovats.lunch.model.UserRoleDto
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component


@Component
class RoleToDtoConverter : Converter<UserRole, UserRoleDto> {
    override fun convert(from: UserRole): UserRoleDto {
        return when (from) {
            UserRole.ADMIN -> UserRoleDto.ADMIN
            UserRole.USER -> UserRoleDto.USER
            UserRole.GUEST -> UserRoleDto.GUEST
        }
    }
}

@Component
class DtoToRoleConverter : Converter<UserRole, UserRoleDto> {
    override fun convert(from: UserRole): UserRoleDto {
        return when (from) {
            UserRole.ADMIN -> UserRoleDto.ADMIN
            UserRole.USER -> UserRoleDto.USER
            UserRole.GUEST -> UserRoleDto.GUEST
        }
    }
}