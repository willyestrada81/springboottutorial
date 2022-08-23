//package com.example.springboottutorial.datasource.network
//
//import com.example.springboottutorial.datasource.BankDataSource
//import com.example.springboottutorial.model.Bank
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Repository
//import org.springframework.web.client.RestTemplate
//
//@Repository("network")
//class NetworkBankDataSource(
//    @Autowired private val restTemplate: RestTemplate
//): BankDataSource {
//    override fun retrieveBanks(): Collection<Bank> {
//
//        restTemplate.getForEntity("54.193.31.159/banks")
//    }
//
//    override fun retrieveBank(accountNumber: String): Bank {
//        TODO("Not yet implemented")
//    }
//
//    override fun createBank(bank: Bank): Bank {
//        TODO("Not yet implemented")
//    }
//
//    override fun updateBank(bank: Bank): Bank {
//        TODO("Not yet implemented")
//    }
//
//    override fun deleteBank(accountNumber: String) {
//        TODO("Not yet implemented")
//    }
//}