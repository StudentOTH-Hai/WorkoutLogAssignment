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
import org.wit.workoutlog.adapters.WorkoutAdapter
import org.wit.workoutlog.adapters.WorkoutListener
import org.wit.workoutlog.databinding.ActivityExerciseListBinding
import org.wit.workoutlog.databinding.ActivityWorkoutListBinding
import org.wit.workoutlog.main.MainApp
import org.wit.workoutlog.models.ExerciseModel
import org.wit.workoutlog.models.WorkoutModel
import timber.log.Timber

class WorkoutListActivity : AppCompatActivity() , WorkoutListener{

    /*In our Workoutlistactitivy the workouts we are creating will be listed
    */


    lateinit var app: MainApp
    private lateinit var binding: ActivityWorkoutListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp
        Timber.i(app.all_workouts.workouts.toString())
        val layoutManager = LinearLayoutManager(this)

        if(app.all_workouts.workouts.isEmpty())
        {
            Snackbar.make(binding.root,R.string.no_workouts, Snackbar.LENGTH_LONG)
                .show()
        }


        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = WorkoutAdapter(app.all_workouts.findAllWorkouts(),this)


        registerRefreshCallback()

    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_workoutlist, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, CreateWorkoutActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        when (item.itemId){
            R.id.item_cancel-> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }

    override fun onWorkoutClick(workout: WorkoutModel) {
            val launcherIntent = Intent(this, CreateWorkoutActivity::class.java)
            launcherIntent.putExtra("edit_workout", workout)
            refreshIntentLauncher.launch(launcherIntent)

    }
}