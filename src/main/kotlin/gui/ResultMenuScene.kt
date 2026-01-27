package gui

import service.RootService
import tools.aqua.bgw.components.uicomponents.Button
import tools.aqua.bgw.components.uicomponents.Label
import tools.aqua.bgw.core.Color
import tools.aqua.bgw.core.MenuScene
import tools.aqua.bgw.util.Font
import tools.aqua.bgw.visual.ColorVisual
import kotlin.system.exitProcess

/**
 * This result menu scene shows the winnings player (or draw) after a game
 * and gives the player the option to quit the application to restart.
 */

class ResultMenuScene(private val rootService: RootService) : MenuScene(1920,1080),Refreshable {
    /**
     * Label, that displays the winner
     */
    private val winnerLabel = Label(
        posX = 1920 / 2,
        posY = 1080 / 2,
        width = 300,
        height = 50,
    )

    /**
     * Button, that quits the application
     */
    private val quitButton = Button(
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
     * Adds the components into the scene
     */
    init {
         addComponents(
             quitButton,
             winnerLabel
         )
    }

    /**
     * Overrides the function, it determines, which player won.
     */
    override fun refreshAfterEndGame(winner : String?){
        val game = rootService.currentGame
        checkNotNull(game)
        winnerLabel.text = "$winner won the game!"
    }
}
