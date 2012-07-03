package org.riksa.bombah.server

import java.util.concurrent.atomic.AtomicLong
import org.slf4j.LoggerFactory
import org.riksa.bombah.thrift.MapState
import org.riksa.bombah.thrift.GameInfo
import org.riksa.bombah.thrift.Tile
import org.riksa.bombah.thrift.Coordinate

import org.riksa.bombah.thrift.GameOverException
import org.riksa.bombah.thrift.Constants
import org.riksa.bombah.thrift.BombActionResult
import org.riksa.bombah.thrift.BombAction
import org.riksa.bombah.thrift.BombState
import org.riksa.bombah.thrift.PlayerState
import org.riksa.bombah.thrift.Disease
import org.riksa.bombah.thrift.Direction
import org.riksa.bombah.thrift.ControllerState
import org.riksa.bombah.thrift.MoveActionResult

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 30.6.2012
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
class Game {
    def log = LoggerFactory.getLogger(getClass())
    static AtomicLong idGenerator = new AtomicLong()
    def id
    def slots
    AtomicLong waiting
    GameInfo gameInfo
    def players = new Vector<PlayerState>()
    def playerIds = new Vector()
    def currentTick
    Timer timer
    def bombs = new Vector<BombState>()
    MapState mapState
    def sleepers = []

    public Game() {
        id = idGenerator.incrementAndGet()
        log.debug("Creating a new game #$id")
        gameInfo = loadMap(13, 11,
                "1  xxxxxxx  2" +
                        " OxOxOxOxOxO " +
                        " xxxxxxxxxxx " +
                        "xOxOxOxOxOxOx" +
                        "xxxxxxxxxxxxx" +
                        "xOxOxOxOxOxOx" +
                        "xxxxxxxxxxxxx" +
                        "xOxOxOxOxOxOx" +
                        " xxxxxxxxxxx " +
                        " OxOxOxOxOxO " +
                        "3  xxxxxxx  4"
        )
        players.clear()
        waiting = new AtomicLong(0)
        currentTick = 0
        slots = 4
        mapState = new MapState(ticksRemaining: gameInfo.ticksTotal)
    }

    def getClientId = {
        Thread.currentThread().id
    }

    synchronized boolean join() {
        if (players.size() >= slots) {
            log.debug("Game is full")
            return false
        }
        def clientId = getClientId();
        def startingPosition = gameInfo.startingPositions.get(players.size())

        playerIds.add(clientId)
        def player = new PlayerState(
                bombSize: 2,
                bombAmount: 1,
                foot: false,
                chain: false,
                disease: Disease.NONE,
                alive: true,
                x: startingPosition.x,
                y: startingPosition.y,
                playerNumber: getPlayerIdx(clientId))

        players.add(player)
        log.debug("Client joined $clientId")
        return true
    }

    GameInfo loadMap(def width, def height, String asciiArt) {
        def rate = Constants.TICKS_PER_SECOND*10
        GameInfo gameInfo = new GameInfo(mapWidth: width, mapHeight: height, ticksTotal: 3 * 60 * Constants.TICKS_PER_SECOND, ticksPerSecond: rate)

        if (asciiArt.length() != width * height) {
            log.error("Map is not $width X $height")
            return
        }

        asciiArt.each {
            if (it == 'x')
                gameInfo.addToTiles(Tile.D_GREY)
            else if (it == 'O')
                gameInfo.addToTiles(Tile.I_GREY)
            else
                gameInfo.addToTiles(Tile.NONE)
        }

        def pos1 = findStartingPosition('1', width, asciiArt)
        if (pos1)
            gameInfo.addToStartingPositions(pos1)
        def pos2 = findStartingPosition('2', width, asciiArt)
        if (pos2)
            gameInfo.addToStartingPositions(pos2)
        def pos3 = findStartingPosition('3', width, asciiArt)
        if (pos3)
            gameInfo.addToStartingPositions(pos3)
        def pos4 = findStartingPosition('4', width, asciiArt)
        if (pos4)
            gameInfo.addToStartingPositions(pos4)
        def pos5 = findStartingPosition('5', width, asciiArt)
        if (pos5)
            gameInfo.addToStartingPositions(pos5)

        return gameInfo
    }

    Coordinate findStartingPosition(String player, width, String asciiArt) {
        def index = asciiArt.indexOf(player)
        if (index != -1) {
            def x = index % width
            def y = (index - x) / width
            new Coordinate(x: x, y: y)
        }
    }

    byte waitForStart() {
        if (waiting.incrementAndGet() == slots) {
            startGame()
        } else {
            // WAIT
            waitTick()
        }

        return getPlayerIdx(getClientId())
    }

    synchronized void tick() throws GameOverException {
        def time = System.currentTimeMillis()
        currentTick++
        synchronized (bombs) {
            bombs.each {
                it.blastSize = getPlayer(it.owner).bombSize
                it.ticksRemaining--
                if (it.moving) {
                    // TODO
                }
            }
        }

        mapState = new MapState(
                ticksRemaining: gameInfo.ticksTotal - currentTick,
                bombs: bombs.clone(), // wasted, TODO
                players: players.clone(),
                tiles: gameInfo.tiles.clone() )
        // inefficient

//        mapState.ticksRemaining = gameInfo.ticksTotal - currentTick
//        mapState.bombs = this.bombs
//        mapState.players = this.players
//        mapState.tiles = gameInfo.tiles
        // TODO: tiles
        // TODO: move players

//        log.debug("Tick #$currentTick, time = $time")
        if (currentTick >= gameInfo.ticksTotal)
        // TODO
            throw new GameOverException()

        synchronized (sleepers) {
            sleepers.each {
                it.interrupt()
            }
            sleepers.clear()
        }

    }

    void startGame() {

        final long sleepTime = 1000l / gameInfo.ticksPerSecond

        if (timer)
            timer.cancel()

        def timerTask = new TimerTask() {
            def previousExecution = System.currentTimeMillis()

            @Override
            void run() {
                def now = System.currentTimeMillis()
                def delta = now - previousExecution
                previousExecution = now
//                log.debug("delta = $delta")
                try {
                    tick()
                } catch (GameOverException e) {
                    log.debug("Game over")
                    timer.cancel()
                    timer = null
                }
            }
        }
        timer = new Timer()
        timer.scheduleAtFixedRate(timerTask, 0, sleepTime)

    }

    void waitTicks(int ticks) {
//        int currentTick = currentTick
//        final long sleepTime = 1000l / gameInfo.ticksPerSecond
//        while (currentTick < currentTick + ticks) {
//            Thread.sleep(sleepTime)
//        }
        ticks.times {
            waitTick()
        }
    }

    void waitTick() {
        final now = currentTick;
        while (currentTick == now) {
            Thread.yield()
        }
//        log.debug("waitTick completed")
//        synchronized (sleepers) {
//            sleepers.add(Thread.currentThread())
//        }
//
//        try {
//            Thread.sleep(3 * 60 * 1000)
//        } catch (InterruptedException e) {
////            log.debug("waitTick completed")
//        }

    }

    BombActionResult bomb(BombAction bombAction) {
        def playerState = getCurrentPlayer()
        def ticks = Constants.TICKS_BOMB
        if (playerState.disease == Disease.FAST_BOMB) {
            ticks >> 1
        }
        if (playerState.disease == Disease.SLOW_BOMB) {
            ticks << 1
        }

        def bomb = new BombState(
                blastSize: playerState.bombSize,
                xCoordinate: Math.round(playerState.x),
                yCoordinate: Math.round(playerState.y),
                ticksRemaining: ticks,
                moving: false,
                direction: Direction.E,
                owner: playerState.playerNumber
        )
        bombs.add(bomb)
        return new BombActionResult(myState: getCurrentPlayer(), mapState: mapState)
    }

    int getPlayerIdx(clientId) {
        playerIds.indexOf(clientId)
    }

    PlayerState getCurrentPlayer() {
        return getPlayer(getPlayerIdx(getClientId()))
    }

    PlayerState getPlayer(def playerIdx) {
        return players.get(playerIdx)
    }

    MoveActionResult controllerEvent(ControllerState controllerState) {
        return new MoveActionResult(myState: getCurrentPlayer(), mapState: mapState)
    }
}
