package org.riksa.bombah.client

import org.riksa.bombah.thrift.GameInfo
import org.riksa.bombah.thrift.MapState
import org.riksa.bombah.thrift.PlayerState
import org.slf4j.LoggerFactory
import org.riksa.bombah.thrift.Direction
import org.riksa.bombah.thrift.Coordinate
import org.riksa.bombah.thrift.Constants
import org.riksa.bombah.thrift.Tile
import org.riksa.bombah.thrift.BombState
import org.riksa.bombah.thrift.FlameState
import org.riksa.bombah.server.Game
import org.riksa.bombah.thrift.Disease

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 12.7.2012
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
class AmazingAi {
    def log = LoggerFactory.getLogger(getClass())
    GameInfo gameInfo
    MapState mapState
    PlayerState myState
    def spec
    def random = new Random()


    AmazingAi(GameInfo gameInfo) {
        this.gameInfo = gameInfo
    }

    def setMapState(MapState mapState) {
        def newState = mapState.players.find { it.playerId == gameInfo.playerId }
        if (newState) {
            this.mapState = mapState
            myState = newState
        }
        else
            log.warn "Why is $newState null"

    }



    Action pickAction() {
        def flameSpeculations = buildFlameSpeculation(false)
        List<Direction> path

        Coordinate myCoordinate = getTileCoordinate(myState.x, myState.y, null)

        // Path to buffs
        path = pathToSomethingAwesome(myCoordinate, flameSpeculations, {
            Coordinate t ->
            Tile buff = getTileBuff(t.x, t.y)
            if (buff && buff != Tile.DEBUFF) {
                return true
            }
        })
        if (path && path.size() > 1) {
            return createMoveAction(path.get(1))
        }

        // Path to something worth bombing
        path = pathToSomethingAwesome(myCoordinate, flameSpeculations, {
            Coordinate t ->
            if (mapState.players.find {
                PlayerState playerState ->
                if (playerState.playerId != myState.playerId) {
                    def c = getTileCoordinate(playerState.x, playerState.y, null)
                    if (c.x + 1 == t.x && c.y == t.y) {
                        log.debug("Target player $playerState with bomb @ $t")
                        return true
                    }
                    if (c.x - 1 == t.x && c.y == t.y) {
                        log.debug("Target player $playerState with bomb @ $t")
                        return true
                    }
                    if (c.x == t.x && c.y + 1 == t.y) {
                        log.debug("Target player $playerState with bomb @ $t")
                        return true
                    }
                    if (c.x == t.x && c.y - 1 == t.y) {
                        log.debug("Target player $playerState with bomb @ $t")
                        return true
                    }
                }
            }) return true

            if (getTile(t.x + 1, t.y) == Tile.DESTRUCTIBLE) {
                return true
            }
            if (getTile(t.x, t.y + 1) == Tile.DESTRUCTIBLE) {
                return true
            }
            if (getTile(t.x, t.y - 1) == Tile.DESTRUCTIBLE) {
                return true
            }
            if (getTile(t.x - 1, t.y) == Tile.DESTRUCTIBLE) {
                return true
            }
            return false
        })
        log.debug("Path $path")
        if (path) {
            log.debug("Path $path")
            if (path.size() == 1) {
                def flameSpeculationsWithBomb = buildFlameSpeculation(true)
                if (pathToSafety(myCoordinate, flameSpeculationsWithBomb))
                    return new Action(what: Action.ActionTypeEnum.BOMB)
                else {
                    log.debug("Bombing would be stupid")
                }
            }
            else
                return createMoveAction(path.get(1))
        }

//        log.debug( "Cannot find safe buffs")
        path = pathToSafety(myCoordinate, flameSpeculations)
        if (path && path.size() > 1) {
            log.debug("Found a safe place $path")
            return createMoveAction(path.get(1))
        }
//        log.debug( "Cannot find safe place")

//        if (canBomb()) {
//            def bombFlameSpeculation = buildFlameSpeculation(true)
//            path = findSafePath(flameSpeculation)
//            if (path) {
//                log.debug("Found path with bomb here $path")
//                return new Action(what: Action.ActionTypeEnum.BOMB)
//            } else {
//                log.debug("Bombing would be suicidal")
//            }
//        }
//
//        flameSpeculation = buildFlameSpeculation(false)
//        path = findSafePath(flameSpeculation)
//        if (path) {
//            log.debug("Found path without bomb here $path")
//            Coordinate target = path.get(0)
//            def myTile = getTileCoordinate(myState.x, myState.y, null)
//            def dir
//            if (target.x < myTile.x)
//                dir = Direction.E
//            if (target.x > myTile.x)
//                dir = Direction.W
//            if (target.y < myTile.y)
//                dir = Direction.S
//            if (target.y > myTile.y)
//                dir = Direction.N
//
//            log.debug("Moving $myTile -> $dir = $target ")
//            return new Action(what: Action.ActionTypeEnum.MOVE, direction: dir)
//        }

//        return new Action(what: Action.ActionTypeEnum.WAIT)

        def direction = [Direction.N, Direction.E, Direction.W, Direction.S].get(random.nextInt(4))
        return new Action(what: Action.ActionTypeEnum.MOVE, direction: direction)
    }

    List<Coordinate> pathToSomethingAwesome(Coordinate from, def flameSpeculations, Closure somethingAwesome) {
        def visited = new HashSet<Coordinate>()
        def q = new LinkedList<Coordinate>()
        q.add(from)
        visited.add(from)
        def parent = [:]

        while (!q.isEmpty()) {
            def t = q.removeFirst()

            def reachTile = 0
            def exitTile = reachTile + Constants.TICKS_PER_TILE + Constants.TICKS_BOMB // kludge, is it safe enough to stay there

            if (somethingAwesome(t) && canMoveSafe(t, reachTile, exitTile, flameSpeculations)) {
                def path = [t]
                def node = t
                while (parent[node]) {
                    node = parent[node]
                    path.add(0, node)
                }

                return path
            }

            [Direction.N, Direction.E, Direction.W, Direction.S].each {
                def coordinate = getTileCoordinate(t.x, t.y, it)
                if (!visited.contains(coordinate)) {
                    parent[coordinate] = t
                    if (canMoveTo(coordinate.x, coordinate.y)) {
                        visited.add(coordinate)
                        q.add(coordinate)
                    }
                }

            }
        }
    }

    Action createMoveAction(Coordinate target) {
        def myTile = getTileCoordinate(myState.x, myState.y, null)
        def dir
        if (target.x < myTile.x)
            dir = Direction.W
        if (target.x > myTile.x)
            dir = Direction.E
        if (target.y < myTile.y)
            dir = Direction.S
        if (target.y > myTile.y)
            dir = Direction.N

        log.debug("Moving $myTile -> $dir = $target ")
        return new Action(what: Action.ActionTypeEnum.MOVE, direction: dir)
    }

    def drawFlameSpeculation(FlameSpeculation[][] flameSpeculation) {
        for (y in 0..gameInfo.mapHeight - 1) {
            def sb = new StringBuffer()
            for (x in 0..gameInfo.mapWidth - 1) {
                def spec = flameSpeculation[x][y]
                sb.append(String.format("[%3d-%3d %s:%3d] ", spec.start < 999 ? spec.start : 0, spec.stop < 999 ? spec.stop : 0, spec.bombState ? "*" : " ", spec.bombState?.ticksRemaining ?: 0))
            }
            log.debug(sb.toString())
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


//    List<Coordinate> pathToSafety(myCoordinate, def flameSpeculations) {
//        def visited = new HashSet<Coordinate>()
//        def q = new LinkedList<Coordinate>()
//        q.add(myCoordinate)
//        visited.add(myCoordinate)
//        def parent = [:]
//
//        while (!q.isEmpty()) {
//            def t = q.removeFirst()
//
//            def reachTile = 0
//            def exitTile = reachTile + Constants.TICKS_PER_TILE + Constants.TICKS_BOMB // kludge, is it safe enough to stay there
//
//            if (canMoveSafe(t, reachTile, exitTile, flameSpeculations)) {
//                def path = [t]
//                def node = t
//                while (parent[node]) {
//                    node = parent[node]
//                    path.add(0, node)
//                }
//
//                return path
//            }
//        }
//    }

    List<Coordinate> pathToSafety(myCoordinate, def flameSpeculations) {
        def somethingAwesome = {
            Coordinate t ->
            def spec = flameSpeculations[t.x][t.y]
            if (spec.stop < 1 || spec.start > Constants.TICKS_BOMB + Constants.TICKS_FLAME) {
                return true
            }
        }
        return pathToSomethingAwesome(myCoordinate, flameSpeculations, somethingAwesome)
    }

    def canMoveSafe(Coordinate coordinate, int enter, int exit, FlameSpeculation[][] flameSpeculations) {
        if (canMoveTo(coordinate.x, coordinate.y)) {
            // TODO: how long would it take to be there and beyond, for now assume next tile
            def spec = flameSpeculations[coordinate.x][coordinate.y]
            if (spec.start > exit) {
                // we got time to move to and from the tile without dying
                return true
            }
            if (spec.stop < enter) {
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
        if (tile == Tile.DESTRUCTIBLE || tile == Tile.INDESTRUCTIBLE) {
            return false
        }

        if (mapState.bombs.find {
            BombState bomb ->
            def coordinate = getTileCoordinate(bomb.xCoordinate, bomb.yCoordinate, null)
            return (coordinate.x == x && coordinate.y == y)
        }) return false

        return true
    }

    Tile getTile(int x, int y) {
        // TODO: Why is tiles null occasionally
        int idx = x + y * gameInfo.mapWidth
        if (idx >= 0 && idx < mapState?.tiles?.size())
            return mapState.tiles.get(idx)
    }

    Tile getTileBuff(int x, int y) {
        Tile tile = getTile(x, y)
        switch (tile) {
            case Tile.BUFF_BOMB:
            case Tile.BUFF_CHAIN:
            case Tile.BUFF_FLAME:
            case Tile.BUFF_FOOT:
            case Tile.DEBUFF:
                return tile
        }
    }

    FlameState getTileFlame(int x, int y) {
        mapState.flames.find {
            it.coordinate.x == x && it.coordinate.y == y
        }
    }

    class FlameSpeculation {
        int start
        int stop
        BombState bombState

        @Override
        public String toString() {
            return "FlameSpeculation{" +
                    "start=" + start +
                    ", stop=" + stop +
                    '}';
        }
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

//        log.debug("Flames count=${mapState.flames.size()}, ${mapState.flames}")

        def bombs = new LinkedList<BombState>(mapState.bombs)
        if (dropBomb) {
            def ticks = Constants.TICKS_BOMB
            if (myState.disease == Disease.FAST_BOMB) {
                ticks >> 1
            }
            if (myState.disease == Disease.SLOW_BOMB) {
                ticks << 1
            }
            bombs.add(new BombState(
                    blastSize: myState.bombSize,
                    xCoordinate: Math.round(myState.x),
                    yCoordinate: Math.round(myState.y),
                    ticksRemaining: ticks,
                    moving: false,
                    direction: Direction.E,
                    owner: myState.playerId
            ))
        }

        def destroyTile = {
            x, y, tickOffset ->
            if (x < 0 || y < 0 || x >= gameInfo.mapWidth || y >= gameInfo.mapHeight)
                return Game.DestroyEnum.BLOCKED

            synchronized (bombs) {
                bombs.each {
                    def coordinates = getTileCoordinate(it.xCoordinate, it.yCoordinate, null)
                    if (coordinates.x == x && coordinates.y == y) {
                        it.ticksRemaining = tickOffset + Constants.TICKS_BOMB_IN_FLAMES
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
                    return Game.DestroyEnum.DESTROYED;
                case Tile.DESTRUCTIBLE:
                    return Game.DestroyEnum.DESTROYED;
                case Tile.INDESTRUCTIBLE:
                    return Game.DestroyEnum.BLOCKED;
                case Tile.NONE:
                    return Game.DestroyEnum.CONTINUE;
                default:
                    return Game.DestroyEnum.BLOCKED;
            }

        }


        def handleTileFlame = {
            x, y, tickOffset ->

            def buffer = Constants.TICKS_PER_SECOND
            switch (destroyTile(x, y, tickOffset)) {
                case Game.DestroyEnum.BLOCKED:
                    return false
                case Game.DestroyEnum.DESTROYED:
                    spec[x][y].start = Math.min(tickOffset - buffer, spec[x][y].start)
                    spec[x][y].stop = Math.max(tickOffset + buffer + Constants.TICKS_FLAME, spec[x][y].start)
                    return false
                case Game.DestroyEnum.CONTINUE:
                    spec[x][y].start = Math.min(tickOffset, spec[x][y].start)
                    spec[x][y].stop = Math.max(tickOffset + Constants.TICKS_FLAME, spec[x][y].start)
                    return true
            }
        }

        def explodeBomb = {
            BombState bomb, int tickOffset ->
            def coordinate = getTileCoordinate(bomb.xCoordinate, bomb.yCoordinate, null)
            spec[coordinate.x][coordinate.y].bombState = bomb

            handleTileFlame(coordinate.x, coordinate.y, tickOffset)

            for (i in 1..bomb.blastSize) {
                if (!handleTileFlame(coordinate.x + i, coordinate.y, tickOffset))
                    break;
            }
            for (i in 1..bomb.blastSize) {
                if (!handleTileFlame(coordinate.x - i, coordinate.y, tickOffset))
                    break;
            }
            for (i in 1..bomb.blastSize) {
                if (!handleTileFlame(coordinate.x, coordinate.y + i, tickOffset))
                    break;
            }
            for (i in 1..bomb.blastSize) {
                if (!handleTileFlame(coordinate.x, coordinate.y - i, tickOffset))
                    break;
            }


        }

        while (!bombs.isEmpty()) {
            bombs.sort {
                it.ticksRemaining
            }

//            log.debug( "Bombs: $bombs")

            def bomb = bombs.removeFirst()
            explodeBomb(bomb, bomb.ticksRemaining)
        }

//        drawFlameSpeculation(spec)
        return spec
    }

    boolean canBomb() {
        if (mapState.bombs.grep() {
            it.owner == gameInfo.playerId
        }.size() >= myState?.bombAmount) return false

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
