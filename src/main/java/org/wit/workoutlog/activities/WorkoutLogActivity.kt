package org.wit.workoutlog.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import com.google.firebase.auth.FirebaseAuth
import org.wit.workoutlog.R
import org.wit.workoutlog.databinding.ActivityWorkoutlogBinding
import org.wit.workoutlog.main.MainApp
import timber.log.Timber
import timber.log.Timber.i
import kotlin.math.sign


class WorkoutLogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWorkoutlogBinding
    var app : MainApp? = null
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        binding = ActivityWorkoutlogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Timber.plant(Timber.DebugTree())
        app = application as MainApp




        binding.toolbarMain.title = title
        setSupportActionBar(binding.toolbarMain)

        i("WorkoutLog Activity started..")

        binding.btnChoose.setOnClickListener() {
            i("choose Button Pressed")
        }

        binding.btnAdd.setOnClickListener() {
            val launcherIntent2 = Intent(this, WorkoutListActivity::class.java)
            startActivity(launcherIntent2)
        }

        binding.btnShow.setOnClickListener(){
            val launcherIntent = Intent(this, ExerciseListActivity::class.java)
            startActivity(launcherIntent)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_workoutlog, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_signOut -> {
                mAuth.signOut()
                val signOutIntent = Intent(this, LoginActivity::class.java)
                startActivity(signOutIntent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}