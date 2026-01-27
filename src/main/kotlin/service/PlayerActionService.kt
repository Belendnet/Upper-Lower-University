package service
import entity.*


/**
 * Provides the action and the consequence of the actions of the player
 */

class PlayerActionService(private val rootService: RootService) : AbstractRefreshingService() {

    /**
     * Checks if a card can be played on a stack without actually playing it.
     * Used to validate moves before executing them.
     *
     * @param card The card to check
     * @param stack The stack to check against
     * @return true if the card can be played on the stack, false otherwise
     */
    private fun canPlayCard(card: Card, stack: Card): Boolean {
        val valueDiff1 = (card.value.ordinal - stack.value.ordinal + 13) % 13
        val valueDiff2 = (stack.value.ordinal - card.value.ordinal + 13) % 13

        return when {
            valueDiff1 == 1 || valueDiff2 == 1 -> true
            (valueDiff1 == 2 || valueDiff2 == 2) && stack.suit == card.suit -> true
            else -> false
        }
    }

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
     * @return true if the card was played successfully, false otherwise
     */
    fun playCard(card : Card, stack : Card) : Boolean{
        val game = rootService.currentGame
        checkNotNull(game)

        if (!canPlayCard(card, stack)) {
            onAllRefreshables { refreshInvalidMove("This card cannot be played on this stack") }
            return false
        }

        // Update the stack based on the move
        if((((card.value.ordinal - stack.value.ordinal + 13) % 13) == 1) ||
            (((stack.value.ordinal - card.value.ordinal + 13) % 13) == 1)){
            stack.suit = card.suit
            stack.value = card.value
        } else {
            stack.value = card.value
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
     *
     * @return true if the cards were exchanged successfully, false otherwise
     */
    fun exchangeCards() : Boolean{
        val game = rootService.currentGame
        checkNotNull(game)

        if(game.players[game.activePlayer].drawStack.isEmpty()) {
            onAllRefreshables { refreshInvalidMove("Draw stack cannot be empty") }
            return false
        }
        if(game.players[game.activePlayer].handCards.size < 8) {
            onAllRefreshables { refreshInvalidMove("You need at least 8 hand cards to exchange") }
            return false
        }

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
     * - The drawStack isn't empty
     * - The HandCards don't exceed the value of 10
     *
     * @return true if the card was drawn successfully, false otherwise
     */
    fun drawCard() : Boolean{
        val game = rootService.currentGame
        checkNotNull(game)

        if(game.players[game.activePlayer].drawStack.isEmpty()){
            onAllRefreshables { refreshInvalidMove("Draw stack is empty") }
            return false
        }
        if(game.players[game.activePlayer].handCards.size >= 10){
            onAllRefreshables { refreshInvalidMove("Too many hand cards (maximum 10)") }
            return false
        }

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
     *
     * @return true if the pass was successful, false otherwise
     */
    fun pass(): Boolean{
        val game = rootService.currentGame
        checkNotNull(game)

        if(game.players[game.activePlayer].drawStack.isNotEmpty()){
            onAllRefreshables { refreshInvalidMove("You can still draw a card") }
            return false
        }

        val playStack1 = game.playStack1
        checkNotNull(playStack1)
        val playStack2 = game.playStack2
        checkNotNull(playStack2)

        // Check if player can play any card
        for (card in game.players[game.activePlayer].handCards) {
            if (canPlayCard(card, playStack1) || canPlayCard(card, playStack2)) {
                onAllRefreshables { refreshInvalidMove("You can still play a card") }
                return false
            }
        }

        if(game.previousPass){
            rootService.gameService.endGame()
        }
        else{
            game.previousPass = true
            endTurn()
        }
        return true
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
