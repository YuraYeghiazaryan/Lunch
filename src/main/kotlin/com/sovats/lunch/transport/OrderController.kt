package com.sovats.lunch.transport

import com.sovats.lunch.api.OrderApi
import com.sovats.lunch.model.CreateOrderDto
import com.sovats.lunch.model.OrderDto
import com.sovats.lunch.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController (
    val orderService: OrderService
): OrderApi {
    override fun createOrder(xUserId: Long, teamId: Long, createOrderDto: CreateOrderDto): ResponseEntity<OrderDto> {
        this.orderService.createOrder(teamId, xUserId, createOrderDto.contextUrl)
        return ResponseEntity.status(201).build()
    }
}
