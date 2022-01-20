package org.wit.workoutlog.models

import timber.log.Timber.i
import com.google.firebase.database.*

class WorkoutFireBaseStore {

    private lateinit var database: DatabaseReference

    val workouts = ArrayList<WorkoutModel>()

    /*use to save temporary knowledge of the selected exercises which are connected to a specific model --> Workout needs
    to be add to firebase with name and exercise list
    with loading function this Workout needs to be loaded.
     */

    val exercises = ArrayList<ExerciseModel>()

    fun getWorkout(workout: WorkoutModel): WorkoutModel? {
        var foundWorkout: WorkoutModel? = workouts.find{ p -> p.fBid == workout.fBid}

        return foundWorkout
    }

    fun loadWorkoutData() {
        database =
            FirebaseDatabase.getInstance("https://workoutlog-4c897-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("workouts")
        database.get().addOnSuccessListener {
            if (it.exists()) {
                for (workoutSnapshot in it.children) {
                    val tempworkout = workoutSnapshot.getValue(WorkoutModel::class.java)
                    workouts.add(tempworkout!!)
                }
            }

        }
    }


    fun findWorkout(workout: WorkoutModel): List<ExerciseModel>{
        var foundWorkout: WorkoutModel? = workouts.find{ p -> p.fBid == workout.fBid}

        if (foundWorkout != null)
            return foundWorkout.selected

        else return emptyList()
    }

    fun removeExerciseFromWorkout(workout: WorkoutModel, exercise: ExerciseModel){
        var foundWorkout: WorkoutModel? = workouts.find{ p -> p.fBid == workout.fBid}

        if(foundWorkout != null){
            foundWorkout.selected.remove(exercise)
        }

    }

    fun addExerciseToWorkout(workout: WorkoutModel, exercise: ExerciseModel){
        var foundWorkout: WorkoutModel? = workouts.find{ p -> p.fBid == workout.fBid}

        if(foundWorkout != null){
            foundWorkout.selected.add(exercise)
        }

    }


    fun createWorkout(workout: WorkoutModel){

        database =
            FirebaseDatabase.getInstance("https://workoutlog-4c897-default-rtdb.europe-west1.firebasedatabase.app/").reference
        val key = database.push().key

        key?.let {
            workout.fBid = key
            workouts.add(workout)
            database.child("workouts").child(key).setValue(workout)

        }


    }

    fun updateWorkout(workout: WorkoutModel){

        var foundWorkout: WorkoutModel? = workouts.find { p -> p.fBid == workout.fBid}

        if (foundWorkout != null) {
            foundWorkout.workout_name = workout.workout_name
            foundWorkout.workout_focus = workout.workout_focus
            foundWorkout.selected = workout.selected.map{ it.copy()} as ArrayList<ExerciseModel>
        }

        database.child(workout.fBid).setValue(workout)
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

    fun delete(workout: WorkoutModel) {
        database.child(workout.fBid).removeValue()
        workouts.remove(workout)
    }

}