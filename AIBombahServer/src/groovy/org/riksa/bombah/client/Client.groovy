package org.riksa.bombah.client

import org.apache.thrift.protocol.TJSONProtocol
import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.transport.THttpClient
import org.riksa.bombah.thrift.BombAction
import org.riksa.bombah.thrift.BombahService
import org.riksa.bombah.thrift.Direction
import org.riksa.bombah.thrift.MoveAction
import org.slf4j.LoggerFactory

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 28.6.2012
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */

//def HOST = "localhost"
//def PORT = 12345
def url = "http://localhost:8080/AIBombahServer/bombah/json"
def log = LoggerFactory.getLogger(getClass())
//FileInputStream fis =  new FileInputStream("log4j.properties");
//LogManager.getLogManager().readConfiguration(fis);

def clientRunnable = new Runnable() {
    @Override
    void run() {
//        def transport = new TSocket(HOST, PORT);
        def transport = new THttpClient(url)
        transport.open();

        TProtocol protocol = new TJSONProtocol(transport); //TBinaryProtocol(transport);
        def id = Thread.currentThread().id
        BombahService.Client client = new BombahService.Client(protocol);
        log.debug("#$id Joining game")
        def gameInfo = client.joinGame( -1 )
        if (gameInfo) {
            def playerId = gameInfo.playerId
            log.debug("#$id Joined game $gameInfo")
            client.waitForStart()
            log.debug("Game started, I am player #" + playerId )

            client.move(playerId, new MoveAction(direction: Direction.N))    // 10
            5.times {
                client.move(playerId, new MoveAction(direction: Direction.N))
                client.bomb(playerId, new BombAction(chainBombs: false))          // 150
                client.move(playerId, new MoveAction(direction: Direction.S))
                client.move(playerId, new MoveAction(direction: Direction.S))
                client.move(playerId, new MoveAction(direction: Direction.E))
                client.move(playerId, new MoveAction(direction: Direction.E))
                client.waitTicks(100)
                client.bomb(playerId, new BombAction(chainBombs: false))          // 150
                client.move(playerId, new MoveAction(direction: Direction.W))
                client.move(playerId, new MoveAction(direction: Direction.W))
                client.move(playerId, new MoveAction(direction: Direction.N))    // 10
                client.waitTicks(100)
            }

            Thread.sleep(5000);
            log.debug("#$id Done...")
        }

        transport.close()
    }
}

4.times {
    new Thread(clientRunnable).start()
}

//new SimpleObserver( HOST, PORT ).start();
