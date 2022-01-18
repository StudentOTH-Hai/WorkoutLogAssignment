package org.wit.workoutlog.main

import android.app.Application
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import org.wit.workoutlog.models.*
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {


    val all_exercises = ExerciseFireBaseStore()
    val all_workouts = WorkoutFireBaseStore()



    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Placemark started")
        all_exercises.loadExerciseData()


        //all_exercises.add(ExerciseModel("Push-up", "chest"))
        //all_exercises.add(ExerciseModel("Pull-Up", "back"))
    }

}

