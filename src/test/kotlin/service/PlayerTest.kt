package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Player
import kotlin.test.*

/**
 * This test checks, if the object of Player is correctly initialized and works as expected
 *
 * @property player The object of class Player
 */

class PlayerTest {
    private val player = Player("boss")
    /**
     * Sets up the test player
     */
    @BeforeTest
    fun setup(){
        player.drawStack.add(Card(CardValue.ACE,CardSuit.SPADES))
        player.drawStack.add(Card(CardValue.TEN,CardSuit.SPADES))
        player.handCards.add(Card(CardValue.KING,CardSuit.HEARTS))
        player.handCards.add(Card(CardValue.SEVEN,CardSuit.CLUBS))
    }
    /**
     * Tests, if the parameters are empty
     */
    @Test
    fun notNullTest(){
        assertNotNull(player.name)
        assertNotNull(player.drawStack)
        assertNotNull(player.handCards)
    }
    /**
     * Tests, if the cards are equal from drawStack and handCards
     */
    @Test
    fun equalTest(){
        for(drawCard in player.drawStack){
            for(handCard in player.handCards){
                if(drawCard.value == handCard.value && drawCard.suit == handCard.suit){
                    throw IllegalStateException()
                }
            }
        }
    }
}
