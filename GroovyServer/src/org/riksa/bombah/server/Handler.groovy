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

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 28.6.2012
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */
class Handler implements BombahService.Iface {
    private static final Logger log = LoggerFactory.getLogger( Handler.class )
    def game

    def getThreadId = {
        Thread.currentThread().id
    }

    @Override
    String ping() {
        def clientId = getThreadId()

        log.debug( "Ping from client $clientId")

        return "pong "+clientId
    }

    @Override
    ControllerResult controllerEvent(ControllerState controllerState) {
        return null  //To change body of implemented methods use File | Settings | File Templates.
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
    synchronized MapState joinGame() {
        def clientId = getThreadId()
        log.debug( "joinGame from $clientId")

        if( !game )
            game = createGame()

        game.join( clientId )

        return game.mapState
    }

    synchronized Game createGame() {
        return new Game()
    }

    @Override
    MapState getMapState() {
        return null  //To change body of implemented methods use File | Settings | File Templates.
    }
}
