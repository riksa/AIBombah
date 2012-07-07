package org.riksa.bombah

import grails.converters.JSON
import org.json.simple.JSONObject
import thrift.transport.TMemoryBuffer
import thrift.protocol.TProtocol
import thrift.protocol.TJSONProtocol
import thrift.TProcessor
import thrift.BombahService
import org.riksa.bombah.server.BombahHandler

class BombahController {
    def json() {
        def input = request.inputStream.bytes;
        log.debug("input " + new String(input))

//Input
        TMemoryBuffer inbuffer = new TMemoryBuffer(input.length);
        inbuffer.write(input);
        TProtocol inprotocol = new TJSONProtocol(inbuffer);

        //Output
        TMemoryBuffer outbuffer = new TMemoryBuffer(100);
        TProtocol outprotocol = new TJSONProtocol(outbuffer);

        TProcessor processor = new BombahService.Processor(new BombahHandler());
        processor.process(inprotocol, outprotocol);

        byte[] output = new byte[outbuffer.length()];
        outbuffer.readAll(output, 0, output.length);

        render new String(output,"UTF-8")
    }
}
