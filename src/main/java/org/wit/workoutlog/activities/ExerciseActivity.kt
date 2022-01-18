package org.wit.workoutlog.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import org.wit.workoutlog.R
import org.wit.workoutlog.databinding.ActivityExerciseBinding
import org.wit.workoutlog.helpers.showImagePicker
import org.wit.workoutlog.main.MainApp
import org.wit.workoutlog.models.ExerciseModel
import timber.log.Timber.i

class ExerciseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExerciseBinding
    var exercise = ExerciseModel()
    lateinit var app: MainApp
    private lateinit var imageIntentLauncher : ActivityResultLauncher<Intent>
    private lateinit var reference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp
        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)


        if (intent.hasExtra("exercise_edit")) {
            edit = true
            exercise = intent.extras?.getParcelable("exercise_edit")!!
            binding.exerciseName.setText(exercise.exercise_name)
            binding.muscleGroup.setText(exercise.muscle_group)
            binding.btnAdd.setText(R.string.save_exercise)
            Picasso.get()
                .load(Uri.parse(exercise.image))
                .into(binding.exerciseImage)
            if (exercise.image != null) {
                binding.chooseImage.setText(R.string.change_exercise_image)
            }
        }

        binding.btnAdd.setOnClickListener() {
            exercise.exercise_name = binding.exerciseName.text.toString()
            exercise.muscle_group = binding.muscleGroup.text.toString()
            if (exercise.exercise_name.isEmpty()) {
                Snackbar.make(it,R.string.enter_exercise_name, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.all_exercises.update(exercise.copy())
                } else {
                    app.all_exercises.create(exercise.copy())
                }
            }
            setResult(RESULT_OK)
            finish()
        }

        binding.chooseImage.setOnClickListener {
            showImagePicker(imageIntentLauncher)
        }

        registerImagePickerCallback()


    }

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            exercise.image = result.data!!.data!!.toString()
                            Picasso.get()
                                .load(Uri.parse(exercise.image))
                                .into(binding.exerciseImage)
                            binding.chooseImage.setText(R.string.change_exercise_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_exercise, menu)
        if (edit) menu.getItem(1).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete ->{
                app.all_exercises.delete(exercise.copy())
                finish()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}