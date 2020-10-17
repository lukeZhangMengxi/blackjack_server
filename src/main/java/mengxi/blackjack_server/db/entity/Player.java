package mengxi.blackjack_server.db.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Player {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    private String displayName;

    private long deposit;

    public Player() {};
    public Player(String id, String name, long deposit) {
        this.id = UUID.fromString(id); this.displayName = name; this.deposit = deposit;
    }


    @Override
    public String toString() {
        return String.format("Customer[id=%d, displayName='%s', deposit=%d]", 
        id, displayName, deposit);
    }

    public UUID getId() { return id; }
    public String getDisplayName() { return displayName; }
    public long getDeposit() { return deposit; }

    public void setId(UUID id) { this.id = id; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setDeposit(long deposit) { this.deposit = deposit; }

    @Override
    public boolean equals(Object obj) {
        Player other = (Player) obj;
        return this.id.toString().equals(other.getId().toString()) &&
            this.displayName.equals(other.getDisplayName()) &&
            this.deposit == other.getDeposit();
    }

}
