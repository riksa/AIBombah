package org.riksa.bombah.client

import org.riksa.bombah.thrift.Direction

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 12.7.2012
 * Time: 18:30
 * To change this template use File | Settings | File Templates.
 */
class Action {
    enum ActionTypeEnum {
        MOVE, BOMB, WAIT
    }

    ActionTypeEnum what
    Direction direction

    @Override
    public String toString() {
        return "Action{" +
                "what=" + what +
                ", direction=" + direction +
                '}';
    }
}
