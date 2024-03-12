package org.example.micro_crypto.vao

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "cryptoCurrencies")
data class CryptoCurrency(
        @Id
        val id: String? = null,
        var name: String,
        var ticker: String,
        var averagePurchasePrice: Double,
        var quantity: Double,
        var desiredSalePrice: Double
)
