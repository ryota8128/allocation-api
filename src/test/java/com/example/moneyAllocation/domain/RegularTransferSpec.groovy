package com.example.moneyAllocation.domain

import com.example.moneyAllocation.exception.ResourceValidationException
import spock.lang.Specification
import spock.lang.Unroll

class RegularTransferSpec extends Specification {
    private RegularTransfer regularTransfer

    def setup() {
        regularTransfer = new RegularTransfer()
        regularTransfer.setUserId(10L)
    }

    @Unroll
    def "GetSelectorList from: #from, to: #to"() {
        given:
        regularTransfer.setFromAccount(from)
        regularTransfer.setToAccount(to)

        when:
        List<AccountSelector> selectorList = regularTransfer.fetchSelectorList()

        then:
        selectorList.size() == len

        where:
        from | to   | len
        1L   | 2L   | 2
        null | 2L   | 1
        1L   | null | 1
        null | null | 0
    }

    def "CheckRatioValid with ratio: #ratio"() {
        given:
        regularTransfer.setRatio((Float) ratio)
        boolean catchException = false

        when:
        try {
            regularTransfer.checkRatioValid()
        } catch (ResourceValidationException e) {
            catchException = true
        }

        then:
        catchException == exception

        where:
        ratio  | exception
        -1     | true
        -0.001 | true
        0      | false
        0.001  | false
        0.5    | false
        1      | false
        1.0001 | true
        5      | true
    }

    def "SetZero"() {
        given:
        regularTransfer.setRatio(ratio)
        regularTransfer.setAmount(amount)

        when:
        regularTransfer.setZero()

        then:
        regularTransfer.ratio == (Float) ansRetio
        regularTransfer.amount == (Integer) ansAmount

        where:
        ratio | amount | ansRetio | ansAmount
        null  | null   | 0        | 0
        0.5   | null   | 0.5      | 0
        null  | 20000  | 0        | 20000
        -0.4  | 500000 | -0.4     | 500000

    }
}
