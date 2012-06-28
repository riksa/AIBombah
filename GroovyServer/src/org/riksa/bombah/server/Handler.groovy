package org.riksa.bombah.server

import org.riksa.bombah.thrift.BombahService

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 28.6.2012
 * Time: 22:06
 * To change this template use File | Settings | File Templates.
 */
class Handler implements BombahService.Iface {
    @Override
    String ping() {
        return "pong"
    }
}
