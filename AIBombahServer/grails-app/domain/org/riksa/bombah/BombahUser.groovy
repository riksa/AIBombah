package org.riksa.bombah

class BombahUser {
    String username
    String passwordHash
    
    static hasMany = [ roles: BombahRole, permissions: String ]

    static constraints = {
        username(nullable: false, blank: false, unique: true)
    }
}
