package com.sovats.lunch.convertor

import com.sovats.lunch.model.ProductDto
import com.sovats.lunch.persistence.entity.Product
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component

@Component
class ProductToDtoConvertor: Converter<Product, ProductDto> {
    override fun convert(from: Product): ProductDto {
        return ProductDto(
            id = from.id,
            name = from.name,
            quantity = from.quantity,
            itemPrice = from.itemPrice,
            orderId = from.order.id,
            creatorId = from.creator.id,
            url = from.url
        )
    }
}
