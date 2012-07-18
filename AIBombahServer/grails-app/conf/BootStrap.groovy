import grails.util.Environment
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

        def user = new BombahUser(username: "admin")
        user.password = "admin"
        user.addToRoles(adminRole)
        user.save(failOnError: true)
    }


    def destroy = {
    }
}
