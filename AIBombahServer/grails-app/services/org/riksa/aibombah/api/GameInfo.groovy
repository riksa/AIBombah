package org.riksa.aibombah.api

/**
 * Created with IntelliJ IDEA.
 * User: riksa
 * Date: 27.6.2012
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
class GameInfo implements Serializable {
    public enum Error {SERVER_FULL}

    long id
    Error error

    @Override
    public String toString() {
        return "GameInfo{" +
                "error=" + error +
                ", id=" + id +
                '}';
    }
}
