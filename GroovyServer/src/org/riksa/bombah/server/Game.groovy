package org.riksa.bombah.server

import java.util.concurrent.atomic.AtomicLong
import org.slf4j.LoggerFactory
import org.riksa.bombah.thrift.MapState

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 30.6.2012
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
class Game {
    def log = LoggerFactory.getLogger( getClass() )
    static AtomicLong idGenerator = new AtomicLong()
    def id
    def slots = 4

    public Game() {

        id = idGenerator.incrementAndGet()
        log.debug("Creating a new game #$id")

    }

    synchronized boolean join(long clientId) {
        if( slots < 1 ) {
            log.debug( "Game is full $clientId" )
            return false
        }
        log.debug( "Client joined $clientId" )
        slots--
        return true
    }

    MapState getMapState() {
        return new MapState()
    }
}
