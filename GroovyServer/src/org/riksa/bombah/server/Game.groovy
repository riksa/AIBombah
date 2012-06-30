package org.riksa.bombah.server

import java.util.concurrent.atomic.AtomicLong
import org.slf4j.LoggerFactory
import org.riksa.bombah.thrift.MapState
import org.riksa.bombah.thrift.GameInfo
import org.riksa.bombah.thrift.Tile
import org.riksa.bombah.thrift.Coordinate
import java.util.concurrent.atomic.AtomicInteger

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
    def List clientIds = new ArrayList()
    def tick
    def tickerThread

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
        clientIds.clear()
        waiting = new AtomicLong(0)
        tick = 0
        slots = 4
    }

    def getClientId = {
        Thread.currentThread().id
    }

    synchronized boolean join() {
        if (clientIds.size() >= slots ) {
            log.debug("Game is full")
            return false
        }
        def clientId = getClientId();
        clientIds.add(clientId)
        log.debug("Client joined $clientId")
        return true
    }

    MapState getMapState() {
        return new MapState()
    }

    GameInfo loadMap(def width, def height, String asciiArt) {
        GameInfo gameInfo = new GameInfo(mapWidth: width, mapHeight: height, ticksTotal: 3 * 60 * 30, ticksPerSecond: 30)

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

    Coordinate findStartingPosition( String player, width, String asciiArt) {
        def index = asciiArt.indexOf( player )
        if( index != -1 ) {
            def x = index % width
            def y = (index - x)/width
            new Coordinate(x: x, y: y)
        }
    }

    byte waitForStart() {
        if( waiting.incrementAndGet() == slots ) {
            startGame()
        } else {
            // WAIT
        }

        return clientIds.indexOf( getClientId() )

    }

    synchronized void tick() {
        def time = System.currentTimeMillis()
        tick++
        log.debug( "Tick #$tick, time = $time")
    }

    def gameRunnable = new Runnable() {
        @Override
        void run() {
            def running = true
            while( running ) {
                try {
                    Thread.sleep( 1000l )
                    log.error( "Noone woke me up and I died :(")
                    running = false
                } catch( InterruptedException ie ) {
                    tick()
                }
            }
        }
    }

    void startGame() {
        def final gameThread = new Thread( gameRunnable )
        gameThread.start()

        final long sleepTime = 1000l/gameInfo.ticksPerSecond
        def tickerRunnable = new Runnable() {
            @Override
            void run() {
                def running = true
                while( running ) {
                    try {
                        Thread.sleep( sleepTime );
                        gameThread.interrupt()
                    } catch( InterruptedException ie ) {
                        running = false
                    }
                }
            }
        }

        tickerThread = new Thread( tickerRunnable );
        tickerThread.start()

    }
}
