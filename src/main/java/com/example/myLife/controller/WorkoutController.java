package com.example.myLife.controller;

import com.example.myLife.model.Exercise;
import com.example.myLife.model.Workout;
import com.example.myLife.repository.ExerciseRepository;
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
    private final ExerciseRepository exerciseRepository;

    // Konstruktor-Injektion
    public WorkoutController(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
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

    // ---------------------------------------------------------
    // UPDATE: Formular zum Bearbeiten anzeigen
    // ---------------------------------------------------------
    @GetMapping("/showUpdateForm/{id}")
    public String showUpdateForm(@PathVariable(value = "id") Long id, Model model) {

        // 1. Such das Workout in der DB (oder wirf einen Fehler, wenn nicht da)
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ungültige Workout Id:" + id));

        // 2. Pack das GEFUNDENE Workout auf den Tisch (in das Model)
        // Dadurch sind die Felder im HTML schon vorausgefüllt!
        model.addAttribute("workout", workout);

        // 3. Zeige dasselbe Formular wie beim Erstellen
        return "new_workout";
    }

    // ---------------------------------------------------------
    // DETAIL-ANSICHT & ÜBUNGEN
    // ---------------------------------------------------------

    // 1. Zeige die Details eines Workouts + Liste der Übungen
    @PostMapping("/workout/{id}/addExercise")
    public String addExerciseToWorkout(@PathVariable(value = "id") Long id,
                                       @ModelAttribute("newExercise") Exercise exercise) {

        // 1. Hole das zugehörige Workout
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + id));

        // 2. WICHTIG: Stelle sicher, dass Spring weiß, dass dies eine NEUE Übung ist!
        // Wir erzwingen, dass die ID null ist, bevor wir speichern.
        exercise.setId(null);

        // 3. Verknüpfen
        exercise.setWorkout(workout);

        // 4. Speichern
        exerciseRepository.save(exercise);

        return "redirect:/workout/" + id;
    }
    // DIESE METHODE MUSS DA SEIN, DAMIT /workout/1 FUNKTIONIERT
    @GetMapping("/workout/{id}")
    public String showWorkoutDetails(@PathVariable(value = "id") Long id, Model model) {

        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid workout Id:" + id));

        model.addAttribute("workout", workout);
        model.addAttribute("newExercise", new Exercise());

        return "workout_details";
    }
}