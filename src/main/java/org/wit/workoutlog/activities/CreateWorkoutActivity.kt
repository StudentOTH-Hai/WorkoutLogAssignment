package org.wit.workoutlog.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.wit.workoutlog.R
import org.wit.workoutlog.adapters.ExerciseAdapter
import org.wit.workoutlog.adapters.ExerciseListener
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
    var edit = false
    var tempExercises = ArrayList<ExerciseModel>()


    /*The CreateWorkoutActivity functions simliar to our ExerciseActivity where we can create WorkoutModel by setting the Edittext fields
    and adding Exercises to our Workoutmodel through our ExerciseMultiSelectionActivity
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateWorkoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)
        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager


        if (intent.hasExtra("edit_workout")) {
            /*if we enter this activity by clicking on a existing workout, we enter the edit mode.
            In the edit mode our recyclerView is set by the WorkoutModel list
            features like editing the text fields and removing and adding new exercises will be able to use now.
            * */

            edit = true
            workout = intent.extras?.getParcelable("edit_workout")!!
            binding.workoutName.setText(workout.workout_name)
            binding.focus.setText(workout.workout_focus)

            i(workout.selected.toString())

            binding.recyclerView.adapter = ExerciseAdapter(app.all_workouts.findWorkout(workout), this)


        }
        else {


            if (app.all_workouts.findAllExercises().isEmpty()) {
                Snackbar.make(binding.root, R.string.no_exercises, Snackbar.LENGTH_LONG)
                    .show()
            }


            binding.recyclerView.adapter =
                ExerciseAdapter(app.all_workouts.findAllExercises(), this)

        }

        binding.btnChoose.setOnClickListener(){
            val multiselectIntent = Intent(this, ExerciseMultiSelectionActivity::class.java)
            if(edit){
                multiselectIntent.putExtra("workout_edit", workout)
            }
            refreshIntentLauncher.launch(multiselectIntent)
        }


        registerRefreshCallback()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_createworkout, menu)
        if (edit) menu.getItem(1).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_save -> {
                /*if the save button in the menu bar is clicked, we are saving the the texts and the exercise list to a workout model
                this workout model is added to our workoutFirebaseStore and will be uploaded to our realtime database for consistency
                */
                workout.workout_name = binding.workoutName.text.toString()
                workout.workout_focus = binding.focus.text.toString()


                    if (edit) {
                        workout.selected = app.all_workouts.findWorkout(workout) as ArrayList<ExerciseModel>
                        app.all_workouts.updateWorkout(workout.copy())
                    } else {
                        tempExercises = app.all_workouts.findAllExercises()
                            .map { it.copy() } as ArrayList<ExerciseModel>
                        workout.selected = tempExercises
                        app.all_workouts.createWorkout(workout.copy())

                    }


                    app.all_workouts.exercises.clear()

                    for (exercise in app.all_exercises.exercises) {
                        exercise.selected = false
                    }

                setResult(RESULT_OK)
                finish()
            }
            R.id.item_remove ->{
                app.all_workouts.delete(workout)
                setResult(RESULT_OK)
                finish()
            }

            }


        return super.onOptionsItemSelected(item)
    }

    override fun onExerciseClick(exercise: ExerciseModel) {
        i("Exercise clicked")
    }


    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }
}