package org.example.micro_crypto.rest

import org.example.micro_crypto.vao.CryptoCurrency
import org.example.micro_crypto.service.CryptoCurrencyService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cryptocurrencies")
class CryptoCurrencyController(private val service: CryptoCurrencyService) {

    private val log = LoggerFactory.getLogger(CryptoCurrencyController::class.java)

    @GetMapping
    fun listAll(): ResponseEntity<List<CryptoCurrency>> {
        log.info("Request to list all cryptocurrencies")
        return ResponseEntity.ok(service.findAll()).also {
            log.debug("Listed ${it.body?.size ?: 0} cryptocurrencies")
        }
    }

    @PostMapping
    fun addCryptoCurrency(@RequestBody cryptoCurrency: CryptoCurrency): ResponseEntity<CryptoCurrency> {
        log.info("Request to add a new cryptocurrency: {}", cryptoCurrency.ticker)
        return ResponseEntity(service.add(cryptoCurrency), HttpStatus.CREATED).also {
            log.debug("Added cryptocurrency with ID: {}", it.body?.id)
        }
    }

    @PutMapping("/{id}")
    fun updateCryptoCurrency(@PathVariable id: String, @RequestBody cryptoCurrency: CryptoCurrency): ResponseEntity<CryptoCurrency> {
        log.info("Request to update cryptocurrency with ID: {}", id)
        return ResponseEntity.ok(service.update(id, cryptoCurrency)).also {
            log.debug("Updated cryptocurrency with ID: {}", it.body?.id)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteCryptoCurrency(@PathVariable id: String): ResponseEntity<Void> {
        log.info("Request to delete cryptocurrency with ID: {}", id)
        service.delete(id)
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT).also {
            log.debug("Deleted cryptocurrency with ID: {}", id)
        }
    }

    @GetMapping("/{ticker}")
    fun getByTicker(@PathVariable ticker: String): ResponseEntity<List<CryptoCurrency>> {
        log.info("Request to list cryptocurrencies by ticker: {}", ticker)
        return ResponseEntity.ok(service.findByTicker(ticker)).also {
            log.debug("Listed ${it.body?.size ?: 0} cryptocurrencies for ticker: {}", ticker)
        }
    }
}
