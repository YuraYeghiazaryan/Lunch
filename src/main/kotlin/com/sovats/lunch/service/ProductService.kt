package com.sovats.lunch.service

import com.sovats.lunch.persistence.entity.Product
import com.sovats.lunch.persistence.repository.OptionRepository
import com.sovats.lunch.persistence.repository.ProductOptionRepository
import com.sovats.lunch.persistence.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService (
    private val productRepository: ProductRepository,
    private val optionRepository: OptionRepository,
    private val productOptionRepository: ProductOptionRepository
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

    fun addOptionToProduct(productId: Long, optionName: String) {
        /** 1. Find or create option */
        val option = optionRepository.findByName(optionName)
            ?: optionRepository.insert(optionName)

        /** 2. Check if link already exists */
        if (productOptionRepository.existsByProductIdAndOptionId(productId, option.id)) return

        /** 3. Insert link */
        productOptionRepository.insert(productId, option.id)
    }
}
