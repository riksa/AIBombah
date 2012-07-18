package org.riksa.bombah

import org.riksa.bombah.server.BombahHandler
import org.apache.thrift.TProcessor
import org.riksa.bombah.thrift.BombahService
import org.apache.thrift.transport.TMemoryBuffer
import org.apache.thrift.protocol.TProtocol
import org.apache.thrift.protocol.TJSONProtocol

class BombahController {
    static TProcessor processor = new BombahService.Processor(new BombahHandler());

    def index() {
        redirect( uri: "/" )
    }

    def json() {
        withFormat {
            html {
                redirect( uri: "/" )
            }
            json {
                try {
                    def input = request.inputStream.bytes;
                    //        log.debug("input " + new String(input))

                    TMemoryBuffer inbuffer = new TMemoryBuffer(input.length);
                    inbuffer.write(input);
                    TProtocol inprotocol = new TJSONProtocol(inbuffer);

                    //Output
                    TMemoryBuffer outbuffer = new TMemoryBuffer(100);
                    TProtocol outprotocol = new TJSONProtocol(outbuffer);

                    processor.process(inprotocol, outprotocol);

                    byte[] output = new byte[outbuffer.length()];
                    outbuffer.readAll(output, 0, output.length);

                    render new String(output, "UTF-8")
                } catch (e) {
                    log.error(e.getMessage(), e)
                }
            }
        }
    }
}
