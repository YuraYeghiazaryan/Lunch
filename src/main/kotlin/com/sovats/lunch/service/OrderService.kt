package com.sovats.lunch.service

import com.sovats.lunch.persistence.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
) {
    fun createOrder(teamId: Long, createdByUserId: Long, contextUrl: String?) {
        orderRepository.insert(teamId, createdByUserId, contextUrl)
    }
}
