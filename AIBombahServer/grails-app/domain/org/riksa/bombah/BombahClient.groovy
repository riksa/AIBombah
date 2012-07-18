package org.riksa.bombah

/**
 * An AI client
 */
class BombahClient {
    String name

    static belongsTo = [user: BombahUser]

    static hasMany = [tournaments: Tournament, matches: Match]

    static constraints = {
        name(unique: true)
    }
}
