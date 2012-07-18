package org.riksa.bombah.client

@Grapes([
@Grab(group = 'org.apache.httpcomponents', module = 'httpcore', version = '4.2.1'),
@Grab(group = 'org.apache.httpcomponents', module = 'httpclient', version = '4.2.1'),
@Grab(group = 'org.slf4j', module = 'slf4j-log4j12', version = '1.6.6'),
@Grab(group = 'org.apache.thrift', module = 'libthrift', version = '0.8.0'),
])

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
import org.riksa.bombah.thrift.MapState
import org.riksa.bombah.thrift.MoveActionResult
import org.riksa.bombah.thrift.Coordinate
import org.riksa.bombah.thrift.PlayerState
import org.riksa.bombah.thrift.BombState
import org.riksa.bombah.thrift.GameInfo
import org.riksa.bombah.thrift.FlameState
import org.riksa.bombah.thrift.Tile
import java.util.concurrent.atomic.AtomicInteger

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
def username = "testUser"
def clientIdGenerator = new AtomicInteger(1)
//FileInputStream fis =  new FileInputStream("log4j.properties");
//LogManager.getLogManager().readConfiguration(fis);

def clientRunnable = new Runnable() {
    int gameId = -1
    boolean done = false

    @Override
    void run() {
        def transport = createTransport(url)
        try {
            def client = createClient(transport)
            log.debug("#Joining game")
            def gameInfo = client.joinGame(gameId, username, "testClient_#" + clientIdGenerator.incrementAndGet())
            if (gameInfo) {
                def ai = new AmazingAi(gameInfo)
                client.waitForStart(gameId)
                log.debug("Game started, I am player #" + ai.gameInfo.playerId)

                def mapState = client.getMapState(gameId)
                while (!done) {
                    ai.setMapState(mapState)
                    def action = ai.pickAction()
                    switch (action.what) {
                        case Action.ActionTypeEnum.MOVE:
                            log.debug("MOVE")
                            mapState = client.move(ai.gameInfo.playerId, new MoveAction(direction: action.direction)).mapState
                            break
                        case Action.ActionTypeEnum.BOMB:
                            log.debug("BOMB")
                            mapState = client.bomb(ai.gameInfo.playerId, new BombAction(chainBombs: false)).mapState
                            break;
                        case Action.ActionTypeEnum.WAIT:
                            log.debug("WAIT")
                            mapState = client.waitTicks(ai.gameInfo.playerId, 1);
                            break
                    }
                }
            }
        } catch (YouAreDeadException e) {
            log.info("I am dead")
        } catch (GameOverException e) {
            log.info("Game Over")
        } catch (Exception e) {
            done = true
            log.error(e.getMessage(), e)
        } finally {
            transport.close()
            log.debug("#Done...")
        }

    }

}

resetGame(url)

def createTransport(url) {
    return new THttpClient(url)
}

def createClient(transport) {
    transport.open();

    TProtocol protocol = new TJSONProtocol(transport); //TBinaryProtocol(transport);
    return new BombahService.Client(protocol);
}

def resetGame(url) {
    def transport = createTransport(url)
    def client = createClient(transport)
    transport.open()
    client.debugResetGame(-1);
    transport.close()
}

4.times {
    new Thread(clientRunnable).start()
}

//new SimpleObserver( HOST, PORT ).start();
