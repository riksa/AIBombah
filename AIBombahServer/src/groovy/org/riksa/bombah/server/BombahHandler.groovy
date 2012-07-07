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
        if( runningGame.gameState == Game.GameState.FINISHED ) {
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
        game.controllerEvent( playerId, controllerState )
        game.waitTicks(Constants.TICKS_PER_TILE);
        controllerState.directionPadDown = false
        game.controllerEvent( playerId, controllerState )
        return new MoveActionResult(myState: game.getPlayer(playerId), mapState: game.mapState)
    }

    @Override
    BombActionResult bomb(int playerId, BombAction bombAction) {
        return game.bomb( playerId, bombAction);
    }

    @Override
    MapState waitTicks(int ticks) {
        game.waitTicks(ticks);
        return game.mapState
    }

    @Override
    synchronized GameInfo joinGame(int gameId) {
        def playerId = game.join()
        if (playerId > 0 ) {
            def info = game.gameInfo
            info.playerId = playerId
            return info
        } else{
            throw new GameOverException()
        }
    }

    @Override
    void waitForStart() {
//        if( !game ) {
//            return -1;
//        }
        game.waitForStart()
    }

    synchronized Game createGame() {
        return new Game()
    }

    @Override
    MapState getMapState() {
        if (game)
            return game.mapState
        return new MapState()
    }
}
