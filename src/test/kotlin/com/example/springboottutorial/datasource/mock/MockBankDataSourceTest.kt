package com.example.springboottutorial.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest {
    private val mockDataSource = MockBankDataSource()
    @Test
    fun `should provide a collection of banks` () {
        // When
        val banks = mockDataSource.retrieveBanks()
        //Then
        assertThat(banks.size).isGreaterThanOrEqualTo(3)
    }
    @Test
    fun `should provide some mock data` () {
        // When
        val banks = mockDataSource.retrieveBanks()
        //Then
        assertThat(banks).allMatch { it.accountNumber.isNotBlank()}
        assertThat(banks).anyMatch() { it.trust != 0.0}
        assertThat(banks).allMatch { it.transactionFee != 0}

    }

}