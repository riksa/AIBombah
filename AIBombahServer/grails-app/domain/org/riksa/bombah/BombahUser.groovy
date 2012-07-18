package org.riksa.bombah

import org.apache.shiro.crypto.hash.Sha512Hash

import java.security.SecureRandom

/**
 * Bombah User, i.e. a person that made an AI client
 */
class BombahUser {
    Date dateCreated
    Date lastUpdated

    static int SALT_SIZE_BYTES = 32
    final static SecureRandom secureRandom = new SecureRandom()
    def credentialMatcher

    String username
    String passwordHash
    String salt

    static hasMany = [clients: BombahClient, roles: BombahRole, permissions: String]

    def setPassword(password) {
        salt = createSalt(SALT_SIZE_BYTES)
        def iterations = credentialMatcher.hashIterations
        log.error("Iterations $iterations")
        passwordHash = new Sha512Hash(password, salt, iterations).toHex()
    }

    String createSalt(int salt_bytes) {
        def salt = new byte[salt_bytes]
        secureRandom.nextBytes(salt)
        return salt.encodeHex()
    }

    static constraints = {
        username(nullable: false, blank: false, unique: true)
        salt(nullable: true)
        passwordHash(nullable: true)
    }

    @Override
    String toString() {
        return username
    }

}
