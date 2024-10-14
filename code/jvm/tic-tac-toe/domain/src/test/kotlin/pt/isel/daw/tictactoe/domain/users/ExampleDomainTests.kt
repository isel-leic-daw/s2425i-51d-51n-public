package pt.isel.daw.tictactoe.domain.users

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class ExampleDomainTests {
    @Test
    fun `example test`() {
        // given: an encoder
        val encoder = Sha256TokenEncoder()
        // and: a token
        val token = "the-token"

        // when: encoding the token
        val validationInformation = encoder.createValidationInformation(token)

        // then: the validationInformation is not equal to the token
        assertNotEquals(token, validationInformation.validationInfo)

        // when: recomputing the validationInformation produces the same value
        val newValidationInformation = encoder.createValidationInformation(token)

        // then: it produces the same information
        assertEquals(validationInformation.validationInfo, newValidationInformation.validationInfo)
    }
}