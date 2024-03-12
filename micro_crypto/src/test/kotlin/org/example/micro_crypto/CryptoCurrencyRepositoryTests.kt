package org.example.micro_crypto.dao

import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
class CryptoCurrencyRepositoryTests {

    @Autowired
    private lateinit var repository: CryptoCurrencyRepository

    @Test
    fun `ensure repository is not empty`() {
        val result = repository.findAll()
        assertNotEquals(0, result.size, "Repository should not be empty")
    }
}
