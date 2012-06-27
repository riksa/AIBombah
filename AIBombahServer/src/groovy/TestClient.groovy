import com.caucho.hessian.client.HessianProxyFactory
import org.riksa.aibombah.server.GameApi

/**
 * User: riksa
 * Date: 6/27/12
 * Time: 2:59 PM
 */

def url = "http://localhost:8888/AIBombahServer/hessian/GameService";

println "Connecting to $url"

HessianProxyFactory factory = new HessianProxyFactory();
GameApi api = (GameApi) factory.create(GameApi.class, url);

def start = System.currentTimeMillis()
999.times {
    api.ping()
}
def response = api.ping()
def spent = System.currentTimeMillis() - start
println "Ping $response in {$spent}ms )"

