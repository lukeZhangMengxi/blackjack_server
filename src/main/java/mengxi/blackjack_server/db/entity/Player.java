package mengxi.blackjack_server.db.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Player {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    String displayName;

    long balance;

    String email;

    public Player() {
    };

    public Player(String id, String name, long balance) {
        this.id = UUID.fromString(id);
        this.displayName = name;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, displayName='%s', balance=%d]", id, displayName, balance);
    }

    public UUID getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object obj) {
        Player other = (Player) obj;
        return this.id.toString().equals(other.getId().toString()) && this.displayName.equals(other.getDisplayName())
                && this.balance == other.getBalance();
    }

}
