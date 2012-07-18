package org.riksa.bombah

class BombahRole {
    String name

    static hasMany = [ users: BombahUser, permissions: String ]
    static belongsTo = BombahUser

    static constraints = {
        name(nullable: false, blank: false, unique: true)
    }
}
