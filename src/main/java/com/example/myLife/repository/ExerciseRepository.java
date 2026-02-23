package com.example.myLife.repository;

import com.example.myLife.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    // Leer lassen - Spring macht den Rest!
}