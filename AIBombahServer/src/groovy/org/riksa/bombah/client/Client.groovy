package org.riksa.bombah.client

import org.apache.thrift.protocol.TJSONProtocol
import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.transport.THttpClient
import org.riksa.bombah.thrift.BombAction
import org.riksa.bombah.thrift.BombahService
import org.riksa.bombah.thrift.Direction
import org.riksa.bombah.thrift.MoveAction
import org.slf4j.LoggerFactory
import org.riksa.bombah.thrift.Constants
import org.riksa.bombah.thrift.YouAreDeadException
import org.riksa.bombah.thrift.GameOverException

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 28.6.2012
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */

//def HOST = "localhost"
//def PORT = 12345
def url = "http://localhost:8080/AIBombahServer/bombah/json"
def log = LoggerFactory.getLogger(getClass())
//FileInputStream fis =  new FileInputStream("log4j.properties");
//LogManager.getLogManager().readConfiguration(fis);

def clientRunnable = new Runnable() {

    int gameId = -1

    @Override
    void run() {
        def transport = createTransport(url)
        try {
            def client = createClient(transport)
            log.debug("#Joining game")
            def gameInfo = client.joinGame(gameId)
            if (gameInfo) {
                def playerId = gameInfo.playerId
                log.debug("#Joined game $gameInfo")
                client.waitForStart(gameId)
                log.debug("Game started, I am player #" + playerId)

                client.move(playerId, new MoveAction(direction: Direction.N))
                client.move(playerId, new MoveAction(direction: Direction.N))
                while( true ) {
                    client.bomb(playerId, new BombAction(chainBombs: false))
                    client.move(playerId, new MoveAction(direction: Direction.S))
                    client.move(playerId, new MoveAction(direction: Direction.S))
                    client.move(playerId, new MoveAction(direction: Direction.E))
                    client.move(playerId, new MoveAction(direction: Direction.E))
                    client.waitTicks(gameId, Constants.TICKS_BOMB+Constants.TICKS_FLAME - 6*Constants.TICKS_PER_TILE )
                    client.bomb(playerId, new BombAction(chainBombs: false))
                    client.move(playerId, new MoveAction(direction: Direction.W))
                    client.move(playerId, new MoveAction(direction: Direction.W))
                    client.move(playerId, new MoveAction(direction: Direction.N))
                    client.move(playerId, new MoveAction(direction: Direction.N))
                    client.waitTicks(gameId, Constants.TICKS_BOMB+Constants.TICKS_FLAME - 6*Constants.TICKS_PER_TILE )
                }

            }
        } catch ( YouAreDeadException e ) {
            log.info( "I am dead")
        } catch ( GameOverException e ) {
            log.info( "Game Over")
        } catch ( Exception e ) {
            log.error( e.getMessage(), e )
        } finally {
            transport.close()
            log.debug("#Done...")
        }

    }
}

resetGame(url)

def createTransport( url ) {
    return new THttpClient(url)
}

def createClient( transport ) {
    transport.open();

    TProtocol protocol = new TJSONProtocol(transport); //TBinaryProtocol(transport);
    return new BombahService.Client(protocol);
}

def resetGame(url) {
    def transport = createTransport(url)
    def client = createClient(transport)
    transport.open()
    client.debugResetGame( -1 );
    transport.close()
}

4.times {
    new Thread(clientRunnable).start()
}

//new SimpleObserver( HOST, PORT ).start();
