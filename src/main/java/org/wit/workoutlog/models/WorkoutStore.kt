package org.wit.workoutlog.models

interface WorkoutStore {

    fun findAll(): List<ExerciseModel>
    fun create(exercise: ExerciseModel)
    fun update(exercise: ExerciseModel)
    fun delete(exercise: ExerciseModel)
}