package mengxi.blackjack_server.db.entity;

public class PlayerWithCredentials extends Player {
    String passwordHash;
    String salt;

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
