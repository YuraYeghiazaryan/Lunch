package com.sovats.lunch.service

import com.sovats.lunch.model.OrderStatus
import com.sovats.lunch.persistence.entity.Order
import com.sovats.lunch.persistence.repository.OrderRepository
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository,
) {

    fun createOrder(teamId: Long, createdByUserId: Long, contextUrl: String?): Order {
        return orderRepository.insert(teamId, createdByUserId, contextUrl)
    }

    fun setOrderStatus(orderId: Long, orderStatus: OrderStatus) {
        orderRepository.updateStatus(orderId, orderStatus.name)
    }

    fun deleteOrder(orderId: Long) {
        this.orderRepository.deleteOrderById(orderId)
    }

    fun editOrderDetails(orderId: Long, contextUrl: String?) {
        this.orderRepository.updateOrderDetails(orderId, contextUrl)
    }
}
