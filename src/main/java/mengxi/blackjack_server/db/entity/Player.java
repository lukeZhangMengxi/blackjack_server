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

    private String firstName;

    private String lastName;

    private long deposit;


    @Override
    public String toString() {
        return String.format("Customer[id=%d, firstName='%s', lastName='%s', deposit=%d]", 
        id, firstName, lastName, deposit);
    }

    public UUID getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public long getDeposit() { return deposit; }

    public void setId(UUID id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDeposit(long deposit) { this.deposit = deposit; }

}
