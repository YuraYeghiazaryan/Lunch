package com.sovats.lunch.service

import com.sovats.lunch.persistence.entity.Product
import com.sovats.lunch.persistence.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService (
    private val productRepository: ProductRepository,
) {

    fun createProduct(createdByUserId: Long, orderId: Long, name: String, url: String?, quantity: Int, itemPrice: Long): Product {
        return this.productRepository.insert(createdByUserId, orderId, name, url, quantity, itemPrice)
    }

    fun editProductDetails(productId: Long, name: String, url: String?, quantity: Int, itemPrice: Long) {
        this.productRepository.updateProductDetails(productId, name, url, quantity, itemPrice)
    }

    fun copyProduct(copierUserId: Long, destinationOrderId: Long, sourceProductId: Long) {
        val sourceProduct: Product = this.productRepository.findProductById(sourceProductId)
        this.productRepository.insert(
            copierUserId,
            destinationOrderId,
            sourceProduct.name,
            sourceProduct.url,
            sourceProduct.quantity,
            sourceProduct.itemPrice
        )
    }

    fun deleteProduct(productId: Long) {
        this.productRepository.deleteById(productId)
    }
}
