package org.example.micro_crypto

import org.example.micro_crypto.rest.CryptoCurrencyController
import org.example.micro_crypto.vao.CryptoCurrency
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class CryptoCurrencyScenarioTest {

    @Autowired
    private lateinit var cryptoCurrencyController: CryptoCurrencyController

    private var newCryptoCurrency: CryptoCurrency? = null

    @BeforeEach
    fun createCryptoCurrency() {
        newCryptoCurrency = cryptoCurrencyController.addCryptoCurrency(
            CryptoCurrency(name = "Bitcoin", ticker = "BTC", averagePurchasePrice = 10000.0, quantity = 1.0, desiredSalePrice = 20000.0)
        ).body
    }

    @Test
    fun checkCryptoCurrencyExistence() {
        val fromServer = cryptoCurrencyController.listAll().body?.find { it.id == newCryptoCurrency?.id }
        assertEquals(newCryptoCurrency?.name, fromServer?.name)
        assertEquals(newCryptoCurrency?.ticker, fromServer?.ticker)
    }

    @Test
    fun updateCryptoCurrency() {
        val updatedCryptoCurrency = cryptoCurrencyController.updateCryptoCurrency(
            newCryptoCurrency!!.id!!,
            CryptoCurrency(name = "Bitcoin", ticker = "BTC", averagePurchasePrice = 10500.0, quantity = 1.0, desiredSalePrice = 25000.0)
        ).body!!

        assertEquals(10500.0, updatedCryptoCurrency.averagePurchasePrice)
        assertEquals(25000.0, updatedCryptoCurrency.desiredSalePrice)
    }

    @Test
    fun deleteCryptoCurrency() {
        cryptoCurrencyController.deleteCryptoCurrency(newCryptoCurrency!!.id!!)
        val cryptocurrencies = cryptoCurrencyController.listAll().body!!
        assertTrue { cryptocurrencies.none { it.id == newCryptoCurrency?.id } }
    }

    @Test
    fun getCryptoCurrenciesByTicker() {
        // Assuming that the newCryptoCurrency has been created in @BeforeEach
        val cryptocurrencies = cryptoCurrencyController.getByTicker("BTC").body!!
        assertTrue { cryptocurrencies.any { it.ticker == "BTC" } }
    }
}
