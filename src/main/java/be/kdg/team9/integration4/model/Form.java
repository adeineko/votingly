package be.kdg.team9.integration4.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import javax.validation.constraints.NotEmpty;

@Entity
// @Table()
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Constructors, getters, and setters
    public Form() {
    }

    public Form(String name) {
        this.name = name;
    }

    // Getter and Setter methods
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // toString method for debugging and logging
    @Override
    public String toString() {
        return "Form{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
