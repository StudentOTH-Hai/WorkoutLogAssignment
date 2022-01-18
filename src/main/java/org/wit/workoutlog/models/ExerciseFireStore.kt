/*package org.wit.workoutlog.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}



class ExerciseFireStore : ExerciseStore{

    private lateinit var FirebaseDatabase rootNode
    private lateinit var DatabaseReference reference

    val exercises = ArrayList<ExerciseModel>()


    override fun findAll(): List<ExerciseModel> {
        return exercises
    }

    override fun create(exercise: ExerciseModel) {
        exercise.id = getId()
        exercises.add(exercise)
        logAll()
    }

    fun logAll() {
        exercises.forEach{ i("${it}") }
    }


    override fun update(exercise: ExerciseModel) {
        var foundExercise: ExerciseModel? = exercises.find { p -> p.id == exercise.id }
        if (foundExercise != null) {
            foundExercise.exercise_name = exercise.exercise_name
            foundExercise.muscle_group = exercise.muscle_group
            foundExercise.image = exercise.image
            logAll()
        }
    }


}*/