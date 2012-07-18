package org.riksa.bombah

/**
 * Bombah User, i.e. a person that made an AI client
 */
class BombahUser {
    String username
    String passwordHash
    
    static hasMany = [ clients: BombahClient, roles: BombahRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true)
    }
}
