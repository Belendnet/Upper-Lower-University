package service
import gui.Refreshable


/**
 * This class is responsible to refresh the application after each action
 *
 * @property refreshables List of all possible refreshables after an action
 */
abstract class AbstractRefreshingService {
    private val refreshables = mutableListOf<Refreshable>()

    /**
     * This functions adds all new refreshables onto the existing refreshables
     */
    fun addRefreshable(newRefreshable: Refreshable) {
        refreshables += newRefreshable
    }

    /**
     * This functions lists all refreshables
     */
    fun onAllRefreshables(method: Refreshable.() -> Unit) {
        refreshables.forEach { it.method()}
    }

}
