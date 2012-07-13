package org.riksa.bombah.server

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.riksa.bombah.thrift.*

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 7.7.2012
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
class BombahHandler implements BombahService.Iface {
    private static final Logger log = LoggerFactory.getLogger(BombahHandler.class)
    Game runningGame
    ControllerState controllerState

    synchronized Game getGame() {
        if( !runningGame ) {
            runningGame = createGame();
        }
        return runningGame
    }

    @Override
    String ping() {
        return "pong "
    }

    @Override
    ControllerResult controllerEvent(int playerId, ControllerState controllerState) {
        this.controllerState = controllerState;
        return new ControllerResult()
    }

    @Override
    MoveActionResult move(int playerId, MoveAction moveAction) {
        def controllerState = new ControllerState(directionPadDown: true, direction: moveAction.direction, key1Down: false, key2Down: false)
        def done = false
        def currentState = game.controllerEvent( playerId, controllerState ).myState
        def step = 1d / Constants.TICKS_PER_TILE
        if( game.canMove( currentState.x, currentState.y, moveAction.direction, step ) ) {
            Coordinate targetTile = game.getTileCoordinate( currentState.x, currentState.y, moveAction.direction )

            def oldX = currentState.x
            def oldY = currentState.y
            while( !done ) {
                game.waitTick()
                currentState = game.getPlayer(playerId)
                if( Math.abs(oldX - currentState.x)  < 0.001d && Math.abs(oldY - currentState.y) < 0.001d ) {
                    // no longer moving, for whatever reason
//                    log.debug( "Not moving, $currentState")
                    done = true
//                    log.debug( "Moving towards $targetTile at $currentState")
                }
                if( Math.abs(targetTile.x - currentState.x)  < 0.001d && Math.abs(targetTile.y - currentState.y) < 0.001d ) {
                    done = true
                }

                oldX = currentState.x
                oldY = currentState.y
            }
        }
        controllerState.directionPadDown = false
        game.controllerEvent( playerId, controllerState )

        return new MoveActionResult(myState: game.getPlayer(playerId), mapState: game.mapState)
    }

    @Override
    BombActionResult bomb(int playerId, BombAction bombAction) {
        return game.bomb( playerId, bombAction);
    }

    @Override
    MapState waitTicks(int gameId, int ticks) {
        def tick = game.mapState.currentTick+ticks
        return waitForTick( gameId, tick )
    }

    @Override
    MapState waitForTick(int gameId, int tick) {
        game.waitForTick(tick);
        return game.mapState
    }

    @Override
    synchronized GameInfo joinGame(int gameId) {
        if( !runningGame ||runningGame.gameState == Game.GameState.FINISHED ) {
            runningGame = createGame();
        }

        def playerId = game.join()
        if (playerId > 0 ) {
            def info = game.gameInfo.clone()
            info.playerId = playerId
            return info
        } else{
            throw new GameOverException()
        }
    }

    @Override
    GameInfo getGameInfo(int gameId) {
        return game.gameInfo
    }

    @Override
    void debugResetGame(int gameId) {
        runningGame = createGame()
    }

    @Override
    void waitForStart(int gameId) {
//        if( !game ) {
//            return -1;
//        }
        game.waitForStart()
    }

    synchronized Game createGame() {
        return new Game()
    }

    @Override
    MapState getMapState(int gameId) {
        if (runningGame)
            return runningGame.mapState
        return new MapState()
    }
}
