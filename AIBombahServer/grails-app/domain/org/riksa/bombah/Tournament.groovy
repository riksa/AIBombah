package org.riksa.bombah

class Tournament {
    Date dateCreated
    Date lastUpdated

    static hasMany = [ matches: Match ]

    static constraints = {
    }

    @Override
    String toString() {
        return "Tournament $dateCreated"
    }

}
