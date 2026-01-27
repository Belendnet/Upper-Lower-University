package service

import kotlin.test.*
import entity.Card
import entity.CardSuit
import entity.CardValue

/**
 * Here, an example Card will be created and tested, if it actually exists
 *
 * @property coolCard The example object from class Card
 */

class CardTest {
    private val coolCard = Card(CardValue.ACE, CardSuit.CLUBS)

    /**
     * Tests, if the created Card has any Value
     */
    @Test
    fun cardNotNullTest(){
        assertNotNull(coolCard)
    }
    /**
     * Tests, if the toString function works
     */
    @Test
    fun toStringTest(){
        assertEquals(coolCard.toString(), "A♣")
    }
}
