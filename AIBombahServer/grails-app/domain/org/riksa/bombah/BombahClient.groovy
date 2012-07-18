package org.riksa.bombah

/**
 * An AI client
 */
class BombahClient {
    Date dateCreated
    Date lastUpdated

    String clientname

    static belongsTo = [user: BombahUser]

    static hasMany = [tournaments: Tournament, matches: Match]

    static constraints = {
        clientname(unique: true)
    }
}
