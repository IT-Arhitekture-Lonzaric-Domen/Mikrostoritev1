package org.example.micro_crypto.service

import org.example.micro_crypto.dao.CryptoCurrencyRepository
import org.example.micro_crypto.vao.CryptoCurrency
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.boot.test.context.SpringBootTest


@ExtendWith(MockitoExtension::class)
@SpringBootTest
class CryptoCurrencyServiceTest {

    @Mock
    lateinit var cryptoCurrencyRepository: CryptoCurrencyRepository

    @InjectMocks
    lateinit var cryptoCurrencyService: CryptoCurrencyService

    @Test
    fun `findAll returns list of cryptocurrencies`() {
        val mockData = listOf(
            CryptoCurrency("1", "Bitcoin", "BTC", 10000.0, 1.0, 20000.0),
            CryptoCurrency("2", "Ethereum", "ETH", 2000.0, 10.0, 3000.0)
        )

        `when`(cryptoCurrencyRepository.findAll()).thenReturn(mockData)

        val result = cryptoCurrencyService.findAll()

        assertEquals(2, result.size)
        assertEquals("Bitcoin", result[0].name)
        assertEquals("Ethereum", result[1].name)
    }
}
