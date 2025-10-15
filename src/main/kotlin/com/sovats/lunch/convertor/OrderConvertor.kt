package com.sovats.lunch.convertor

import com.sovats.lunch.model.OrderDto
import com.sovats.lunch.model.OrderStatus
import com.sovats.lunch.model.OrderStatusDto
import com.sovats.lunch.persistence.entity.Order
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.time.ZoneOffset

@Component
class DtoToOrderStatusConvertor: Converter<OrderStatusDto, OrderStatus> {
    override fun convert(from: OrderStatusDto): OrderStatus {
        return when(from) {
            OrderStatusDto.IN_PROGRESS -> OrderStatus.IN_PROGRESS
            OrderStatusDto.ORDERED -> OrderStatus.ORDERED
        }
    }
}

@Component
class OrderStatusToDtoConvertor: Converter<OrderStatus, OrderStatusDto> {
    override fun convert(from: OrderStatus): OrderStatusDto {
        return when(from) {
            OrderStatus.IN_PROGRESS -> OrderStatusDto.IN_PROGRESS
            OrderStatus.ORDERED -> OrderStatusDto.ORDERED
        }
    }
}

@Component
class OrderToDtoConvertor: Converter<Order, OrderDto> {
    override fun convert(from: Order): OrderDto {
        return OrderDto(
            id = from.id,
            teamId = from.team.id,
            creatorId = from.creator.id,
            status = OrderStatusToDtoConvertor().convert(from.status),
            createdAt = from.createdAt.toLocalDateTime().atOffset(ZoneOffset.UTC),
            contextUrl = from.contextUrl
        )
    }
}
