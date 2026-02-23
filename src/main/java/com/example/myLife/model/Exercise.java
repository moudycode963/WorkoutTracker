package com.example.myLife.model;

import jakarta.persistence.*;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // z.B. "Bankdrücken"

    private int sets;    // z.B. 3

    private double weight; // z.B. 80.0

    private String reps; // z.B. "8-12"

    // --- DIE BEZIEHUNG (Das Herzstück) ---

    // Viele Übungen gehören zu EINEM Workout (@ManyToOne)
    // FetchType.LAZY = "Lade das Workout nur, wenn ich explizit danach frage" (Spart Speicher)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false) // Das ist der Fremdschlüssel in der DB-Tabelle
    private Workout workout;

    // --- Konstruktoren ---

    public Exercise() {
    }

    public Exercise(String name, int sets, double weight, String reps, Workout workout) {
        this.name = name;
        this.sets = sets;
        this.weight = weight;
        this.reps = reps;
        this.workout = workout;
    }

    // --- Getter und Setter ---
    // (Wieder generieren lassen oder abtippen)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }

    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

    public String getReps() { return reps; }
    public void setReps(String reps) { this.reps = reps; }

    public Workout getWorkout() { return workout; }
    public void setWorkout(Workout workout) { this.workout = workout; }
}