package service
import kotlin.test.*

/**
 * This class tests the function buildGame and sees, if it is valid
 */
class BuildGameTest {
    private val rootService = RootService()

    /**
     * Tests, if the game builds correctly and all settings are met as expected
     */
    @Test
    fun testBuildGame() {
        rootService.gameService.buildGameService(mutableListOf("Jonas", "Alfred"))
        val game = rootService.currentGame
        checkNotNull(game)
        assertEquals(game.players[0].name, "Jonas")
        assertEquals(game.players[1].name, "Alfred")
        assertEquals(game.players.size, 2)
        assertEquals(game.players[0].drawStack.size, 20)
        assertEquals(game.players[1].drawStack.size, 20)
        assertEquals(game.players[0].handCards.size, 5)
        assertEquals(game.players[1].handCards.size, 5)
    }

    /**
     * Tests, if the game fails, if there are too many players.
     */
    @Test
    fun testBuildGameWithTooManyPlayers(){
        assertFailsWith<IllegalArgumentException> {
            rootService.gameService.buildGameService(mutableListOf("Jonas", "Alfred", "Johnny"))}
    }

    /**
     * Tests, if the game fails, if there are too few players.
     */
    @Test
    fun testBuildGameWithLessPlayers(){
        assertFailsWith<IllegalArgumentException> {
            rootService.gameService.buildGameService(mutableListOf("Jonas"))
        }
    }
}
