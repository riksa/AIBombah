package org.riksa.bombah

class Match {
    Date dateCreated
    Date lastUpdated
    BombahClient winner

    static hasOne = [tournament: Tournament]
    static hasMany = [clients: BombahClient]
    static belongsTo = [BombahClient]

    static constraints = {
        dateCreated()
        tournament(nullable: true)
        winner(nullable: true) // null = tie
    }

    @Override
    String toString() {
        return "Match $dateCreated"
    }

}
