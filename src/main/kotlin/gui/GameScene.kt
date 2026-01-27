package gui


import entity.*
import service.RootService
import tools.aqua.bgw.components.container.CardStack
import tools.aqua.bgw.components.container.LinearLayout
import tools.aqua.bgw.components.gamecomponentviews.CardView
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.BoardGameScene
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.util.BidirectionalMap
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import tools.aqua.bgw.visual.CompoundVisual
import tools.aqua.bgw.visual.Visual

/**
 * This scene is the actual game, in which the players have different options to interact
 * with the game.
 */
class GameScene(private val rootService: RootService) : BoardGameScene
    (1920, 1080, background = ColorVisual(Color.CYAN)), Refreshable{
    private val cardMap: BidirectionalMap<Card, CardView> = BidirectionalMap()
    private var selectedHandCard : CardView? = null
    /**
     * Displays the name of the first player
     */
    private val playerOneName = Label(
        posX = 620,
        posY = 850,
        width = 200,
        height = 75,
        text = "Player 1",
        alignment = Alignment.CENTER,
        font = Font(20.0, Color.WHITE)
    )

    /**
     * Displays the hand cards of the first player
     */
    private val playerOneHandCard = LinearLayout<CardView>(
        posX = 220,
        posY = 750,
        width = 1000,
        height = 130,
        alignment = Alignment.CENTER,
        spacing = -60
    )

    /**
     * Displays the name of the second Player
     */
    private val playerTwoName = Label(
        posX = 620,
        posY = 50,
        width = 200,
        height = 75,
        text = "Player 2",
        alignment = Alignment.CENTER,
        font = Font(20.0, Color.WHITE)
    )

    /**
     * Displays the hand Cards of the second player
     */
    private val playerTwoHandCard = LinearLayout<CardView>(
        posX = 220,
        posY = 150,
        width = 1000,
        height = 130,
        alignment = Alignment.CENTER,
        spacing = -60
    )

    /**
     * Displays the first play stack in the middle
     */
    private val playStack1 = CardStack<CardView>(
        posX = 620,
        posY = 400,
        width = 130,
        height = 200,

    )

    /**
     * Displays the second play stack in the middle
     */
    private val playStack2 = CardStack<CardView>(
        posX = 820,
        posY = 400,
        width = 130,
        height = 200,
        alignment = Alignment.CENTER,
        )

    /**
     * Hidden Draw Stack, that is used to update the draw Stack of the first player
     */
    private val hiddenPlayStack1 = CardView(
        posX = 620,
        posY = 400,
        width = 130,
        height = 200,
        front = Visual.EMPTY
    ).apply {
        onMouseClicked = {
            if(selectedHandCard != null) {
                val game = rootService.currentGame
                checkNotNull(game)
                selectedHandCard?.scaleY(1.0)
                rootService.playerActionService.playCard(cardMap.
                backward(selectedHandCard as CardView), game.playStack1 as Card)
                selectedHandCard = null
            }
        }
    }

    /**
     * Hidden Draw Stack, that is used to update the draw Stack of the second player
     */
    private val hiddenPlayStack2 = CardView(
        posX = 820,
        posY = 400,
        width = 130,
        height = 200,
        front = Visual.EMPTY
    ).apply {
        onMouseClicked = {
            if(selectedHandCard != null) {
                val game = rootService.currentGame
                checkNotNull(game)
                selectedHandCard?.scaleY(1.0)
                rootService.playerActionService.playCard(cardMap.
                backward(selectedHandCard as CardView), game.playStack2 as Card)
                selectedHandCard = null
            }
        }
    }

    /**
     * Displays the draw Stack of the first player
     */
    private val drawStack1 = CardStack<CardView>(
        posX = 100,
        posY = 750,
        width = 130,
        height = 200,
        alignment = Alignment.CENTER,
    ).apply { onMouseClicked = {
        rootService.playerActionService.drawCard()
    } }

    /**
     * Displays the draw Stack of the second player
     */
    private val drawStack2 = CardStack<CardView>(
        posX = 100,
        posY = 250,
        width = 130,
        height = 200,
        alignment = Alignment.CENTER,
    ).apply { onMouseClicked = {
        rootService.playerActionService.drawCard()
    } }

    /**
     * Button, that passes onto the next player
     */
    private val passButton = Button(
        posX = 220,
        posY = 150,
        width = 150,
        height = 50,
        text = "PASS",
        visual = ColorVisual.ORANGE
    ).apply {
        onMouseClicked = {
            rootService.playerActionService.pass()
        }
    }

    /**
     * Button, that starts the turn of the player
     */
    private val startTurnButton = Button(
        posX = 1920/2 - 75,
        posY = 1080/2 - 25,
        width = 150,
        height = 50,
        text = "Start Turn",
    ).apply{
        onMouseClicked = {
            rootService.playerActionService.startTurn()
        }

    }

    /**
     * Exchange Button to exchange the cards
     */
    private val exchangeCardButton = Button(
        posX = 220,
        posY = 250,
        width = 150,
        height = 50,
        text = "Exchange Card",
    ).apply{
        onMouseClicked = {
            rootService.playerActionService.exchangeCards()
        }
    }

    /**
     * Adds all components to the scene
     */
    init {
        addComponents(
            playerOneName,
            playerOneHandCard,
            playerTwoName,
            playerTwoHandCard,
            playStack1,
            playStack2,
            hiddenPlayStack1,
            hiddenPlayStack2,
            passButton,
            startTurnButton,
            drawStack2,
            drawStack1,
            exchangeCardButton
        )
    }

    /**
     * Refreshes the game, after it has been started by the button on menu
     */
    override fun refreshAfterBuildGame() {
        val game = rootService.currentGame
        checkNotNull(game)

        val cardImageLoader = CardImageLoader()
        cardMap.clear()
        game.players[0].drawStack.forEach{
            card -> cardMap[card] = CardView(
                posX = 0,
                posY = 0,
                height = 200,
                width = 130,
                front = cardImageLoader.frontImageFor(card.suit, card.value),
                back = cardImageLoader.backImage
            ).apply {
                onMouseClicked = {
                    if(selectedHandCard != null) {
                    selectedHandCard?.scaleY(1.0)
                }
                selectedHandCard = this
                this.scaleY(1.1)
            }
        }
        }

        game.players[1].drawStack.forEach{
                card -> cardMap[card] = CardView(
            posX = 0,
            posY = 0,
            height = 200,
            width = 130,
            front = cardImageLoader.frontImageFor(card.suit, card.value),
            back = cardImageLoader.backImage
        ).apply {
            onMouseClicked = {
                if(selectedHandCard != null) {
                    selectedHandCard?.scaleY(1.0)
                }
                selectedHandCard = this
                this.scaleY(1.1)
            }
        }
        }

        game.players[0].handCards.forEach{
                card -> cardMap[card] = CardView(
            posX = 0,
            posY = 0,
            height = 200,
            width = 130,
            front = cardImageLoader.frontImageFor(card.suit, card.value),
            back = cardImageLoader.backImage
        ).apply {
            onMouseClicked = {
                if(selectedHandCard != null) {
                    selectedHandCard?.scaleY(1.0)
                }
                selectedHandCard = this
                this.scaleY(1.1)
            }
        }
        }

        game.players[1].handCards.forEach{
                card -> cardMap[card] = CardView(
            posX = 0,
            posY = 0,
            height = 200,
            width = 130,
            front = CompoundVisual(cardImageLoader.frontImageFor(card.suit, card.value)),
            back = cardImageLoader.backImage
        ).apply {
            onMouseClicked = {
                if(selectedHandCard != null) {
                    selectedHandCard?.scaleY(1.0)
                }
                selectedHandCard = this
                this.scaleY(1.1)
            }
        }
        }

        val playStack12 = game.playStack1
        checkNotNull(playStack12)
        cardMap[playStack12] = CardView(
            posX = 0,
            posY = 0,
            height = 200,
            width = 130,
            front = cardImageLoader.frontImageFor(playStack12.suit, playStack12.value),
            back = cardImageLoader.backImage
        )

        game.players[0].handCards.forEach{
            card -> playerOneHandCard.add(cardMap[card] as CardView)
        }
        game.players[1].handCards.forEach{
                card -> playerTwoHandCard.add(cardMap[card] as CardView)
        }

        val playStack11 = game.playStack1
        checkNotNull(playStack11)
        val playStack21 = game.playStack2
        checkNotNull(playStack21)
        cardMap[playStack21] = CardView(
            posX = 0,
            posY = 0,
            height = 200,
            width = 130,
            front = cardImageLoader.frontImageFor(playStack21.suit, playStack21.value),
            back = cardImageLoader.backImage
        )
        cardMap[playStack11] = CardView(
            posX = 0,
            posY = 0,
            height = 200,
            width = 130,
            front = cardImageLoader.frontImageFor(playStack11.suit, playStack11.value),
            back = cardImageLoader.backImage
        )

        playStack1.add(cardMap[playStack11] as CardView)
        playStack2.add(cardMap[playStack21] as CardView)
        playStack1.peek().showFront()
        playStack2.peek().showFront()
        playerOneName.text = game.players[0].name
        playerTwoName.text = game.players[1].name
        drawStack1.add(
            CardView(
                posX = 0,
                posY = 0,
                height = 200,
                width = 130,
                front = cardImageLoader.backImage
            )
        )
        drawStack2.add(
            CardView(
                posX = 0,
                posY = 0,
                height = 200,
                width = 130,
                front = cardImageLoader.backImage
            )
        )
    }

    /**
     * Refreshes the game, after the turn of a player.
     */
    override fun refreshAfterStartTurn() {
        val game = rootService.currentGame
        checkNotNull(game)

        if(game.activePlayer == 0){
            playerOneHandCard.forEach { card ->
                card.showFront()
            }
        }
        else if (game.activePlayer == 1){
            playerTwoHandCard.forEach { card ->
                card.showFront()
            }
        }
        startTurnButton.isVisible = false
    }

    /**
     * Refreshes the game, after a card has been played
     */
    override fun refreshAfterPlayCard(card : Card, stack : Int) {
        val game = rootService.currentGame
        checkNotNull(game)

        if(game.activePlayer == 0){
            playerOneHandCard.remove(cardMap[card] as CardView)
            if(stack == 0){
                playStack1.add(cardMap[card] as CardView)
            } else if (stack == 1){
                playStack2.add(cardMap[card] as CardView)
            }
        }
        if(game.activePlayer == 1){
            playerTwoHandCard.remove(cardMap[card] as CardView)
            if(stack == 0){
                playStack1.add(cardMap[card] as CardView)
            } else if (stack == 1){
                playStack2.add(cardMap[card] as CardView)
            }
        }
        val cardImageLoader = CardImageLoader()
        cardMap[card] = CardView(
            posX = 0,
            posY = 0,
            height = 200,
            width = 130,
            front = cardImageLoader.frontImageFor(card.suit, card.value),
            back = cardImageLoader.backImage
        ).apply {
            onMouseClicked = {
                if(selectedHandCard != null) {
                    selectedHandCard?.scaleY(1.0)
                    rootService.playerActionService.playCard(cardMap.
                    backward(selectedHandCard as CardView), game.playStack2 as Card)
                    selectedHandCard = null
                }
            }
        }
    }

    /**
     * Refreshes the game after a card has been drawn from the draw Stack.
     */
    override fun refreshAfterDrawCard() {
        val game = rootService.currentGame
        checkNotNull(game)
        if(game.activePlayer == 0){
            playerOneHandCard.add(cardMap[game.players[0].handCards.last()] as CardView)
        } else if(game.activePlayer == 1){
            playerTwoHandCard.add(cardMap[game.players[1].handCards.last()] as CardView)
        }
        if(game.players[0].drawStack.isEmpty()){
            drawStack1.isVisible = false
        } else if(game.players[1].drawStack.isEmpty()){
            drawStack2.isVisible = false
        }
    }

    /**
     * Refreshes the game after a player has been switched
     */
    override fun refreshAfterNextPlayer() {
        val game = rootService.currentGame
        checkNotNull(game)
        if(game.activePlayer == 0){
            playerTwoHandCard.forEach { card ->
                card.showBack()
            }
        } else if(game.activePlayer == 1){
            playerOneHandCard.forEach { card ->
                card.showBack()
            }
        }
        startTurnButton.isVisible = true
    }

    /**
     * Refreshes the game, after a player exchanged his cards.
     */
    override fun refreshAfterExchangeCard() {
        val game = rootService.currentGame
        checkNotNull(game)
        if(game.activePlayer == 0){
            playerOneHandCard.clear()
            game.players[0].handCards.forEach{
                    card -> playerOneHandCard.add(cardMap[card] as CardView)
            }
        } else if(game.activePlayer == 1){
            playerTwoHandCard.clear()
            game.players[1].handCards.forEach{
                    card -> playerTwoHandCard.add(cardMap[card] as CardView)
            }
        }
    }
}
