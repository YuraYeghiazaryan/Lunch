package com.sovats.lunch.security
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
//import org.springframework.security.web.SecurityFilterChain
//import org.springframework.security.config.http.SessionCreationPolicy
//
//@Configuration
//@EnableWebSecurity
//class SecurityConfig {
//
//    @Bean
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//            .csrf { it.disable() }
//            .authorizeHttpRequests { auth ->
//                auth.requestMatchers("/api/v1/auth/signup").permitAll()
//                auth.anyRequest().authenticated()
//            }
//            .httpBasic {  }
//            .sessionManagement { session ->
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            }
//        return http.build()
//    }
//}
//
