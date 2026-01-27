package entity


/**
 * Represents a running UpAndDownGame
 *
 * 
 * @property activePlayer Which player is currently active in the game
 * @property previousPass Has a previous Player passed on his turn?
 * @property players The amount of players playing
 * @property playStack1 Stack 1 in the middle.
 * @property playStack2 Stack 2 in the middle.
 */

data class Game (var activePlayer: Int){
    var previousPass: Boolean = false
    var players: MutableList<Player> = mutableListOf()
    var playStack1 : Card? = null
    var playStack2 : Card? = null

    /**
     * Switches the player in the game
     */
    fun nextPlayer(){
        activePlayer = (activePlayer + 1) % 2
    }
}
