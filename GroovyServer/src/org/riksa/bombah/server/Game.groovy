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
    def players = new ArrayList<PlayerState>()
    def List playerIds = new ArrayList()
    def tick
    Timer timer
    List<BombState> bombs = []
    MapState mapState = new MapState()

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
        tick = 0
        slots = 4
        mapState.ticksRemaining = gameInfo.ticksTotal
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
                        playerNumber: getPlayerIdx(clientId) )

        players.add( player )
        log.debug("Client joined $clientId")
        return true
    }

    GameInfo loadMap(def width, def height, String asciiArt) {
        def rate = 1 // Constants.TICKS_PER_SECOND
        GameInfo gameInfo = new GameInfo(mapWidth: width, mapHeight: height, ticksTotal: 3 * 60 * Constants.TICKS_PER_SECOND, ticksPerSecond: rate )

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
        }

        return getPlayerIdx(getClientId())
    }

    synchronized void tick() throws GameOverException {
        def time = System.currentTimeMillis()
        tick++
        bombs.each {
            it.blastSize = getPlayer( it.owner ).bombSize
            it.ticksRemaining--
            if( it.moving ) {
                // TODO
            }
        }

        mapState.ticksRemaining = gameInfo.ticksTotal - tick
        mapState.bombs = this.bombs
        mapState.players = this.players
        mapState.tiles = gameInfo.tiles
        // TODO: tiles
        // TODO: move players

        log.debug("Tick #$tick, time = $time")
        if (tick >= gameInfo.ticksTotal)
            // TODO
            throw new GameOverException()
    }

//    def gameRunnable = new Runnable() {
//        @Override
//        void run() {
//            def running = true
//            while( running ) {
//                try {
//                    Thread.sleep( 1000l )
//                    log.error( "Noone woke me up and I died :(")
//                    running = false
//                } catch( InterruptedException ie ) {
//                    tick()
//                }
//            }
//        }
//    }

    void startGame() {
//        def final gameThread = new Thread( gameRunnable )
//        gameThread.start()

        final long sleepTime = 1000l / gameInfo.ticksPerSecond
//        def tickerRunnable = new Runnable() {
//            @Override
//            void run() {
//                def running = true
//                while( running ) {
//                    try {
//                        Thread.sleep( sleepTime );
//                        gameThread.interrupt()
//                    } catch( InterruptedException ie ) {
//                        running = false
//                    }
//                }
//            }
//        }

        if (timer)
            timer.cancel()

        def timerTask = new TimerTask() {
            def previousExecution = System.currentTimeMillis()

            @Override
            void run() {
                def now = System.currentTimeMillis()
                def delta = now - previousExecution
                previousExecution = now
                log.debug("delta = $delta")
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

//        tickerThread = new Thread( tickerRunnable );
//        tickerThread.start()

    }

    void waitTicks(int ticks) {
        int currentTick = tick
        final long sleepTime = 1000l / gameInfo.ticksPerSecond
        while (tick < currentTick + ticks) {
            Thread.sleep(sleepTime)
        }
    }

    BombActionResult bomb(BombAction bombAction) {
        def playerState = getCurrentPlayer()
        def ticks = Constants.TICKS_BOMB
        if( playerState.disease == Disease.FAST_BOMB ) {
            ticks >> 1
        }
        if( playerState.disease == Disease.SLOW_BOMB ) {
            ticks << 1
        }

        def bomb = new BombState(
                blastSize: playerState.bombSize,
                xCoordinate: Math.round( playerState.x ),
                yCoordinate: Math.round( playerState.y ),
                ticksRemaining: ticks,
                moving: false,
                direction: Direction.E,
                owner: playerState.playerNumber
        )
        bombs.add(bomb)
    }

    int getPlayerIdx(clientId) {
        playerIds.indexOf(clientId)
    }

    PlayerState getCurrentPlayer() {
        return getPlayer( getPlayerIdx( getClientId() ) )
    }

    PlayerState getPlayer( def playerIdx ) {
        return players.get(playerIdx)
    }

}
