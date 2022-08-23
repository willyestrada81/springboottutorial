package com.example.springboottutorial.controller

import com.example.springboottutorial.model.Bank
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks {
        @Test
        fun `should return all banks` () {
            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

        @Test
        fun `should return a bank by provided account number` () {
            //Given
            val accountNumber = 123456

            // when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }
        @Test
        fun `should return NOT FOUND if account number does not exist` () {
            //Given
            val accountNumber = "does_not_exist"

            //When//Then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }

        }
    }
    
    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostBankData {
    
        @Test
        fun `should add a new bank` () {
            //Given
            val newBank = Bank(accountNumber = "qwerty", trust = 2.35, transactionFee = 2)
        
            //When
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
            
            //Then
            performPost
                .andDo { print() }
                .andExpect { status { isCreated() } }
        }
        @Test
        fun `should return BAD REQUEST if bank with an existing account number exists` () {
            //Given
            val invalidBank = Bank(accountNumber = "4258", trust = 2.35, transactionFee = 2)
            //When
            val performPost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(invalidBank)
            }

            //Then
            performPost
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }
    
    @Nested
    @DisplayName("PATCH /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateBankData {
    
        @Test
        fun `should update a bank when providing correct payload data and accountNumber` () {
            //Given
            val newBank = Bank(accountNumber = "123456", trust = 2.35, transactionFee = 2)
        
            //When
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }
                
            //Then
            performPatch
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }
            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                }
        }
        @Test
        fun `should return NOT FOUND if account number does not exist when updating a bank` () {
            //Given
            val newBank = Bank("invalid_account",2.35, 2)

            //When
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            //Then
            performPatch
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return if invalid payload is sent` () {
            //Given
            val newBank = {}

            //When
            val performPatch = mockMvc.patch(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newBank)
            }

            //Then
            performPatch
                .andDo { print() }
                .andExpect { status { isBadRequest() } }
        }
    }
    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteBank {

        @Test
        @DirtiesContext
        fun `should delete a bank when a valid account number is provided` () {
            //Given
            val accountNumber = "123456"

            //When // Then
            mockMvc.delete("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNoContent() } }

            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }

        @Test
        fun `should return BAD REQUEST when deleting a bank with an invalid account number provided` () {
            //Given
            val invalidAccount = "invalidAccount"

            //When // Then
            mockMvc.delete("$baseUrl/$invalidAccount")
                .andDo { print() }
                .andExpect { status { isNotFound() } }
        }
    }
}

