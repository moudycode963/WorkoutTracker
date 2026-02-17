package com.example.myLife.model;
import jakarta.persistence.*; // Wichtig: jakarta.*, nicht javax.* (in neuen Spring Versionen)
import java.time.LocalDate;

@Entity // Sagt Spring: "Mach aus dieser Klasse eine Datenbank-Tabelle!"
@Table(name = "workouts") // (Optional) Wir benennen die Tabelle explizit "workouts" (Mehrzahl)
public class Workout {

    @Id // Das ist der Primary Key (Der einzigartige Ausweis)
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Die Datenbank zählt automatisch hoch (1, 2, 3...)
    private Long id;

    private String title; // z.B. "Brust Training"

    private LocalDate date; // Java's modernes Datums-Format

    @Column(length = 500) // Wir erlauben bis zu 500 Zeichen für Notizen
    private String notes;

    // --- Konstruktoren ---
    // JPA braucht IMMER einen leeren Konstruktor (Default Constructor)
    public Workout() {
    }

    // Ein bequemer Konstruktor für uns zum Erstellen
    public Workout(String title, LocalDate date, String notes) {
        this.title = title;
        this.date = date;
        this.notes = notes;
    }

    // --- Getter und Setter ---
    // Da wir kein Lombok nutzen, müssen diese hier stehen,
    // damit Spring die Daten lesen und schreiben kann.
    // (Tipp: In IntelliJ: Rechtsklick -> Generate -> Getter and Setter -> Alle auswählen)

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}