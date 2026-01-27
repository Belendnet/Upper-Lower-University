package service

import entity.*
import kotlin.test.*

/**
 * This class tests all the functions and edge cases of the function playCard
 */
class PlayCardTest {
    private val rootService = RootService()

    /**
     * Sets up the conditions ideal for testing
     */
    @BeforeTest
    fun setUp() {
        rootService.gameService.buildGameService(mutableListOf("Alfred", "Jonas"))
    }

    /**
     * Tests, if the card on stack will be replaced with a difference of one
     */
    @Test
    fun playCardWithDifferenceOfOneTest(){
        val game = rootService.currentGame
        checkNotNull(game)
        assertEquals(5, game.players[game.activePlayer].handCards.size)

        game.players[game.activePlayer].handCards.add(Card(CardValue.TWO, CardSuit.HEARTS))
        game.playStack1 = Card(CardValue.ACE, CardSuit.HEARTS)

        rootService.playerActionService.playCard(
            game.players[game.activePlayer].handCards[5], game.playStack1!!)

        assertEquals(5, game.players[game.activePlayer].handCards.size)
        assertEquals(Card(CardValue.TWO, CardSuit.HEARTS), game.playStack1)
    }

    /**
     * Tests, if the card will be replaced with a difference of two, but same suit
     */
    @Test
    fun playCardWithDifferenceOfTwoTest(){
        val game = rootService.currentGame
        checkNotNull(game)

        game.players[game.activePlayer].handCards.add(Card(CardValue.THREE, CardSuit.HEARTS))
        game.playStack1 = Card(CardValue.ACE, CardSuit.HEARTS)

        val notActivePlayer = game.activePlayer

        rootService.playerActionService.playCard(
            game.players[game.activePlayer].handCards[5], game.playStack1!!)

        assertEquals(5, game.players[game.activePlayer].handCards.size)
        assertEquals(Card(CardValue.THREE, CardSuit.HEARTS), game.playStack1)
        assertFalse(game.previousPass)
        assertNotEquals(notActivePlayer, game.activePlayer)
    }

    /**
     * Tests, if the card will be replaced, if the card is invalid
     */
    @Test
    fun invalidCardTest(){
        val game = rootService.currentGame
        checkNotNull(game)

        game.players[game.activePlayer].handCards.add(Card(CardValue.THREE, CardSuit.HEARTS))
        game.playStack1 = Card(CardValue.ACE, CardSuit.SPADES)

        assertFailsWith<IllegalArgumentException> {rootService.playerActionService.playCard(
            game.players[game.activePlayer].handCards[5], game.playStack1!!)}
    }
}
