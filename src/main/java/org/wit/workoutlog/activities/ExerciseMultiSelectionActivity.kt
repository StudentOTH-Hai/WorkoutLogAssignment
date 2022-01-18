package org.wit.workoutlog.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu

import androidx.appcompat.widget.SearchView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.wit.workoutlog.R
import org.wit.workoutlog.adapters.*
import org.wit.workoutlog.databinding.ActivityExerciseMultiSelectionBinding
import org.wit.workoutlog.main.MainApp
import org.wit.workoutlog.models.ExerciseModel
import org.wit.workoutlog.models.WorkoutModel
import timber.log.Timber.i
import java.util.*
import kotlin.collections.ArrayList

class ExerciseMultiSelectionActivity :  AppCompatActivity(), OnItemCheckListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityExerciseMultiSelectionBinding
    lateinit var tempExerciseList: ArrayList<ExerciseModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExerciseMultiSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)


        app = application as MainApp


        tempExerciseList = ArrayList<ExerciseModel>()
        val layoutManager = LinearLayoutManager(this)


        if(app.all_exercises.exercises.isEmpty())
        {
            Snackbar.make(binding.root,R.string.no_exercises, Snackbar.LENGTH_LONG)
                .show()
        }

        tempExerciseList.addAll(app.all_exercises.exercises)
        i("text setted")

        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = SelectExerciseAdapter(tempExerciseList,this)



        binding.btnChoose.setOnClickListener(){
            i("this are selected ")
            setResult(RESULT_OK)

            finish()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_exercisemultiselection, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search?.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                i("sucess")

                tempExerciseList.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if(searchText.isNotEmpty()){

                    app.all_exercises.exercises.forEach{
                        if(it.exercise_name.lowercase(Locale.getDefault()).contains(searchText)){
                            tempExerciseList.add(it)
                        }
                    }
                    binding.recyclerView.adapter?.notifyDataSetChanged()

                }else{

                    tempExerciseList.clear()
                    tempExerciseList.addAll(app.all_exercises.exercises)
                    binding.recyclerView.adapter?.notifyDataSetChanged()

                }

                return false

            }

        })

        return super.onCreateOptionsMenu(menu)
    }


    override fun onItemCheck (exercise: ExerciseModel) {
        val tempexer = exercise.copy()
        exercise.selected = true
        i("exercise added")
        app.all_workouts.createExercise(tempexer)
        i(app.all_workouts.exercises.size.toString())
        //in firebase hochladen

    }

    override fun onItemUncheck(exercise: ExerciseModel) {
        exercise.selected = false
        i("exercise remove")
        app.all_workouts.exercises.remove(exercise)
    }


}