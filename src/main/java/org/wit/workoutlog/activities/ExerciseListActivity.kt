package org.wit.workoutlog.activities
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import org.wit.workoutlog.R
import org.wit.workoutlog.adapters.ExerciseAdapter
import org.wit.workoutlog.adapters.ExerciseListener
import org.wit.workoutlog.databinding.ActivityExerciseListBinding
import org.wit.workoutlog.main.MainApp
import org.wit.workoutlog.models.ExerciseModel
import timber.log.Timber.i


class ExerciseListActivity : AppCompatActivity(), ExerciseListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityExerciseListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)



        if(app.all_exercises.exercises.isEmpty())
        {
            Snackbar.make(binding.root,R.string.no_exercises, Snackbar.LENGTH_LONG)
                .show()
        }


        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ExerciseAdapter(app.all_exercises.findAll(),this)

        registerRefreshCallback()

        i(app.all_exercises.findAll().toString())
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_exerciselist, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, ExerciseActivity::class.java)
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

    override fun onExerciseClick(exercise: ExerciseModel) {
        val launcherIntent = Intent(this, ExerciseActivity::class.java)
        launcherIntent.putExtra("exercise_edit", exercise)
        refreshIntentLauncher.launch(launcherIntent)

    }

    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { binding.recyclerView.adapter?.notifyDataSetChanged() }
    }



}




