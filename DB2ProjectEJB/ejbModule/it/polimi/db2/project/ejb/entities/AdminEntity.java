package it.polimi.db2.project.ejb.entities;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@NamedQueries({
        @NamedQuery(name = "AdminEntity.checkCredentials", query = "SELECT a FROM AdminEntity a WHERE a.username = :username AND a.password = :password")
})
public class AdminEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private int id;

    @Column(name = "Username", nullable = false, length = 45)
    private String username;

    @Column(name = "Password", nullable = false, length = 45)
    private String password;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
