package org.wit.workoutlog.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.workoutlog.databinding.CardExercisesBinding
import org.wit.workoutlog.models.ExerciseModel


interface ExerciseListener {

    fun onExerciseClick(exercise: ExerciseModel)
}

class ExerciseAdapter(private var exercises: List<ExerciseModel>, private val listener: ExerciseListener) :
    RecyclerView.Adapter<ExerciseAdapter.MainHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardExercisesBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {

        val exercise = exercises[holder.adapterPosition]
        holder.bind(exercise, listener)
    }

    override fun getItemCount(): Int = exercises.size

    class MainHolder(private val binding : CardExercisesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: ExerciseModel, listener: ExerciseListener) {
            binding.exerciseName.text = exercise.exercise_name
            binding.muscleGroup.text = exercise.muscle_group
            Picasso.get().load(Uri.parse(exercise.image)).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onExerciseClick(exercise) }
        }
    }


}