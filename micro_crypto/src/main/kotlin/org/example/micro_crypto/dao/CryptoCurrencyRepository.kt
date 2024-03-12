package org.example.micro_crypto.dao

import org.example.micro_crypto.vao.CryptoCurrency
import org.springframework.data.mongodb.repository.MongoRepository

interface CryptoCurrencyRepository : MongoRepository<CryptoCurrency, String> {

    fun findByTicker(ticker: String): List<CryptoCurrency>
}
