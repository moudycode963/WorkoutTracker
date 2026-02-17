package com.example.myLife.repository;

import com.example.myLife.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Wir erben von JpaRepository.
// <Workout, Long> bedeutet: Wir verwalten "Workout"-Objekte und die ID ist vom Typ "Long".
@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    // Das war's schon!
    // Spring Boot zaubert im Hintergrund automatisch Methoden wie:
    // .save(), .findAll(), .findById(), .delete()
    // Wir müssen hier NICHTS implementieren.

}