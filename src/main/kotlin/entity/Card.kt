package entity

/**
 * Represents the Cards in the game.
 *
 * @property value The Value of a Card
 * @property suit The suit of a Card
 *
 */

data class Card(var value: CardValue, var suit: CardSuit) {
    override fun toString() = "$value$suit"
}
