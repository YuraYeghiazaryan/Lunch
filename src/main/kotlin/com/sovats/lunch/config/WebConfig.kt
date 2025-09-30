package com.sovats.lunch.config

import com.sovats.lunch.convertor.TeamToDtoConverter
import com.sovats.lunch.convertor.UserToDtoConverter
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class WebConfig(
    private val userToDtoConverter: UserToDtoConverter,
    private val teamToDtoConverter: TeamToDtoConverter
) : WebMvcConfigurer {

    override fun addFormatters(registry: FormatterRegistry) {
        // TODO add better model mapper service
        registry.addConverter(userToDtoConverter)
        registry.addConverter(teamToDtoConverter)
    }
}
