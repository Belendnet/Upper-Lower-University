package service

import entity.Card
import entity.CardSuit
import entity.CardValue
import entity.Game
import kotlin.test.*

/**
 * Here in this test, the game object will be tested, if it provides the standards given.
 *
 * @property game The example object of Game
 */

class GameTest {
    private val game = Game(0)
    /**
     * Sets up the game object with 1 card each Stack
     */
    @BeforeTest
    fun setup(){
        game.playStack1 = Card(CardValue.ACE, CardSuit.SPADES)
        game.playStack2 = Card(CardValue.KING, CardSuit.HEARTS)
    }
    /**
     * Tests, if the cards are empty after putting a card inside
     */
    @Test
    fun stackEmptyList(){
        assertNotNull(game.playStack1)
        assertNotNull(game.playStack2)
    }
    /**
     * Tests, if the player list is empty
     */
    @Test
    fun playerListEmptyList(){
        assertNotNull(game.players)
    }
    /**
     * Tests, if the cards on the different stacks are equal (Can't be possible)
     */
    @Test
    fun equalTest(){
        if(game.playStack1?.suit == game.playStack2?.suit && game.playStack1?.value==game.playStack2?.value){
            throw IllegalStateException()
        }
    }
    /**
     * Tests, if the next player is switched correctly
     * Standard value = 0, expected value = 1
     */
    @Test
    fun activePlayerTest(){
        game.nextPlayer()
        assertEquals(game.activePlayer, 1)
        game.nextPlayer()
        assertEquals(game.activePlayer, 0)
    }
    /**
     * Tests, if the standard previousPass value is false and the changed state
     */
    @Test
    fun passTest(){
        assertEquals(game.previousPass, false)
    }
}
