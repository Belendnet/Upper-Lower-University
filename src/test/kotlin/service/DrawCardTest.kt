package service

import kotlin.test.*
import entity.*

/**
 * This class tests the function drawCard on playerActionService
 */
class DrawCardTest {
    private val rootService = RootService()

    /**
     * Sets up the conditions to test on
     */
    @BeforeTest
    fun setUp() {
        rootService.gameService.buildGameService(mutableListOf("Jonas","Alfred"))
    }
    /**
     * Tests, if you can draw a card, when you meet to conditions to draw one
     * Since game was newly built, it should allow us to draw a card.
     */
    @Test
    fun drawCardWithPerfectCondition(){
        val game = rootService.currentGame
        checkNotNull(game)
        val activePlayer = game.activePlayer
        rootService.playerActionService.drawCard()

        assertEquals(game.players[activePlayer].handCards.size, 6)
        assertEquals(game.players[activePlayer].drawStack.size, 19)
        assertNotEquals(game.activePlayer, activePlayer)
    }

    /**
     * Tests, if you can draw a card, when you have 10 hand cards.
     */
    @Test
    fun drawCardWithTenHandCards(){
        val game = rootService.currentGame
        checkNotNull(game)

        game.players[game.activePlayer].handCards.add(Card(CardValue.SIX, CardSuit.HEARTS))
        game.players[game.activePlayer].handCards.add(Card(CardValue.SEVEN, CardSuit.HEARTS))
        game.players[game.activePlayer].handCards.add(Card(CardValue.EIGHT, CardSuit.HEARTS))
        game.players[game.activePlayer].handCards.add(Card(CardValue.NINE, CardSuit.HEARTS))
        game.players[game.activePlayer].handCards.add(Card(CardValue.JACK, CardSuit.HEARTS))

        assertEquals(game.players[game.activePlayer].handCards.size, 10)
        assertTrue(game.players[game.activePlayer].drawStack.isNotEmpty())
        assertFailsWith<IllegalArgumentException> { rootService.playerActionService.drawCard() }
    }

    /**
     * Tests, if you can draw a card, even though the draw Stack is empty.
     */
    @Test
    fun drawCardWithEmptyDrawStack(){
        val game = rootService.currentGame
        checkNotNull(game)

        game.players[game.activePlayer].handCards.clear()
        game.players[game.activePlayer].drawStack.clear()

        assertEquals(game.players[game.activePlayer].handCards.size, 0)
        assertTrue(game.players[game.activePlayer].drawStack.isEmpty())
        assertFailsWith<IllegalArgumentException> { rootService.playerActionService.drawCard() }
    }
}
