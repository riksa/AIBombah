/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package thrift.server;

import thrift.TException;
import thrift.TProcessor;
import thrift.protocol.TProtocol;
import thrift.transport.TTransport;
import thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple singlethreaded server for testing.
 *
 */
public class TSimpleServer extends TServer {

  private static final Logger LOGGER = LoggerFactory.getLogger(TSimpleServer.class.getName());

  private boolean stopped_ = false;

  public TSimpleServer(AbstractServerArgs args) {
    super(args);
  }

  public void serve() {
    stopped_ = false;
    try {
      serverTransport_.listen();
    } catch (TTransportException ttx) {
      LOGGER.error("Error occurred during listening.", ttx);
      return;
    }

    setServing(true);

    while (!stopped_) {
      TTransport client = null;
      TProcessor processor = null;
      TTransport inputTransport = null;
      TTransport outputTransport = null;
      TProtocol inputProtocol = null;
      TProtocol outputProtocol = null;
      try {
        client = serverTransport_.accept();
        if (client != null) {
          processor = processorFactory_.getProcessor(client);
          inputTransport = inputTransportFactory_.getTransport(client);
          outputTransport = outputTransportFactory_.getTransport(client);
          inputProtocol = inputProtocolFactory_.getProtocol(inputTransport);
          outputProtocol = outputProtocolFactory_.getProtocol(outputTransport);
          while (processor.process(inputProtocol, outputProtocol)) {}
        }
      } catch (TTransportException ttx) {
        // Client died, just move on
      } catch (TException tx) {
        if (!stopped_) {
          LOGGER.error("Thrift error occurred during processing of message.", tx);
        }
      } catch (Exception x) {
        if (!stopped_) {
          LOGGER.error("Error occurred during processing of message.", x);
        }
      }

      if (inputTransport != null) {
        inputTransport.close();
      }

      if (outputTransport != null) {
        outputTransport.close();
      }

    }
    setServing(false);
  }

  public void stop() {
    stopped_ = true;
    serverTransport_.interrupt();
  }
}
