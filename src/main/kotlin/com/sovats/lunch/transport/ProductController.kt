package com.sovats.lunch.transport

import com.sovats.lunch.api.ProductApi
import com.sovats.lunch.model.ProductDetailsDto
import com.sovats.lunch.model.ProductDto
import com.sovats.lunch.service.ProductService
import org.springframework.core.convert.ConversionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class ProductController (
    private val productService: ProductService,
    private val conversionService: ConversionService
): ProductApi {

    override fun addProduct(xUserId: Long, orderId: Long, productDetailsDto: ProductDetailsDto): ResponseEntity<ProductDto> {
        val product = this.productService.createProduct(
            xUserId,
            orderId,
            productDetailsDto.name,
            productDetailsDto.url,
            productDetailsDto.quantity,
            productDetailsDto.itemPrice
        )
        val productDto: ProductDto = conversionService.convert(product, ProductDto::class.java)
            ?: throw IllegalArgumentException("Product could not be created")

        return  ResponseEntity.status(201).body(productDto)
    }

    override fun editProductDetails(xUserId: Long, productId: Long, productDetailsDto: ProductDetailsDto): ResponseEntity<Unit> {
        if (!this.productService.isCreatorOfProduct(xUserId, productId)) {
            return ResponseEntity.badRequest().body(Unit)
        }
        this.productService.editProductDetails(
            productId,
            productDetailsDto.name,
            productDetailsDto.url,
            productDetailsDto.quantity,
            productDetailsDto.itemPrice
        )

        return ResponseEntity.ok().build()
    }

    override fun copyProduct(xUserId: Long, productId: Long, destinationOrderId: Long): ResponseEntity<Unit> {
        this.productService.copyProduct(xUserId, destinationOrderId,productId)

        return ResponseEntity.ok().build()
    }

    override fun deleteProduct(xUserId: Long, productId: Long): ResponseEntity<Unit> {
        if (!this.productService.isCreatorOfProduct(xUserId, productId)) {
            return ResponseEntity.badRequest().body(Unit)
        }

        this.productService.deleteProduct(productId)
        return ResponseEntity.ok().build()
    }

    override fun addOptionToProduct(xUserId: Long, productId: Long, productOptionName: String): ResponseEntity<Unit> {
        if (!this.productService.isCreatorOfProduct(xUserId, productId)) {
            return ResponseEntity.badRequest().body(Unit)
        }

        productService.addOptionToProduct(productId, productOptionName)
        return ResponseEntity.ok().build()
    }
}
