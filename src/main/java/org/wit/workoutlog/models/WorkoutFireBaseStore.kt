package org.wit.workoutlog.models

class WorkoutFireBaseStore {

    val workouts = ArrayList<WorkoutModel>()

    /*use to save temporary knowledge of the selected exercises which are connected to a specific model --> Workout needs
    to be add to firebase with name and exercise list
    with loading function this Workout needs to be loaded.
     */

    val exercises = ArrayList<ExerciseModel>()


    fun createWorkout(workout: WorkoutModel){
        workouts.add(workout)
    }

    fun findAllWorkouts(): List<WorkoutModel>{
        return workouts
    }

    fun findAllExercises(): List<ExerciseModel> {
        return exercises
    }

    fun createExercise(exercise: ExerciseModel) {
        exercises.add(exercise)
    }

}