package org.riksa.aibombah.server

import java.util.concurrent.atomic.AtomicLong
import org.riksa.aibombah.api.GameInfo

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 27.6.2012
 * Time: 17:28
 * To change this template use File | Settings | File Templates.
 */
class Game {
    static AtomicLong idGenerator = new AtomicLong()
    GameInfo gameInfo

    public Game() {
        gameInfo = new GameInfo( id: idGenerator.incrementAndGet() )
    }
}
