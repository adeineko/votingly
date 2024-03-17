package be.kdg.team9.integration4.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long answerId;

    @Column
    @ManyToOne(fetch = FetchType.LAZY)
    private Question questions;

    public Answer() {
    }
}
