package org.riksa.aibombah.server

class GameService implements GameApi {
    static transactional = true
    static expose = ['rmi','hessian']

    @Override
    String ping() {
        log.debug("ping requested")
        return "pong"
    }
}
