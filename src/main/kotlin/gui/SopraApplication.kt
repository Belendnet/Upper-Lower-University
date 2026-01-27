package gui

import service.RootService
import tools.aqua.bgw.core.BoardGameApplication

/**
 * The Application, which runs the whole game. It opens a windows, which displays the game/menu scenes.
 */
class SopraApplication : BoardGameApplication("SoPra Game"), Refreshable{
    /**
     * The different scenes and rootService to access the services/entities
     */
    private val rootService = RootService()
    private val mainMenuScene = MainMenuScene()
    private val resultMenuScene = ResultMenuScene(rootService)
    private val gameScene = GameScene(rootService)

    /**
     * Initializes the game
     */
    init {
        rootService.addRefreshable(gameScene)
        rootService.addRefreshable(resultMenuScene)
        showMenuScene()
        this.showMenuScene(mainMenuScene)
        show()
    }
    /**
     * Function, that allows the start button to enter the game scene
     */
    private fun showMenuScene() {
        mainMenuScene.startButton.onMouseClicked = {
                hideMenuScene()
                rootService.gameService.buildGameService(mutableListOf(
                    mainMenuScene.playerOneTextField.text.trim(),
                    mainMenuScene.playerTwoTextField.text.trim()))
                showGameScene(gameScene)
        }
    }

    override fun refreshAfterEndGame(winner: String?) {
        this.showMenuScene(ResultMenuScene(rootService))
    }
}
