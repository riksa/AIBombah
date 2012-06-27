package org.riksa.aibombah.api

import org.riksa.aibombah.api.GameInfo

/**
 * User: riksa
 * Date: 6/27/12
 * Time: 3:37 PM
 */
public interface GameApi {
    String ping()

    GameInfo createGame()
    void killGame( long id )
    Collection<GameInfo> listGames()

}