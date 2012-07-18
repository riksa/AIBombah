import grails.util.Environment
import org.riksa.bombah.BombahRole
import org.riksa.bombah.BombahUser
import org.apache.shiro.crypto.hash.Sha512Hash
import org.riksa.bombah.BombahRole
import org.riksa.bombah.BombahUser

class BootStrap {

    def init = { servletContext ->
        switch (Environment.current) {
            case Environment.DEVELOPMENT:
                bootStrapUser()
                break
        }
    }

    def bootStrapUser() {
        def adminRole = new BombahRole(name: "admin").save(failOnError: true)
        adminRole.addToPermissions("*")

        def user = new BombahUser(username: "admin", passwordHash: new Sha512Hash("admin").toHex()).save(failOnError: true)
        user.addToRoles(adminRole)
    }


    def destroy = {
    }
}
