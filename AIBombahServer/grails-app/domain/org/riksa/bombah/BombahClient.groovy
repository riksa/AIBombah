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
    static mappedBy = [matches: 'clients']

    static constraints = {
        clientname(unique: true)
    }

    @Override
    String toString() {
        return clientname
    }
}
