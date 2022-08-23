package com.example.springboottutorial.service

import com.example.springboottutorial.datasource.BankDataSource
import com.example.springboottutorial.datasource.mock.MockBankDataSource
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BankServiceTest {
    private val dataSource: BankDataSource = mockk(relaxed = true)
    private val bankService = BankService(dataSource)
    @Test
    fun `Should call its data source o retrieve banks` () {
        //When
        bankService.getBanks()
        //Then
        verify( exactly = 1 ) { dataSource.retrieveBanks()}
    }
}