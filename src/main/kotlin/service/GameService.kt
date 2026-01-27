package service

import entity.*
import kotlin.random.Random

/**
 * This service provides the logic, that is not caused by a single Player
 */

class GameService(private val rootService: RootService): AbstractRefreshingService() {
    /**
     * This function builds up the game and gives every Player the standard Cards and their sets
     *
     * @param playerNames Describes the names, that each player has
     */
    fun buildGameService(playerNames : MutableList<String>){
        require(playerNames.size == 2){"Size is not valid!"}
        val game = Game(selectRandomPlayer())
        for (name in playerNames) {
            game.players.add(Player(name))
        }

        rootService.currentGame = game

        val cards = createDeck()
        cards.shuffle()
        dealCards(cards)
        onAllRefreshables { refreshAfterBuildGame() }
        rootService.playerActionService.startTurn()
    }

    /**
     * This function ends the game and will determine the winner
     */
    fun endGame(){
        val game = rootService.currentGame
        checkNotNull(game)

        val player1 = game.players[0]
        val player2 = game.players[1]
        var winner : String? = null
        if(player1.handCards.size + player1.drawStack.size > player2.handCards.size + player2.drawStack.size ){
            winner = player2.name
        }
        else if(player1.handCards.size + player1.drawStack.size < player2.handCards.size + player2.drawStack.size ){
             winner = player1.name
        }
        rootService.currentGame = null
        onAllRefreshables { refreshAfterEndGame(winner) }
    }

    /**
     * This function creates a card deck, that will be used in the game
     *
     * @return Returns the finished card stack
     */
    private fun createDeck(): MutableList<Card> {
        val cards : MutableList<Card> = mutableListOf()
        for(cardValue in CardValue.entries){
            cards.add(Card(cardValue,CardSuit.HEARTS))
            cards.add(Card(cardValue,CardSuit.SPADES))
            cards.add(Card(cardValue,CardSuit.CLUBS))
            cards.add(Card(cardValue,CardSuit.DIAMONDS))
        }
        return cards
    }

    /**
     * This function deals the card to each player fairly and puts 1 card
     * from each player in the deck
     *
     * @param cards The card deck, that will be distributed
     */
    private fun dealCards(cards : List<Card>){
        val game = rootService.currentGame
        checkNotNull(game)
        for(card in cards){
            when(cards.indexOf(card) % 2){
                0 -> game.players[0].drawStack.add(card)
                1 -> game.players[1].drawStack.add(card)
            }
        }
        game.playStack1 = game.players[0].drawStack.removeFirst()
        game.playStack2 = game.players[1].drawStack.removeFirst()
        repeat(5){
            game.players[0].handCards.add(game.players[0].drawStack.removeFirst())
            game.players[1].handCards.add(game.players[1].drawStack.removeFirst())
        }

    }

    /**
     * This function will select a random player, that will begin his turn
     */
    private fun selectRandomPlayer() =  Random.nextInt(2)
}
