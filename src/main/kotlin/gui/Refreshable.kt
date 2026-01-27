package gui

import entity.Card

/**
 * This interface contains all the refreshables, that are used in an active game or menu scene.
 */
interface Refreshable {
    /**
     * This refreshes the menu scene into the game scene, after
     * the start game button has been clicked
     */
    fun refreshAfterBuildGame(){}

    /**
     * This refreshes the game scene to the next player, after
     * the player pressed the start turn button
     */
    fun refreshAfterStartTurn(){}

    /**
     * This refreshes the game scene, after the player drew a card.
     */
    fun refreshAfterDrawCard(){}

    /**
     * This refreshes the game scene, after the player played a card.
     */
    fun refreshAfterPlayCard(card : Card, stack : Int){}

    /**
     * This refreshes the game scene, after the player exchanged his cards.
     */
    fun refreshAfterExchangeCard(){}

    /**
     * This refreshes the game scene, after the next player starts his turn.
     */
    fun refreshAfterNextPlayer(){}

    /**
     * This refreshes from the game scene to the result menu scene, after a player has won
     * ,or it is a draw
     */
    fun refreshAfterEndGame(winner : String?){}

    /**
     * This refreshes the game scene, after an invalid move was made.
     */
    fun refreshInvalidMove(message: String){}


}
