package gui

import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.components.uicomponents.TextField
import tools.aqua.bgw.core.Alignment
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import kotlin.system.exitProcess

/**
 * Main Menu Scene, that has the standard options and name selections. It helps to start the game,
 * quit the game and select names for the players.
 */

class MainMenuScene : MenuScene(1440, 900, background = ColorVisual(Color.CYAN)) {
    /**
     * Text field for first player
     */
    val playerOneTextField = TextField(
        posX = 570,
        posY = 300,
        width = 300,
        height = 50,
        visual = ColorVisual(Color(0x808080)),
        font = Font(20.0, Color.WHITE),
        prompt = "Please enter a name.",
        text = "Player 1"
    )

    /**
     * Label for first player
     */
    val playerOneLabel = Label(
        posX = 620,
        posY = 250,
        width = 200,
        height = 75,
        text = "Name Player 1.",
        alignment = Alignment.CENTER,
        font = Font(20.0, Color.WHITE)
    )

    /**
     * Text field for second player
     */
    val playerTwoTextField = TextField(
        posX = 570,
        posY = 400,
        width = 300,
        height = 50,
        visual = ColorVisual(Color(0x808080)),
        font = Font(20.0, Color.WHITE),
        prompt = "Please enter a name.",
        text = "Player 2"
    )

    /**
     * Label for second player
     */
    val playerTwoLabel = Label(
        posX = 620,
        posY = 350,
        width = 200,
        height = 75,
        text = "Name Player 2.",
        alignment = Alignment.CENTER,
        font = Font(20.0, Color.WHITE)
    )

    /**
     * Button, that starts the game
     */
    val startButton = Button(
        posX = 570,
        posY = 500,
        width = 300,
        height = 50,
        text = "START GAME",
        font = Font(color = Color.BLACK),
        visual = ColorVisual.GREEN
    )

    /**
     * Button, that quits the application
     */
    val quitButton = Button(
        posX = 570,
        posY = 600,
        width = 300,
        height = 50,
        text = "QUIT",
        font = Font(color = Color.BLACK),
        visual = ColorVisual.RED
    ).apply {
        onMouseClicked = {
            exitProcess(0)
        }
    }

    /**
     * Adds all components into the application
     */
    init {
        addComponents(
            playerOneTextField,
            playerOneLabel,
            playerTwoTextField,
            playerTwoLabel,
            startButton,
            quitButton
        )
    }
}
