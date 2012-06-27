package org.riksa.aibombah.server

import org.riksa.aibombah.api.GameApi
import org.riksa.aibombah.api.GameInfo

class GameService implements GameApi {
    static transactional = true
    static expose = ['rmi', 'hessian']

    final Collection<Game> runningGames = new ArrayList<Game>()

    @Override
    String ping() {
        log.debug("ping requested")
        return "pong"
    }

    @Override
    GameInfo createGame() {
        Game game = new Game()
        runningGames.add(game)

        return game.gameInfo
    }

    @Override
    void killGame(long id) {
        runningGames.removeAll {
            it.gameInfo.id == id
        }
    }

    @Override
    Collection<GameInfo> listGames() {
        return runningGames*.gameInfo
    }
}
