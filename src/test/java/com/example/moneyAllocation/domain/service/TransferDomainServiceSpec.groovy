package com.example.moneyAllocation.domain.service

import com.example.moneyAllocation.domain.Account
import com.example.moneyAllocation.domain.TemplateTransfer
import com.example.moneyAllocation.domain.dto.TemplateTransferDto
import com.example.moneyAllocation.exception.ResourceNotFoundException
import com.example.moneyAllocation.exception.ResourceValidationException
import com.example.moneyAllocation.repository.AccountRepository
import spock.lang.Specification

class TransferDomainServiceSpec extends Specification {

    AccountRepository accountRepository
    TransferDomainService target

    void setup() {
        accountRepository = Mock(AccountRepository)
        target = new TransferDomainService(accountRepository)
    }

    def "checkValidAccounts: 正常系"() {
        given:
        def dto = TemplateTransferDto.builder().fromAccount(1L).toAccount(2L).build()
        def transfer = TemplateTransfer.from(dto)
        accountRepository.findOne(_) >> new Account()

        when:
        target.checkValidAccounts(transfer)

        then:
        notThrown(ResourceValidationException)
    }

    def "checkValidAccounts: 異常系"() {
        given:
        def dto = TemplateTransferDto.builder().fromAccount(1L).toAccount(2L).build()
        def transfer = TemplateTransfer.from(dto)
        accountRepository.findOne(_) >> { throw new ResourceNotFoundException("") }

        when:
        target.checkValidAccounts(transfer)

        then:
        thrown(ResourceValidationException)
    }

}
