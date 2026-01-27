package service

import entity.Game
import gui.Refreshable

/**
 * Main Class of the service layer. Provides access to all the other service classes
 * and holds [currentGame] state for these services to access
 */
class RootService {
    /**
     * The rootService access the other services to use in the game
     */
    val playerActionService  = PlayerActionService(this)
    val gameService = GameService(this)

    /**
     * The currently active game, can be null, if game hasn't started
     */
    var currentGame: Game? = null

    /**
     * Adds the provided newRefreshable to all services connected
     */
    fun addRefreshable(newRefreshable: Refreshable){
        gameService.addRefreshable(newRefreshable)
        playerActionService.addRefreshable(newRefreshable)
    }

    /**
     * Adds each of the provided newRefreshable to all services connected
     */
    fun addRefreshable(vararg newRefreshable: Refreshable){
        newRefreshable.forEach {addRefreshable(it) }
    }
}
