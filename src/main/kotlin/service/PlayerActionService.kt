package service
import entity.*


/**
 * Provides the action and the consequence of the actions of the player
 */

class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {
    /**
     * playCard describes the action, when a player tries to place a card in the middle
     * It checks, if the conditions are met to place the card onto one of the stacks
     *
     * Preconditions:
     * - If the suit matches the suit on the stack, the cardValue can have a difference of 2
     * - If not, the difference of the cardValue can only be by 1
     *
     * @param card The card, that the player wants to place
     * @param stack The stack, that is in the middle
     */
    fun playCard(card : Card, stack : Card) : Boolean{
        val game = rootService.currentGame
        checkNotNull(game)
        if((((card.value.ordinal - stack.value.ordinal + 13) % 13) == 1) ||
            (((stack.value.ordinal - card.value.ordinal + 13) % 13) == 1)){
            stack.suit = card.suit
            stack.value = card.value
        }
        else if (((((card.value.ordinal - stack.value.ordinal + 13) % 13) == 2)||
                    (((stack.value.ordinal - card.value.ordinal + 13) % 13) == 2))
            && stack.suit == card.suit){
            stack.value = card.value
        } else{
            throw IllegalArgumentException("Card is invalid to play")
        }
        var stackplayed : Int = -1
        if(stack == game.playStack1){
            stackplayed = 0
        } else if (stack == game.playStack2){
            stackplayed = 1
        }
        game.players[game.activePlayer].handCards.remove(card)
        game.previousPass = false
        onAllRefreshables { refreshAfterPlayCard(card, stackplayed) }
        endTurn()
        return true
    }

    /**
     * This function will put all the handCards in the drawStack, shuffles
     * the cards and then gives the player 5 new handCards from the Stack
     *
     * Preconditions:
     * - The player has at least 8 handCards
     * - The draw stack isn't empty
     */
    fun exchangeCards() : Boolean{
        val game = rootService.currentGame
        checkNotNull(game)

        require(game.players[game.activePlayer].drawStack.isNotEmpty()) { "Draw stack can not be empty" }
        require(game.players[game.activePlayer].handCards.size >= 8) { "Hand cards can not be less than 8" }

        game.players[game.activePlayer].drawStack.addAll(game.players[game.activePlayer].handCards)
        game.players[game.activePlayer].handCards.clear()
        game.players[game.activePlayer].drawStack.shuffle()
        repeat(5){
            game.players[game.activePlayer].handCards.add(game.players[game.activePlayer].drawStack.removeFirst())
        }

        game.previousPass = false
        onAllRefreshables { refreshAfterExchangeCard() }
        endTurn()
        return true
    }

    /**
     * This function describes the action, if the player wants to draw from his drawStack
     * It checks, if the stacks are either empty or the handCard limit is exceeded
     *
     * Preconditions:
     * -The drawStack isn't empty
     * -The HandCards don't exceed the value of 10
     */
    fun drawCard() : Boolean{
        val game = rootService.currentGame
        checkNotNull(game)
        require(game.players[game.activePlayer].drawStack.isNotEmpty()){"Draw stack is null"}
        require(game.players[game.activePlayer].handCards.size < 10){"Too many HandCards"}

        game.players[game.activePlayer].handCards.add(game.players[game.activePlayer].drawStack.removeLast())
        game.previousPass = false
        onAllRefreshables { refreshAfterDrawCard() }
        endTurn()
        return true
    }

    /**
     * This function describes the action, when the player passes on his turn
     * It checks, if the previous Player already passed, if so, it ends the game
     *
     * Preconditions:
     * - The player can't play any other moves
     */
    fun pass(){
        val game = rootService.currentGame
        checkNotNull(game)
        require(game.players[game.activePlayer].drawStack.isEmpty()){"You can draw a Card!"}
        val playStack1 = game.playStack1
        checkNotNull(playStack1)
        val playStack2 = game.playStack2
        checkNotNull(playStack2)
        game.players[game.activePlayer].handCards.forEachIndexed { index, _ ->
            require(runCatching {playCard(game.players[game.activePlayer].handCards[index],
                playStack1)}.exceptionOrNull() is IllegalArgumentException) {"You can play a Card"}
            require(runCatching {playCard(game.players[game.activePlayer].handCards[index],
                playStack2)}.exceptionOrNull() is IllegalArgumentException) {"You can play a Card"}
        }
        if(game.previousPass){
            rootService.gameService.endGame()
        }
        else{
            game.previousPass = true
            endTurn()
        }
    }

    /**
     * This function describes the action, when the player starts his turn
     */
    fun startTurn(){
        val game = rootService.currentGame
        checkNotNull(game)

        onAllRefreshables { refreshAfterStartTurn() }
    }

    private fun endTurn(){
        val game = rootService.currentGame
        checkNotNull(game)
        game.nextPlayer()
        onAllRefreshables { refreshAfterNextPlayer() }
    }
}
