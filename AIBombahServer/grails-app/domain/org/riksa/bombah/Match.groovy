package org.riksa.bombah

class Match {

    static hasOne = [tournament: Tournament]

    static constraints = {
        tournament(nullable: true)
    }
}
