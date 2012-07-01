package org.riksa.bombah.server

import org.riksa.bombah.thrift.BombahService
import org.riksa.bombah.thrift.ControllerResult
import org.riksa.bombah.thrift.ControllerState
import org.riksa.bombah.thrift.MoveActionResult
import org.riksa.bombah.thrift.MoveAction
import org.riksa.bombah.thrift.BombActionResult
import org.riksa.bombah.thrift.BombAction
import org.riksa.bombah.thrift.MapState
import org.riksa.bombah.thrift.GameOverException
import org.slf4j.LoggerFactory
import org.slf4j.Logger
import org.riksa.bombah.thrift.GameInfo
import org.riksa.bombah.thrift.Constants

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 28.6.2012
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */
class Handler implements BombahService.Iface {
    private static final Logger log = LoggerFactory.getLogger( Handler.class )
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
        this.controllerState = new ControllerState( directionPadDown: true, direction: moveAction.direction, key1Down: false, key2Down: false )
        game.waitTicks( Constants.TICKS_PER_TILE );
        this.controllerState.directionPadDown = false
        return new MoveActionResult( myState: game.getCurrentPlayer(), mapState: game.mapState )
    }

    @Override
    BombActionResult bomb(BombAction bombAction) {
        return game.bomb( bombAction );
    }

    @Override
    MapState waitTicks(int ticks) {
        game.waitTicks( ticks );
        return game.mapState
    }

    @Override
    synchronized GameInfo joinGame() {

        if( !game )
            game = createGame()

        if( game.join() ) {
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
        if( game )
            return game.mapState
        return new MapState()
    }
}
