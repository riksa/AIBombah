package org.riksa.bombah.server

import org.apache.thrift.transport.TSocket
import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.protocol.TBinaryProtocol
import org.riksa.bombah.thrift.BombahService
import org.slf4j.LoggerFactory
import org.riksa.bombah.thrift.Tile

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 30.6.2012
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
class SimpleObserver {
//    def log = LoggerFactory.getLogger( SimpleObserver.class )
    def timer = new Timer()
    long SLEEP_TIME = 1000/1
    def transport
    def mapWidth = 13
    def artMap = [:] //[ Tile.BUFF_BOMB: "o", Tile.BUFF_CHAIN: "8", Tile.BUFF_FLAME: "*"]

    SimpleObserver(String host, int port) {
        transport = new TSocket(host, port);

        artMap.put( Tile.NONE, " " )
        artMap.put( Tile.BUFF_BOMB, "o" )
        artMap.put( Tile.BUFF_FLAME, "^" )
        artMap.put( Tile.BUFF_CHAIN, "8" )
        artMap.put( Tile.BUFF_FOOT, "l" )
        artMap.put( Tile.DEBUFF, "?" )
        artMap.put( Tile.FIRE, "*" )
        artMap.put( Tile.I_GREY, "X" )
        artMap.put( Tile.D_GREY, "x" )
    }

    void start() {
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        final BombahService.Client client = new BombahService.Client(protocol);
        println("Observing game")

        def timerTask = new TimerTask() {
            @Override
            void run() {
                try {
                    def mapState = client.getMapState()
                    if( mapState ) {
                        println( "Ticks remaining: ${mapState.ticksRemaining}")
                        mapState.players.eachWithIndex {
                            it, idx ->
                            println( " Player #$idx : $it")
                        }
                        StringBuilder stringBuilder = new StringBuilder()
                        mapState.tiles.eachWithIndex {
                            it, idx ->
                            if( idx % mapWidth == 0 )
                                stringBuilder.append("\n")

                            stringBuilder.append( artMap.get( it ) )
                        }
                        println( stringBuilder.toString() )
                    }
                } catch (Exception e) {
                    println "Game over"
                    e.printStackTrace()
                    timer.cancel()
                }
            }
        }

        timer.scheduleAtFixedRate(timerTask, 0, SLEEP_TIME )
    }

    void stop() {
        timer.cancel()
        transport.close()

    }
}
