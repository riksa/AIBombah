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
def random = new Random()

enum ActionTypeEnum {
    MOVE, BOMB, WAIT
}

class Action {
    ActionTypeEnum what
    Direction direction
}

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

                def mapState = client.getMapState(gameId)
                while (true) {
                    def action = pickAction(mapState)
                    switch (action.what) {
                        case ActionTypeEnum.MOVE:
                            mapState = client.move(playerId, new MoveAction(direction: action.direction)).mapState
                            break;
                        case ActionTypeEnum.BOMB:
                            mapState = client.bomb(playerId, new BombAction(chainBombs: false)).mapState
                            break;
                        case ActionTypeEnum.WAIT:
                            mapState = client.waitTicks(playerId, 1);
                            break;
                    }
                }
            }
        } catch (YouAreDeadException e) {
            log.info("I am dead")
        } catch (GameOverException e) {
            log.info("Game Over")
        } catch (Exception e) {
            log.error(e.getMessage(), e)
        } finally {
            transport.close()
            log.debug("#Done...")
        }

    }

    Action pickAction(MapState mapState) {
        Action action = new Action()
        action.direction = Direction.values()[random.nextInt(4)<<1]
        if (random.nextInt(100) < 5)
            action.what = ActionTypeEnum.BOMB
        else
            action.what = ActionTypeEnum.MOVE

        log.debug( "Action ${action.direction}")
        return action;
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
