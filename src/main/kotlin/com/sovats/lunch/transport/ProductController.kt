package com.sovats.lunch.transport

import com.sovats.lunch.api.ProductApi
import com.sovats.lunch.model.ProductDetailsDto
import com.sovats.lunch.service.ProductService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController (
    private val productService: ProductService
): ProductApi {
    override fun addProduct(xUserId: Long, orderId: Long, productDetailsDto: ProductDetailsDto): ResponseEntity<Unit> {
        this.productService.createProduct(
            xUserId,
            orderId,
            productDetailsDto.name,
            productDetailsDto.url,
            productDetailsDto.description,
            productDetailsDto.quantity,
            productDetailsDto.itemPrice
        )

        return ResponseEntity.ok().build()
    }

    override fun editProductDetails(productId: Long, productDetailsDto: ProductDetailsDto): ResponseEntity<Unit> {
        this.productService.editProductDetails(
            productId,
            productDetailsDto.name,
            productDetailsDto.url,
            productDetailsDto.description,
            productDetailsDto.quantity,
            productDetailsDto.itemPrice
        )

        return ResponseEntity.ok().build()
    }

    override fun copyProduct(xUserId: Long, productId: Long, destinationOrderId: Long): ResponseEntity<Unit> {
        this.productService.copyProduct(xUserId, destinationOrderId,productId)

        return ResponseEntity.ok().build()
    }

    override fun deleteProduct(productId: Long): ResponseEntity<Unit> {
        this.productService.deleteProduct(productId)
        return ResponseEntity.ok().build()
    }
}
