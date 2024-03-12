package org.example.micro_crypto.service

import org.slf4j.LoggerFactory
import org.example.micro_crypto.vao.CryptoCurrency
import org.example.micro_crypto.dao.CryptoCurrencyRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CryptoCurrencyService(private val cryptoCurrencyRepository: CryptoCurrencyRepository) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun findAll(): List<CryptoCurrency> {
        log.info("Fetching all cryptocurrencies")
        return cryptoCurrencyRepository.findAll().also {
            log.debug("Found ${it.size} cryptocurrencies")
        }
    }

    fun add(cryptoCurrency: CryptoCurrency): CryptoCurrency {
        log.info("Adding new cryptocurrency: {}", cryptoCurrency.ticker)
        return cryptoCurrencyRepository.save(cryptoCurrency).also {
            log.debug("Added cryptocurrency with ID: {}", it.id)
        }
    }

    fun update(id: String, updateInfo: CryptoCurrency): CryptoCurrency {
        log.info("Updating cryptocurrency with ID: {}", id)
        val cryptoCurrency = cryptoCurrencyRepository.findById(id).orElseThrow {
            log.error("CryptoCurrency not found for ID: {}", id)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "CryptoCurrency not found")
        }
        return cryptoCurrencyRepository.save(
            cryptoCurrency.apply {
                name = updateInfo.name
                ticker = updateInfo.ticker
                averagePurchasePrice = updateInfo.averagePurchasePrice
                quantity = updateInfo.quantity
                desiredSalePrice = updateInfo.desiredSalePrice
            }
        ).also {
            log.debug("Updated cryptocurrency with ID: {}", it.id)
        }
    }

    fun delete(id: String) {
        log.info("Deleting cryptocurrency with ID: {}", id)
        if (!cryptoCurrencyRepository.existsById(id)) {
            log.error("Trying to delete non-existent cryptocurrency with ID: {}", id)
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "CryptoCurrency not found")
        }
        cryptoCurrencyRepository.deleteById(id)
        log.debug("Deleted cryptocurrency with ID: {}", id)
    }

    fun findByTicker(ticker: String): List<CryptoCurrency> {
        log.info("Fetching cryptocurrencies with ticker: {}", ticker)
        return cryptoCurrencyRepository.findByTicker(ticker).also {
            log.debug("Found ${it.size} cryptocurrencies with ticker: {}", ticker)
        }
    }
}