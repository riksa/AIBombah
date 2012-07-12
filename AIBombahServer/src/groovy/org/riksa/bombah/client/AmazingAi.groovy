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
        this.mapState = mapState
        myState = mapState.players.find { it.playerId == gameInfo.playerId }
        log.debug("My state $myState")
    }

    Action pickAction() {
        def flameSpeculation
        List<Direction> path

        if (canBomb()) {
            flameSpeculation = buildFlameSpeculation(true)
            log.debug("FlameSpec $flameSpeculation")
            path = findSafePath(flameSpeculation)
            if (path) {
                log.debug("Found path with bomb here $path")
                return new Action(what: Action.ActionTypeEnum.BOMB)
            } else {
                log.debug("Bombing would be suicidal")
            }
        }

        flameSpeculation = buildFlameSpeculation(false)
        path = findSafePath(flameSpeculation)
        if (path) {
            log.debug("Found path without bomb here $path")
            Coordinate target = path.get(0)
            def myTile = getTileCoordinate( myState.x, myState.y, null )
            def dir
            if( target.x < myTile.x )
                dir = Direction.E
            if( target.x > myTile.x )
                dir = Direction.W
            if( target.y < myTile.y )
                dir = Direction.S
            if( target.y > myTile.y )
                dir = Direction.N

            log.debug( "Moving $myTile -> $dir = $target ")
            return new Action(what: Action.ActionTypeEnum.MOVE, direction: dir)
        }

        def direction = [Direction.N, Direction.E, Direction.W, Direction.S].get(random.nextInt(4))
        return new Action(what: Action.ActionTypeEnum.MOVE, direction: direction)
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


    List<Coordinate> findSafePath(def flameSpeculations) {
        //http://en.wikipedia.org/wiki/Breadth-first_search
        // //        1 procedure BFS(G,v):
//        2      create a queue Q
//        3      enqueue v onto Q
//        4      mark v
//        5      while Q is not empty:
//        6          t ← Q.dequeue()
//        7          if t is what we are looking for:
//        8              return t
//        9          for all edges e in G.incidentEdges(t) do
//        10             o ← G.opposite(t,e)
//        11             if o is not marked:
//        12                  mark o
//        13                  enqueue o onto Q
        def visited = new HashSet<Coordinate>()
        def q = new LinkedList<Coordinate>()
        def v = getTileCoordinate(myState.x, myState.y, null)
        q.add(v)
        visited.add(v)
        while (!q.isEmpty()) {
            def t = q.removeFirst()

            def reachTile = 0
            def exitTile = reachTile + Constants.TICKS_PER_TILE + Constants.TICKS_BOMB // kludge, is it safe enough to stay there

            if (canMoveSafe(t, reachTile, exitTile, flameSpeculations)) {
                return [t]
            }

            [Direction.N, Direction.E, Direction.W, Direction.S].each {
                def coordinate = getTileCoordinate(t.x, t.y, it)
                if( !visited.contains(coordinate) ) {
                    if (canMoveTo(coordinate.x, coordinate.y)) {
                        visited.add(coordinate)
                        q.add(coordinate)
                    }
                }

            }
        }

        log.debug("Path not found, random dir")
        def safe = [Direction.N, Direction.E, Direction.W, Direction.S]
        def idx = random.nextInt(safe.size())
        def dir = safe[idx]
        return [getTileCoordinate(myState.x, myState.y, dir)]
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

    FlameState getTileFlame(def flames, int x, int y) {
        flames.find {
            it.coordinate.x == x && it.coordinate.y == y
        }
    }

    class FlameSpeculation {
        int start
        int stop

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
