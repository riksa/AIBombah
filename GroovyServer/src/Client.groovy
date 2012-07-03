import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.transport.TSocket
import org.riksa.bombah.thrift.BombahService
import org.slf4j.LoggerFactory
import java.util.logging.LogManager
import org.riksa.bombah.thrift.GameOverException
import org.riksa.bombah.server.SimpleObserver
import org.riksa.bombah.thrift.MoveAction
import org.riksa.bombah.thrift.Direction
import org.riksa.bombah.thrift.BombAction

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 28.6.2012
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */

def HOST = "localhost"
def PORT = 12345
def log = LoggerFactory.getLogger( getClass() )
FileInputStream fis =  new FileInputStream("logging.properties");
LogManager.getLogManager().readConfiguration(fis);

def clientRunnable = new Runnable() {
    @Override
    void run() {
        def transport = new TSocket(HOST, PORT);
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        def id = Thread.currentThread().id
        BombahService.Client client = new BombahService.Client(protocol);
        log.debug("#$id Joining game")
        def mapState = client.joinGame()
        if( mapState ) {
            log.debug("#$id Joined game $mapState")

            def playerId = client.waitForStart()
            log.debug( "Game started, I am player #"+playerId )
            log.debug("#$id Joined game $mapState")

            client.move( new MoveAction( direction: Direction.N ))    // 10
            50.times {
                client.move( new MoveAction( direction: Direction.N ))
                client.bomb( new BombAction(chainBombs: false) )          // 150
                client.move( new MoveAction( direction: Direction.S ))
                client.move( new MoveAction( direction: Direction.S ))
                client.move( new MoveAction( direction: Direction.E ))
                client.move( new MoveAction( direction: Direction.E ))
                client.waitTicks(100)
                client.bomb( new BombAction(chainBombs: false) )          // 150
                client.move( new MoveAction( direction: Direction.W ))
                client.move( new MoveAction( direction: Direction.W ))
                client.move( new MoveAction( direction: Direction.N ))    // 10
                client.waitTicks(100)
            }

            Thread.sleep( 5000 );
            log.debug("#$id Done...")
        }

        transport.close()
    }
}

4.times {
    new Thread( clientRunnable ).start()
}

new SimpleObserver( HOST, PORT ).start();
