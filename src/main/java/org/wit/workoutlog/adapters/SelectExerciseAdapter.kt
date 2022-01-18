package org.wit.workoutlog.adapters

import android.net.Uri
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.selects.select
import org.wit.workoutlog.databinding.CardSelectexerciseBinding
import org.wit.workoutlog.models.ExerciseModel
import org.wit.workoutlog.activities.CreateWorkoutActivity


interface OnItemCheckListener {
    fun onItemCheck(exercise: ExerciseModel)
    fun onItemUncheck(exercise: ExerciseModel)
}

class SelectExerciseAdapter(private var exercises: List<ExerciseModel>, private val listener: OnItemCheckListener) :
    RecyclerView.Adapter<SelectExerciseAdapter.MainHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardSelectexerciseBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }



    override fun onBindViewHolder(holder: MainHolder, position: Int) {

        val exercise = exercises[holder.adapterPosition]


        holder.bind(exercise, listener)
    }

    override fun getItemCount(): Int = exercises.size

    class MainHolder(private val binding : CardSelectexerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(exercise: ExerciseModel, listener: OnItemCheckListener) {

            binding.exerciseName.text = exercise.exercise_name
            binding.muscleGroup.text = exercise.muscle_group
            Picasso.get().load(Uri.parse(exercise.image)).resize(200,200).into(binding.imageIcon)
            if(exercise.selected){
                binding.checked.visibility = VISIBLE
            }


            binding.root.setOnClickListener {

                if(exercise.selected){
                    binding.checked.visibility = INVISIBLE
                    listener.onItemUncheck(exercise)
                }
                else{
                binding.checked.visibility = VISIBLE
                listener.onItemCheck(exercise)

            }
        }}

        }




    }


