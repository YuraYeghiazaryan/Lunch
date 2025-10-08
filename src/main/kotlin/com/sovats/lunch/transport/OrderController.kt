package com.sovats.lunch.transport

import com.sovats.lunch.api.OrderApi
import com.sovats.lunch.model.OrderDetailsDto
import com.sovats.lunch.model.OrderDto
import com.sovats.lunch.model.OrderStatus
import com.sovats.lunch.model.OrderStatusDto
import com.sovats.lunch.service.OrderService
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController (
    private val orderService: OrderService,
    private val conversionService: ConversionService
): OrderApi {

    override fun createOrder(xUserId: Long, teamId: Long, orderDetailsDto: OrderDetailsDto): ResponseEntity<OrderDto> {
        val order = this.orderService.createOrder(teamId, xUserId, orderDetailsDto.contextUrl)
        val orderDto: OrderDto = conversionService.convert(order, OrderDto::class.java)
            ?: throw IllegalArgumentException("Order could not be created")

        return ResponseEntity.status(201).body(orderDto)
    }

    override fun setOrderStatus(orderId: Long, orderStatusDto: OrderStatusDto): ResponseEntity<Unit> {
        val orderStatus: OrderStatus = conversionService.convert(orderStatusDto, OrderStatus::class.java)
            ?: throw IllegalArgumentException("Invalid status ${orderStatusDto.name}")

        this.orderService.setOrderStatus(orderId, orderStatus)
        return ResponseEntity.ok().build()
    }

    override fun deleteOrder(orderId: Long): ResponseEntity<Unit> {
        this.orderService.deleteOrder(orderId)
        return ResponseEntity.ok().build()
    }

    override fun editOrderDetails(orderId: Long, orderDetailsDto: OrderDetailsDto): ResponseEntity<Unit> {
        this.orderService.editOrderDetails(orderId, orderDetailsDto.contextUrl)
        return ResponseEntity.ok().build()
    }
}
