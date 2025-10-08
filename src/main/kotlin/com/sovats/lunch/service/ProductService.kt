package com.sovats.lunch.service

import com.sovats.lunch.persistence.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService (
    private val productRepository: ProductRepository,
) {
    fun createProduct(createdByUserId: Long, orderId: Long, name: String, url: String?, description: String?, quantity: Int, itemPrice: Long) {
        this.productRepository.insert(createdByUserId, orderId, name, url, description, quantity, itemPrice)
    }

    fun editProductDetails(productId: Long, name: String, url: String?, description: String?, quantity: Int, itemPrice: Long) {
        this.productRepository.updateProductDetails(productId, name, url, description, quantity, itemPrice)
    }
}
