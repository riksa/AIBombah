package org.riksa.bombah

class Match {
    Date dateCreated
    Date lastUpdated

    static hasOne = [tournament: Tournament]

    static constraints = {
        tournament(nullable: true)
    }
}
