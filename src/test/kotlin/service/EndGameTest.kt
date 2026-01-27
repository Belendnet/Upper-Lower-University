package service

import kotlin.test.*
/**
 * This class tests the function endGame() in GameService
 */
class EndGameTest {
    private val rootService = RootService()
    /**
     * Sets up the game for the conditions
     */
    @BeforeTest
    fun setUp(){
        rootService.gameService.buildGameService(mutableListOf("Alfred","Jonas"))
    }
    /**
     * Tests, if the game ends correctly.
     */
    @Test
    fun endGameNormalTest(){
        var game = rootService.currentGame
        assertNotNull(game)
        game.players[0].handCards.removeLast()
        rootService.gameService.endGame()
        game = rootService.currentGame
        assertNull(game)
    }
}
