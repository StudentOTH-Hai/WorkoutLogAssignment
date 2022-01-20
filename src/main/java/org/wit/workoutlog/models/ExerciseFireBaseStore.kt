package org.wit.workoutlog.models

import com.google.firebase.database.*
import timber.log.Timber.i


class ExerciseFireBaseStore: ExerciseStore {
    private lateinit var database: DatabaseReference

    var exercises = ArrayList<ExerciseModel>()

    override fun findAll(): List<ExerciseModel> {
        return exercises
    }


    /*the loadExerciseData function will be called once when run the app.
    To make sure this happens at the beginning, the function is called in the Main App when we create our ExerciseFireBaseStore Class
    * */
    override fun loadExerciseData() {
        database =
            FirebaseDatabase.getInstance("https://workoutlog-4c897-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("exercises")
        database.get().addOnSuccessListener {
            if (it.exists()) {
                for (exerciseSnapshot in it.children) {
                    val tempexercise = exerciseSnapshot.getValue(ExerciseModel::class.java)
                    exercises.add(tempexercise!!)
                }
            }

        }
    }


    override fun create(exercise: ExerciseModel) {
        /*the Create function pushes a node into our realtime database if no node with the name "exercises" is existing.
        A unique key will be generated and assigned to our ExerciseModel.
        This key helps do navigate through our firebase and is use for the CRUD functions.
        * */
        database =
            FirebaseDatabase.getInstance("https://workoutlog-4c897-default-rtdb.europe-west1.firebasedatabase.app/").reference
        val key = database.push().key

        key?.let {
            exercise.fBid = key
            exercises.add(exercise)
            database.child("exercises").child(key).setValue(exercise)

        }

    }


    override fun update(exercise: ExerciseModel) {
        var foundExercise: ExerciseModel? = exercises.find { p -> p.fBid == exercise.fBid }

        if (foundExercise != null) {
            foundExercise.exercise_name = exercise.exercise_name
            foundExercise.muscle_group = exercise.muscle_group
            foundExercise.image = exercise.image
            logAll()
        }

        database.child(exercise.fBid).setValue(exercise)
    }

    override fun delete(exercise: ExerciseModel) {
        database.child(exercise.fBid).removeValue()
        exercises.remove(exercise)
    }


    fun logAll() {
        exercises.forEach { i("${it}") }
    }

}