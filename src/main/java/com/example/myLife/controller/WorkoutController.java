package com.example.myLife.controller;

import com.example.myLife.model.Workout;
import com.example.myLife.repository.WorkoutRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; // <--- DIESER IMPORT FEHLTE!
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class WorkoutController {

    private final WorkoutRepository workoutRepository;

    // Konstruktor-Injektion
    public WorkoutController(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    // 1. Alle Workouts anzeigen
    @GetMapping("/")
    public String getAllWorkouts(Model model) {
        List<Workout> workouts = workoutRepository.findAll();
        model.addAttribute("workouts", workouts);
        return "index";
    }

    // 2. Formular für neues Workout anzeigen
    @GetMapping("/showNewWorkoutForm")
    public String showNewWorkoutForm(Model model) {
        Workout workout = new Workout();
        model.addAttribute("workout", workout);
        return "new_workout";
    }

    // 3. Speichern des Workouts
    @PostMapping("/saveWorkout")
    public String saveWorkout(@ModelAttribute("workout") Workout workout) {
        // HIER WAR DER FEHLER: Es muss "workoutRepository" heißen, nicht "repository"
        workoutRepository.save(workout);
        return "redirect:/";
    }

    // Optional: Demo-Daten (kannst du behalten oder löschen)
    @GetMapping("/create-demo")
    public String createDemoData() {
        Workout w1 = new Workout("Push Day", LocalDate.now(), "Gutes Training!");
        workoutRepository.save(w1);
        return "redirect:/";
    }

    // ---------------------------------------------------------
    // DELETE: Löschen eines Workouts
    // ---------------------------------------------------------

    // Die URL sieht so aus: /deleteWorkout/5
    // {id} ist ein Platzhalter für die Nummer
    @GetMapping("/deleteWorkout/{id}")
    public String deleteWorkout(@PathVariable(value = "id") Long id) {

        // 1. Der Business-Logik sagen: "Lösch das Ding mit Nummer X"
        this.workoutRepository.deleteById(id);

        // 2. Den Nutzer zurück zur Liste schicken (Refresh)
        return "redirect:/";
    }
}