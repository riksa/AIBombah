import com.caucho.hessian.client.HessianProxyFactory
import org.riksa.aibombah.api.GameApi

/**
 * User: riksa
 * Date: 6/27/12
 * Time: 2:59 PM
 */

def url = "http://localhost:8080/AIBombahServer/hessian/GameService";

println "Connecting to $url"

HessianProxyFactory factory = new HessianProxyFactory();
GameApi api = (GameApi) factory.create(GameApi.class, url);

println "Ping ${api.ping()}"

def gameInfo = api.createGame()
println "Created a new game $gameInfo"

api.listGames().each {
    println "Game running :: $it"
}

api.killGame( gameInfo.id )

api.listGames().each {
    println "Game running :: $it"
}


