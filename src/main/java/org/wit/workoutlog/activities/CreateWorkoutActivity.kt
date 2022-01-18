package org.wit.workoutlog.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.wit.workoutlog.R
import org.wit.workoutlog.adapters.ExerciseAdapter
import org.wit.workoutlog.adapters.ExerciseListener
import org.wit.workoutlog.adapters.SelectExerciseAdapter
import org.wit.workoutlog.databinding.ActivityCreateWorkoutBinding
import org.wit.workoutlog.main.MainApp
import org.wit.workoutlog.models.ExerciseModel
import org.wit.workoutlog.models.WorkoutModel
import timber.log.Timber.i

class CreateWorkoutActivity : AppCompatActivity(), ExerciseListener {

    private lateinit var binding: ActivityCreateWorkoutBinding
    lateinit var app: MainApp
    var workout = WorkoutModel()
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    var tempExercises = ArrayList<ExerciseModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        binding.btnChoose.setOnClickListener(){
            val multiselectIntent = Intent(this, ExerciseMultiSelectionActivity::class.java)
            refreshIntentLauncher.launch(multiselectIntent)
        }



        val layoutManager = LinearLayoutManager(this)

        if(app.all_workouts.findAllExercises().isEmpty())
        {
            Snackbar.make(binding.root,R.string.no_exercises, Snackbar.LENGTH_LONG)
                .show()
        }


        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ExerciseAdapter(app.all_workouts.findAllExercises(),this)



            i(app.all_workouts.exercises.size.toString())


        registerRefreshCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_createworkout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                workout.workout_name = binding.workoutName.text.toString()
                tempExercises = app.all_workouts.findAllExercises() as ArrayList<ExerciseModel>
                workout.selected = tempExercises
                if (workout.workout_name.isEmpty()) {
                    i("name of workout is empty")
                } else {
                        for(exercise in app.all_exercises.exercises){
                            exercise.selected = false
                        }


                        app.all_workouts.createWorkout(workout.copy())
                        app.all_workouts.exercises.clear()
                }
                setResult(RESULT_OK)
                finish()


            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onExerciseClick(exercise: ExerciseModel) {
        i("Clicked")
    }
    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }
}