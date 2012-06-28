import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.transport.TSocket
import org.riksa.bombah.thrift.BombahService

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 28.6.2012
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */

def HOST = "localhost"
def PORT = 12345

def transport = new TSocket(HOST, 12345);
transport.open();

TProtocol protocol = new TBinaryProtocol(transport);
BombahService.Client client = new BombahService.Client(protocol);

client.ping()

def start = System.currentTimeMillis()
1000.times {
    client.ping()
}

def spent = System.currentTimeMillis()-start

println "ping "+client.ping()
println "1000 evocations too ${spent}ms"

transport.close()