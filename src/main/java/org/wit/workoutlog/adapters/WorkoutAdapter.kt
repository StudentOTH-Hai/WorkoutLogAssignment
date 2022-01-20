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
import org.wit.workoutlog.databinding.CardWorkoutsBinding
import org.wit.workoutlog.models.ExerciseModel
import org.wit.workoutlog.models.WorkoutModel


interface WorkoutListener {
    fun onWorkoutClick(workout: WorkoutModel)
}

class WorkoutAdapter(private var workouts: List<WorkoutModel>, private val listener: WorkoutListener) :
    RecyclerView.Adapter<WorkoutAdapter.MainHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardWorkoutsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {

        val workout = workouts[holder.adapterPosition]
        holder.bind(workout, listener)
    }

    override fun getItemCount(): Int = workouts.size

    class MainHolder(private val binding : CardWorkoutsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(workout: WorkoutModel, listener: WorkoutListener) {
            binding.workoutName.text = workout.workout_name
            binding.focus.text = workout.workout_focus
            binding.root.setOnClickListener { listener.onWorkoutClick(workout) }
        }
    }


}