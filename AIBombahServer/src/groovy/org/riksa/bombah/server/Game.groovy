package org.riksa.bombah.server

import org.slf4j.LoggerFactory

import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicLong

import org.riksa.bombah.thrift.*
import groovy.transform.Synchronized

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
    final List players = new LinkedList<PlayerState>()
    def currentTick
    Timer timer
    final List bombs = new LinkedList<BombState>()
    final List flames = new LinkedList<FlameState>()
    MapState mapState
    final def controllers = [:]
    def buffs
    Random random = new Random()
    final double INFECTION_RADIUS_SQUARED = Math.pow(0.5d,2)

    enum DestroyEnum {
        CONTINUE,
        DESTROYED,
        BLOCKED
    }

    boolean canMove(double x, double y, Direction direction, double step) {
//        def currentLocation = getTileCoordinate(x, y, null)
        def half = 0.5d + step

        switch (direction) {
            case Direction.N:
                def targetLocation = getTileCoordinate(x, y + half, null)
                return canMoveTo(targetLocation.x, targetLocation.y)
            case Direction.E:
                def targetLocation = getTileCoordinate(x + half, y, null)
                return canMoveTo(targetLocation.x, targetLocation.y)
            case Direction.W:
                def targetLocation = getTileCoordinate(x - half, y, null)
                return canMoveTo(targetLocation.x, targetLocation.y)
            case Direction.S:
                def targetLocation = getTileCoordinate(x, y - half, null)
                return canMoveTo(targetLocation.x, targetLocation.y)
            default:
                return false
        }
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

//    def sleepers = []
    enum GameState {
        CREATED, RUNNING, FINISHED
    }
    GameState gameState


    public Game() {
        id = idGenerator.incrementAndGet()
        log.debug("Creating a new game #$id")
        players.clear()
        controllers.clear()
        bombs.clear()
        flames.clear()
        players.clear()
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

        buffs = new Tile[gameInfo.mapWidth][gameInfo.mapHeight];
        randomizeBuffs(Tile.DEBUFF, 20)
        randomizeBuffs(Tile.BUFF_BOMB, 9)
        randomizeBuffs(Tile.BUFF_FLAME, 9)
        randomizeBuffs(Tile.BUFF_CHAIN, 1)
        randomizeBuffs(Tile.BUFF_FOOT, 3)

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

    def randomizeBuffs(Tile tile, int count) {
        count.times {
            def freeCoordinates = []

            gameInfo.tiles.eachWithIndex {
                t, index ->
                if (t == Tile.DESTRUCTIBLE) {
                    int x = index % gameInfo.mapWidth
                    int y = (index - x) / gameInfo.mapWidth
                    def buff = getTileBuff(x, y)
                    if (!buff)
                        freeCoordinates.add(new Coordinate(x: x, y: y))
                }
            }

            def pos = freeCoordinates.get(random.nextInt(freeCoordinates.size()))
            buffs[pos.x][pos.y] = tile

        }
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
//        def time = System.currentTimeMillis()
        synchronized (flames) {
            flames.removeAll {
                --it.ticksRemaining <= 0
            }
        }


        synchronized (bombs) {
            bombs.removeAll {
                if (--it.ticksRemaining <= 0) {
                    explodeBomb(it)
                    return true
                }
            }
        }
        players.grep {it.disease && it.disease != Disease.NONE }.each {
            PlayerState infected ->

            players.grep {it.disease == null || it.disease == Disease.NONE }.each {
                PlayerState target ->
                def dist = Math.pow(target.x-infected.x,2) + Math.pow(target.y-infected.y,2)

                if( dist < INFECTION_RADIUS_SQUARED )
                    infect( target, infected.disease, infected.diseaseTicks )
            }

            if (--infected.diseaseTicks <= 0) {
                infected.disease = Disease.NONE
            }


        }

        players.grep {it.alive}.each {
            PlayerState it ->

            double step = 1d / Constants.TICKS_PER_TILE
            if (it.disease == Disease.FAST)
                step *= 4
            else if (it.disease == Disease.SLOW)
                step /= 4

            def playerId = it.playerId
            def controller = controllers.get(playerId)
            def coordinate = getTileCoordinate(it.x, it.y, null)
            def buff = destroyBuff(coordinate.x, coordinate.y)
            switch (buff) {
                case Tile.BUFF_BOMB:
                    it.bombAmount++
                    log.debug("Got bomb")
                    break;
                case Tile.BUFF_CHAIN:
                    it.chain = true
                    log.debug("Got chain")
                    break;
                case Tile.BUFF_FLAME:
                    it.bombSize++
                    log.debug("Got flame")
                    break;
                case Tile.BUFF_FOOT:
                    it.foot = true
                    log.debug("Got foot")
                    break
                case Tile.DEBUFF:
                    infect(it, randomDisease(), Constants.TICKS_DISEASE)
                    log.debug("Disease obtained ${it.disease}")
                    break;
            }

            if (controller) {
                if (controller.key1Down) {
                    BombAction bombAction = new BombAction(chainBombs: false)
                    bomb(playerId, bombAction)
                }
                if (controller.directionPadDown) {
//                        log.debug( "$playerId moving to direction ${controller.direction}" )

                    def devX = coordinate.x - it.x
                    def devY = coordinate.y - it.y

                    // check to see if we are in the middle of a tile
                    if ((controller.direction == Direction.N || controller.direction == Direction.S) && Math.abs(devX) > step / 2d) {
                        // Center to tile X first
                        if (devX < 0) it.x -= step
                        else it.x += step
                    } else if ((controller.direction == Direction.E || controller.direction == Direction.W) && Math.abs(devY) > step / 2d) {
                        // Center to tile Y
                        if (devY < 0) it.y -= step
                        else it.y += step
                    } else {
                        if (canMove(it.x, it.y, controller.direction, step)) {
                            switch (controller.direction) {
                                case Direction.N:
                                    it.y += step
                                    break;
                                case Direction.E:
                                    it.x += step
                                    break;
                                case Direction.W:
                                    it.x -= step
                                    break;
                                case Direction.S:
                                    it.y -= step
                                    break;
                            }
                        }
                    }
                }
            }
        }

//        log.debug("Tick #$currentTick, time = $time")

//        synchronized (sleepers) {
//            sleepers.each {
//                it.interrupt()
//            }
//            sleepers.clear()
//        }

        if (currentTick >= gameInfo.ticksTotal)
            stopGame()

        mapState = new MapState(
                currentTick: currentTick
        )
        mapState.bombs = new ArrayList<BombState>(bombs)
        mapState.flames = new ArrayList(flames)
        mapState.tiles = new ArrayList<Tile>(gameInfo.tiles)
        mapState.players = new ArrayList<PlayerState>(players)
//        mapState.addToTiles(gameInfo.tiles)
//        mapState.addToPlayers(players)

//        log.debug( "Spent cloning: $spent")
//        mapState = new MapState(
//                currentTick: currentTick,
//                bombs: bombs,
//                players: players,
//                tiles: gameInfo.tiles)

        currentTick++
    }

    def infect(PlayerState playerState, Disease disease, int ticks) {
        playerState.disease = disease
        playerState.diseaseTicks = ticks
    }

    Disease randomDisease() {
        if (random.nextBoolean())
            return Disease.FAST
        return Disease.SLOW

//        return Disease.DIARRHEA
    }

    def handleTileFlame = {
        x, y ->
        switch (destroyTile(x, y)) {
            case DestroyEnum.BLOCKED:
                return false
            case DestroyEnum.DESTROYED:
                flames.add(new FlameState(coordinate: [x: x, y: y], ticksRemaining: Constants.TICKS_FLAME, burningBlock: true))
                return false
            case DestroyEnum.CONTINUE:
                flames.add(new FlameState(coordinate: [x: x, y: y], ticksRemaining: Constants.TICKS_FLAME))
                return true
        }
    }

    def explodeBomb(BombState bomb) {
        def bombCoordinates = getTileCoordinate(bomb.xCoordinate, bomb.yCoordinate, null)
        handleTileFlame(bombCoordinates.x, bombCoordinates.y)

        for (i in 1..bomb.blastSize) {
            if (!handleTileFlame(bombCoordinates.x + i, bombCoordinates.y))
                break;
        }
        for (i in 1..bomb.blastSize) {
            if (!handleTileFlame(bombCoordinates.x - i, bombCoordinates.y))
                break;
        }
        for (i in 1..bomb.blastSize) {
            if (!handleTileFlame(bombCoordinates.x, bombCoordinates.y + i))
                break;
        }
        for (i in 1..bomb.blastSize) {
            if (!handleTileFlame(bombCoordinates.x, bombCoordinates.y - i))
                break;
        }
    }

    DestroyEnum destroyTile(int x, int y) {
        if (x < 0 || y < 0 || x >= gameInfo.mapWidth || y >= gameInfo.mapHeight)
            return DestroyEnum.BLOCKED

        players.grep {
            def coordinates = getTileCoordinate(it.x, it.y, null)
            return coordinates.x == x && coordinates.y == y
        }.each {
            killPlayer(it)
        }

        synchronized (bombs) {
            bombs.each {
                def coordinates = getTileCoordinate(it.xCoordinate, it.yCoordinate, null)
                if (coordinates.x == x && coordinates.y == y) {
                    it.ticksRemaining = Constants.TICKS_BOMB_IN_FLAMES
                }
            }
        }

        def tile = getTile(x, y)
        switch (tile) {
            case Tile.BUFF_BOMB:
            case Tile.BUFF_CHAIN:
            case Tile.BUFF_FLAME:
            case Tile.BUFF_FOOT:
            case Tile.DEBUFF:
                destroyBuff(x, y)
                return DestroyEnum.DESTROYED;
            case Tile.DESTRUCTIBLE:
                destroyDestructible(x, y)
                return DestroyEnum.DESTROYED;
            case Tile.INDESTRUCTIBLE:
                return DestroyEnum.BLOCKED;
            case Tile.NONE:
                if (getTileFlame(x, y)?.burningBlock)
                    return DestroyEnum.BLOCKED;
                else
                    return DestroyEnum.CONTINUE;
            default:
                return DestroyEnum.BLOCKED;
        }

    }

    def killPlayer(PlayerState player) {
        return;
        log.debug("Player $player died")
        player.alive = false
        if (players.grep() {
            it.alive
        }.size() <= 1) stopGame()
    }

    @Synchronized
    def destroyDestructible(int x, int y) {
        def tiles = gameInfo.tiles
        synchronized (tiles) {
            log.debug("Destroy destructible @ $x,$y")
            int idx = x + y * gameInfo.mapWidth
            if (idx >= 0 && idx < tiles.size()) {
                if (tiles.get(idx) == Tile.DESTRUCTIBLE) {
                    def buff = getTileBuff(x, y)
                    tiles.putAt(idx, buff ?: Tile.NONE)
                }
                else {
                    log.warn("Tried to destruct tile that was not destructible")
                }
            }
            log.debug("Destroy destructible done @ $x,$y")
        }

    }

    def destroyBuff(int x, int y) {
        if (x >= 0 && y >= 0 && x < gameInfo.mapWidth && y < gameInfo.mapHeight) {
            def buff = getTileBuff(x, y)
            if (buff) {
                gameInfo.tiles.putAt(x + y * gameInfo.mapWidth, Tile.NONE)
                buffs[x][y] = null
                return buff
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

        if (bombs.find {
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
        flames.find {
            it.coordinate.x == x && it.coordinate.y == y
        }
    }

    Tile getTileBuff(int x, int y) {
        if (x >= 0 && y >= 0 && x < gameInfo.mapWidth && y < gameInfo.mapHeight)
            return buffs[x][y]
    }

    void startGame() {
        log.debug("Starting game #${gameInfo.gameId}")

        final long sleepTime = 1000l / gameInfo.ticksPerSecond

        if (timer)
            timer.cancel()

        gameState = GameState.RUNNING

        def timerTask = new TimerTask() {
            double avgSum = 0d
            int avgSamples = 0
            final double timeAllowed = 1000l * 1000l * 1000l / Constants.TICKS_PER_SECOND // max ns we have for one tick (without overhead)

            @Override
            void run() {

                try {
                    def timeSpent = time({tick()})
                    double capacity = ((double) timeSpent) / (double) timeAllowed
                    avgSum += capacity
                    def avg = 100d * avgSum / (double) avgSamples
                    if (++avgSamples % 100 == 0)
                        log.debug("CPU capacity $timeSpent/$timeAllowed (@ ${100d * capacity}%) (@ avg: ${avg}%)")
                } catch (GameOverException e) {
                    log.debug("Game over")
                    stopGame();
                } catch (Exception e) {
                    log.error(e.getMessage(), e)
                }
            }
        }
        timer = new Timer()
        timer.scheduleAtFixedRate(timerTask, 0, sleepTime)

    }

    def time(Closure closure) {
        def now = System.nanoTime()
        closure.call()
        def spent = System.nanoTime() - now
//        log.debug( "Execution took ${spent}ns (${spent/1000000}ms)")
        return spent
    }

    void stopGame() {
        log.debug("Game over");

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
    }

    BombActionResult bomb(playerId, BombAction bombAction) {
        if (gameState == GameState.FINISHED)
            throw new GameOverException()

        def playerState = getPlayer(playerId)
        if (!playerState.alive)
            throw new YouAreDeadException()

        synchronized (bombs) {
            def liveBombs = bombs.count { it.owner == playerId }
            if (liveBombs >= playerState.bombAmount) {
                log.debug("Bombing failed, player already has $liveBombs live  bombs")
                // TODO: information that the bombing failed
                return new BombActionResult(myState: playerState, mapState: mapState)
            }

            def ticks = Constants.TICKS_BOMB
            if (playerState.disease == Disease.FAST_BOMB) {
                ticks >> 1
            }
            if (playerState.disease == Disease.SLOW_BOMB) {
                ticks << 1
            }

            // TODO: Chainbomb if tile already contains a bomb
            // TODO: Deprecate bombAction.chainBombs

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
    }

    PlayerState getPlayer(def playerId) {
        players.find {
            it.playerId == playerId
        }
    }

    MoveActionResult controllerEvent(playerId, ControllerState controllerState) {
        if (gameState == GameState.FINISHED)
            throw new GameOverException()

        def playerState = getPlayer(playerId)
        if (!playerState.alive)
            throw new YouAreDeadException()

        controllers.put(playerId, controllerState);
        return new MoveActionResult(myState: getPlayer(playerId), mapState: mapState)
    }

}
