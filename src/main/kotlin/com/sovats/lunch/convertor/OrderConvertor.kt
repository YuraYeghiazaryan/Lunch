package com.sovats.lunch.convertor

import com.sovats.lunch.model.OrderStatus
import com.sovats.lunch.model.OrderStatusDto
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class DtoToOrderStatusConvertor: Converter<OrderStatusDto, OrderStatus> {
    override fun convert(from: OrderStatusDto): OrderStatus {
        return when(from) {
            OrderStatusDto.IN_PROGRESS -> OrderStatus.IN_PROGRESS
            OrderStatusDto.ORDERED -> OrderStatus.ORDERED
        }
    }
}
