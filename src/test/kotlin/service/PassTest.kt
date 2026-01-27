package service

import kotlin.test.*
import entity.*
/**
 * This class tests the function pass() in PlayerActionService
 */
class PassTest {
    private val rootService = RootService()

    /**
     * Sets up the conditions to test on.
     */
    @BeforeTest
    fun setUp() {
        rootService.gameService.buildGameService(mutableListOf("Alfred", "Jonas"))
    }
    /**
     * Tests the pass function when all conditions are met.
     */
    @Test
    fun passWithPerfectCondition(){
        val game = rootService.currentGame
        checkNotNull(game)
        game.playStack1 = Card(CardValue.ACE, CardSuit.HEARTS)
        game.players[game.activePlayer].drawStack.clear()
        game.players[game.activePlayer].handCards.clear()
        game.players[game.activePlayer].handCards.add(Card(CardValue.EIGHT, CardSuit.HEARTS))

        val activePlayer = game.activePlayer
        rootService.playerActionService.pass()

        assertNotEquals(activePlayer, game.activePlayer)
        assertEquals(game.players[activePlayer].handCards.size, 1)
        assertTrue(game.players[activePlayer].drawStack.isEmpty())
    }
    /**
     * Tests, if the pass fails, if one of the options is available, in this case
     * draw.
     */
    @Test
    fun passWithDrawStackFilled(){
        val game = rootService.currentGame
        checkNotNull(game)

        game.players[game.activePlayer].drawStack.add(Card(CardValue.NINE, CardSuit.HEARTS))

        assertFailsWith<IllegalArgumentException> { rootService.playerActionService.pass() }
    }

    /**
     * Tests, if the pass fails, if one of the options is available, in this case
     * playing a Card.
     */
    @Test
    fun passWithPlayableCard(){
        val game = rootService.currentGame
        checkNotNull(game)

        game.playStack1 = Card(CardValue.EIGHT, CardSuit.HEARTS)
        game.players[game.activePlayer].drawStack.clear()
        game.players[game.activePlayer].handCards.clear()
        game.players[game.activePlayer].handCards.add(Card(CardValue.NINE, CardSuit.HEARTS))

        assertFailsWith<IllegalArgumentException> { rootService.playerActionService.pass() }
    }

}
