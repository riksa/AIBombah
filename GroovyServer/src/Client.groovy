import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.transport.TSocket
import org.riksa.bombah.thrift.BombahService
import org.slf4j.LoggerFactory
import java.util.logging.LogManager

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
        log.debug("#$id Joined game $mapState")

        Thread.sleep( 5000 );
        log.debug("#$id Done...")

        transport.close()
    }
}

5.times {
    new Thread( clientRunnable ).start()
}
