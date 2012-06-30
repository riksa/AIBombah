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

    @Override
    String ping() {
        return "pong "
    }

    @Override
    ControllerResult controllerEvent(ControllerState controllerState) {
        return new ControllerResult()
    }

    @Override
    MoveActionResult move(MoveAction moveAction) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    BombActionResult bomb(BombAction bombAction) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
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
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }
}
