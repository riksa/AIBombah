package org.riksa.bombah.server

import org.slf4j.LoggerFactory

import java.util.concurrent.atomic.AtomicLong

import org.riksa.bombah.thrift.*
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
    static AtomicInteger idGenerator = new AtomicInteger()
    def id
    def slots
    AtomicLong waiting
    GameInfo gameInfo
    def players = []
    def currentTick
    Timer timer
    def bombs = new Vector<BombState>()
    MapState mapState
    final def controllers = [:]

//    def sleepers = []
    enum GameState {
        CREATED, RUNNING, FINISHED
    }
    GameState gameState


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
        mapState = new MapState(currentTick: 0)
        gameState = GameState.CREATED
    }

    synchronized int join() {
        if (gameState == GameState.FINISHED) {
            throw new GameOverException()
        }

        if (players.size() >= slots) {
            log.debug("Game is full")
            return -1
        }
        def playerId = idGenerator.incrementAndGet()
        def startingPosition = gameInfo.startingPositions.get(players.size())
        log.debug("Added $playerId as $startingPosition")

        def player = new PlayerState(
                bombSize: 2,
                bombAmount: 1,
                foot: false,
                chain: false,
                disease: Disease.NONE,
                alive: true,
                x: startingPosition.x,
                y: startingPosition.y,
                playerId: playerId)

//        players.put( playerId, player)
        players.add(player)
        return playerId
    }

    GameInfo loadMap(def width, def height, String asciiArt) {
        def rate = Constants.TICKS_PER_SECOND
        GameInfo gameInfo = new GameInfo(mapWidth: width, mapHeight: height, ticksTotal: 30 * 60 * Constants.TICKS_PER_SECOND, ticksPerSecond: rate)

        if (asciiArt.length() != width * height) {
            log.error("Map is not $width X $height")
            return
        }

        asciiArt.each {
            if (it == 'x')
                gameInfo.addToTiles(Tile.DESTRUCTIBLE)
            else if (it == 'O')
                gameInfo.addToTiles(Tile.INDESTRUCTIBLE)
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

    void waitForStart() {
        if (waiting.incrementAndGet() == slots) {
            startGame()
        }
        log.debug("Players waiting $waiting/$slots")
        waitTick()
    }

    synchronized void tick() throws GameOverException {
        def time = System.currentTimeMillis()
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
                currentTick: currentTick,
                bombs: bombs.clone(), // wasted, TODO
                players: players.clone(),
                tiles: gameInfo.tiles.clone())
        // inefficient

//        mapState.ticksRemaining = gameInfo.ticksTotal - currentTick
//        mapState.bombs = this.bombs
//        mapState.players = this.players
//        mapState.tiles = gameInfo.tiles
        // TODO: tiles
        // TODO: move players
//        struct ControllerState {
//            1: bool directionPadDown, // Direction pad pressed to direction
//            2: Direction direction,
//            3: bool key1Down, // Drop bomb pressed
//            4: bool key2Down  // Stop bomb pressed
//        }

        synchronized ( controllers ) {
            players.each {
                def playerId = it.playerId
                def controller = controllers.get(playerId)
                if( controller ) {
                    if( controller.key1Down ) {
                        log.debug( "$playerId dropped bomb" )
                        // TODO
                    }
                    if( controller.directionPadDown ) {
                        log.debug( "$playerId moving to direction ${controller.direction}" )
                        double step = 1d/Constants.TICKS_PER_TILE

                        switch( controller.direction ) {
                            case Direction.N:
                                it.x += step
                                break;
                            case Direction.E:
                                it.y += step
                                break;
                            case Direction.W:
                                it.y -= step
                                break;
                            case Direction.S:
                                it.x -= step
                                break;
                        }
                        // TODO
                    }
                }

            }
        }

//        log.debug("Tick #$currentTick, time = $time")

        if (currentTick >= gameInfo.ticksTotal)
        // TODO
            throw new GameOverException()

//        synchronized (sleepers) {
//            sleepers.each {
//                it.interrupt()
//            }
//            sleepers.clear()
//        }

        currentTick++
    }

    void startGame() {
        log.debug("Starting game #${gameInfo.gameId}")

        final long sleepTime = 1000l / gameInfo.ticksPerSecond

        if (timer)
            timer.cancel()

        gameState = GameState.RUNNING

        def timerTask = new TimerTask() {
            def previousExecution = System.currentTimeMillis()

            @Override
            void run() {
                def timeAllowed = 1000l*1000l*1000l/Constants.TICKS_PER_SECOND // max ns we have for one tick (without overhead)
                try {
                    def timeSpent = time({tick()})
                    double capacity = (100d * (double)timeSpent) / (double)timeAllowed
                    log.debug( "CPU capacity $timeSpent/$timeAllowed (@ ${capacity}%)")
                } catch (GameOverException e) {
                    log.debug("Game over")
                    stopGame();
                }
            }
        }
        timer = new Timer()
        timer.scheduleAtFixedRate(timerTask, 0, sleepTime)

    }

    def time(Closure closure) {
        def now = System.nanoTime()
        closure.call()
        def spent = System.nanoTime()-now
//        log.debug( "Execution took ${spent}ns (${spent/1000000}ms)")
        return spent
    }

    void stopGame() {
        if (timer) {
            timer.cancel()
        }
        timer = null

        gameState = GameState.FINISHED
    }

//    void waitTicks(int ticks) {
//        ticks.times {
//            waitTick()
//        }
//    }

    GameState waitForTick(int i) {
        while (currentTick < i) {
            if (gameState == GameState.FINISHED) {
                throw new GameOverException()
            }
            Thread.yield()
        }
        return gameState
    }

    void waitTick() {
        final now = currentTick;
        while (currentTick == now) {
            if (gameState == GameState.FINISHED) {
                throw new GameOverException()
            }
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

    BombActionResult bomb(playerId, BombAction bombAction) {
        if (gameState == GameState.FINISHED) {
            throw new GameOverException()
        }

        def playerState = getPlayer(playerId)
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
                owner: playerState.playerId
        )
        bombs.add(bomb)
        return new BombActionResult(myState: playerState, mapState: mapState)
    }

    PlayerState getPlayer(def playerId) {
        players.find {
            it.playerId == playerId
        }
    }

    MoveActionResult controllerEvent(playerId, ControllerState controllerState) {
            controllers.put( playerId, controllerState );
        return new MoveActionResult(myState: getPlayer(playerId), mapState: mapState)
    }
}
