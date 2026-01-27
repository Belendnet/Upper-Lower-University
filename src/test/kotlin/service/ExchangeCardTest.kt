package service

import kotlin.test.*

/**
 * This class tests the exchangeCards function in PlayerActionService
 */
class ExchangeCardTest {
    private val rootService = RootService()

    /**
     * Sets up the conditions ideal for the tests.
     */
    @BeforeTest
    fun setUp() {
        rootService.gameService.buildGameService(mutableListOf("Jonas", "Alfred"))
        repeat(6) {
            rootService.playerActionService.drawCard()
        }
    }

    /**
     * Tests, if the exchangeCard functions works as intended
     */
    @Test
    fun exchangeCardsValidTest() {
        val game = rootService.currentGame
        checkNotNull(game)

        assertEquals(8, game.players[0].handCards.size)
        assertEquals(8, game.players[1].handCards.size)
        assertEquals(17, game.players[0].drawStack.size)
        assertEquals(17, game.players[1].drawStack.size)

        val activePlayer = game.activePlayer

        rootService.playerActionService.exchangeCards()

        assertEquals(5, game.players[activePlayer].handCards.size)
        assertEquals(20, game.players[activePlayer].drawStack.size)
        assertFalse(game.previousPass)
        assertNotEquals(game.activePlayer, activePlayer)
    }

    /**
     * Tests, if the exchangeCard functions gives out the right error with empty draw stack
     */
    @Test
    fun exchangeCardsFailWithEmptyDrawStack() {
        val game = rootService.currentGame
        checkNotNull(game)

        game.players[game.activePlayer].drawStack.clear()

        assertTrue(game.players[game.activePlayer].drawStack.isEmpty())
        assertFailsWith<IllegalArgumentException> { rootService.playerActionService.exchangeCards() }
    }

    /**
     * Tests, if the exchangeCard functions gives out the right error with less than 8 hand cards
     */
    @Test
    fun exchangeCardsFailWithLessThanEightHandCards() {
        val game = rootService.currentGame
        checkNotNull(game)

        game.players[game.activePlayer].handCards.removeLast()

        assertTrue(game.players[game.activePlayer].handCards.size < 8)
        assertFailsWith<IllegalArgumentException> { rootService.playerActionService.exchangeCards() }
    }
}
