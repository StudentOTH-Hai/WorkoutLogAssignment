package org.wit.workoutlog.models

data class WorkoutModel(
    var workout_name: String = "",
    var selected: ArrayList<ExerciseModel> = ArrayList<ExerciseModel>()
)
