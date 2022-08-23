package com.example.springboottutorial.datasource.mock

import com.example.springboottutorial.datasource.BankDataSource
import com.example.springboottutorial.model.Bank
import org.springframework.stereotype.Repository

@Repository("network")
class MockBankDataSource : BankDataSource {
    val banks = mutableListOf(
        Bank("123456", 8.9, 9),
        Bank("12568", 8.9, 9),
        Bank("4258", 8.9, 9),
    )
    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not found a bank with account number $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if (banks.any {it.accountNumber == bank.accountNumber}) {
            throw IllegalArgumentException("Bank account number already exist")
        }
        banks.add(bank)
        return bank
    }

    override fun updateBank(bank: Bank): Bank {
        val existingBank = banks.firstOrNull() { it.accountNumber == bank.accountNumber}
            ?: throw NoSuchElementException("Could not found a bank with account number ${bank.accountNumber}")

        banks[banks.indexOf(existingBank)] = bank
        return bank
    }

    override fun deleteBank(accountNumber: String) {
        val existingBank = banks.firstOrNull() { it.accountNumber == accountNumber}
            ?: throw NoSuchElementException("Could not found a bank with account number $accountNumber")

        banks.remove(existingBank)
    }
}