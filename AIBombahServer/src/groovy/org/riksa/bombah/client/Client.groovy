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

Coordinate getTileCoordinate(x, y, direction) {
    def tileX = Math.round(x)
    def tileY = Math.round(y)
    switch (direction) {
        case Direction.N:
            return new Coordinate(x: tileX, y: tileY + 1)
        case Direction.E:
            return new Coordinate(x: tileX + 1, y: tileY)
        case Direction.W:
            return new Coordinate(x: tileX - 1, y: tileY)
        case Direction.S:
            return new Coordinate(x: tileX, y: tileY - 1)
        default:
            return new Coordinate(x: tileX, y: tileY)
    }
}

def clientRunnable = new Runnable() {

    int gameId = -1
    GameInfo gameInfo
    MapState mapState
    PlayerState myState
    def spec

    @Override
    void run() {
        def transport = createTransport(url)
        try {
            def client = createClient(transport)
            log.debug("#Joining game")
            gameInfo = client.joinGame(gameId)
            if (gameInfo) {
                def playerId = gameInfo.playerId
                log.debug("#Joined game $gameInfo")
                client.waitForStart(gameId)
                log.debug("Game started, I am player #" + playerId)

                mapState = client.getMapState(gameId)
                while (true) {
                    myState = mapState.players.find { it.playerId == playerId }
                    log.debug("My state $myState")
                    def action = pickAction()
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

    Action pickAction() {
        def flameSpeculation
        List<Direction> path

        if (canBomb()) {
            flameSpeculation = buildFlameSpeculation(true)
            path = findSafePath(flameSpeculation)
            if (path) {
                log.debug("Found path with bomb here $path")
                return new Action(what: ActionTypeEnum.BOMB)
            } else {
                log.debug("Bombing would be suicidal")
            }
        }

        flameSpeculation = buildFlameSpeculation(false)
        path = findSafePath(flameSpeculation)
        if (path) {
            log.debug("Found path without bomb here $path")
            return new Action(what: ActionTypeEnum.MOVE, direction: path.get(0))
        }

        def direction = [Direction.N, Direction.E, Direction.W, Direction.S].get(random.nextInt(4))
        return new Action(what: ActionTypeEnum.MOVE, direction: direction)
    }

    List<Direction> findSafePath(def flameSpeculations) {
        def safe = [Direction.N, Direction.E, Direction.W, Direction.S].grep {
            def targetLocation = getTileCoordinate(myState.x, myState.y, it)
            // TODO: BFS
            canMoveSafe(targetLocation, 0, flameSpeculations)
        }

        if (safe) {
            def idx = random.nextInt(safe.size())
            return [safe[idx]]
        }

    }

    def canMoveSafe(Coordinate coordinate, int tick, FlameSpeculation[][] flameSpeculations) {
        if (canMoveTo(coordinate.x, coordinate.y)) {
            // TODO: how long would it take to be there and beyond, for now assume next tile
            def spec = flameSpeculations[coordinate.x][coordinate.y]
            if (spec.start > tick + Constants.TICKS_PER_TILE) {
                // we got time to move to and from the tile without dying
                return true
            }
            if (spec.stop < tick ) {
                // flame is out by the time we get there
                return true
            }
        }
    }

    boolean canMoveTo(int x, int y) {
        if (x < 0 || x >= gameInfo.mapWidth) {
//            log.debug("x out of bounds ($x, $y)")
            return false
        }

        if (y < 0 || y >= gameInfo.mapHeight) {
//            log.debug("x out of bounds ($x, $y)")
            return false
        }

        def tile = getTile(x, y)
        if (tile == Tile.DESTRUCTIBLE || tile == Tile.INDESTRUCTIBLE)
            return false

        if (mapState.bombs.find {
            BombState bomb ->
            def coordinate = getTileCoordinate(bomb.xCoordinate, bomb.yCoordinate, null)
            return (coordinate.x == x && coordinate.y == y)
        }) return false

        return true
    }

    Tile getTile(int x, int y) {
        int idx = x + y * gameInfo.mapWidth
        if (idx >= 0 && idx < gameInfo.tiles.size())
            return gameInfo.tiles.get(idx)
    }

    FlameState getTileFlame(int x, int y) {
        mapState.flames.find {
            it.coordinate.x == x && it.coordinate.y == y
        }
    }

    class FlameSpeculation {
        int start
        int stop
    }

    def buildFlameSpeculation(boolean dropBomb) {
        if (!spec) {
            spec = new FlameSpeculation[gameInfo.mapWidth][gameInfo.mapHeight]
            for (y in 0..gameInfo.mapHeight - 1)
                for (x in 0..gameInfo.mapWidth - 1) {
                    spec[x][y] = new FlameSpeculation()
                }

        }

        for (y in 0..gameInfo.mapHeight - 1)
            for (x in 0..gameInfo.mapWidth - 1) {
                spec[x][y].start = Integer.MAX_VALUE
                spec[x][y].stop = Integer.MAX_VALUE
            }

        mapState.flames.each {
            FlameState flame ->
            spec[flame.coordinate.x][flame.coordinate.y].start = 0
            spec[flame.coordinate.x][flame.coordinate.y].stop = flame.ticksRemaining
        }

        // TODO: process bombs

        return spec
    }

    boolean canBomb() {
        def coordinate = getTileCoordinate(myState.x, myState.y, null)
        return !getTileBomb(coordinate.x, coordinate.y)
    }

    boolean getTileBomb(byte x, byte y) {
        mapState.bombs.find {
            BombState bomb ->
            def coordinate = getTileCoordinate(bomb.xCoordinate, bomb.yCoordinate, null)
            coordinate.x == x && coordinate.y == y
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
