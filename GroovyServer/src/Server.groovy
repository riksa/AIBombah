import org.apache.thrift.server.TServer
import org.apache.thrift.server.TSimpleServer
import org.apache.thrift.transport.TServerSocket
import org.apache.thrift.transport.TServerTransport
import org.riksa.bombah.server.Handler
import org.riksa.bombah.thrift.BombahService

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 28.6.2012
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */

def PORT = 12345

//final TNonblockingServerSocket socket = new TNonblockingServerSocket(PORT);
//final BombahService.Processor processor = new BombahService.Processor( new Handler());
//final TServer server = new THsHaServer(processor, socket, new TFramedTransport.Factory(), new TCompactProtocol.Factory())

//TServerTransport serverTransport = new TServerSocket(PORT);
//final BombahService.Processor processor = new BombahService.Processor( new Handler());
//TServer server = new TSimpleServer(processor, serverTransport);

try {
    Handler handler = new Handler();
    BombahService.Processor processor = new BombahService.Processor(handler);

    TServerTransport serverTransport = new TServerSocket(PORT);
    TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));

    // Use this for a multithreaded server
    // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

    System.out.println("Starting the simple server...");
    server.serve();
} catch (Exception e) {
    e.printStackTrace();
}

