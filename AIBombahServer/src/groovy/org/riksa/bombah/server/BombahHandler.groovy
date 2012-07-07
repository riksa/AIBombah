package org.riksa.bombah.server

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import thrift.BombahService
import thrift.ControllerState
import thrift.ControllerResult
import thrift.MoveActionResult
import thrift.MoveAction
import thrift.Constants
import thrift.BombActionResult
import thrift.BombAction
import thrift.MapState
import thrift.GameInfo

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 7.7.2012
 * Time: 10:45
 * To change this template use File | Settings | File Templates.
 */
class BombahHandler implements BombahService.Iface {
    private static final Logger log = LoggerFactory.getLogger(BombahHandler.class)
    Game game
    ControllerState controllerState

    @Override
    String ping() {
        return "pong "
    }

    @Override
    ControllerResult controllerEvent(ControllerState controllerState) {
        this.controllerState = controllerState;
        return new ControllerResult()
    }

    @Override
    MoveActionResult move(MoveAction moveAction) {
        this.controllerState = new ControllerState(directionPadDown: true, direction: moveAction.direction, key1Down: false, key2Down: false)
        game.waitTicks(Constants.TICKS_PER_TILE);
        this.controllerState.directionPadDown = false
        return new MoveActionResult(myState: game.getCurrentPlayer(), mapState: game.mapState)
    }

    @Override
    BombActionResult bomb(BombAction bombAction) {
        return game.bomb(bombAction);
    }

    @Override
    MapState waitTicks(int ticks) {
        game.waitTicks(ticks);
        return game.mapState
    }

    @Override
    synchronized GameInfo joinGame() {

        if (!game)
            game = createGame()

        if (game.join()) {
            return game.gameInfo
        }

    }

    @Override
    byte waitForStart() {
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
