package entity

/**
 * Represents a Player in UpAndDownGame
 *
 * @property name The name of the player
 * @property drawStack The Stack, which the player draws from
 * @property handCards The Cards, that the Player carries on his hand
 */


class Player (var name : String = ""){
    var drawStack : MutableList<Card> = mutableListOf()
    var handCards : MutableList<Card> = mutableListOf()
}
