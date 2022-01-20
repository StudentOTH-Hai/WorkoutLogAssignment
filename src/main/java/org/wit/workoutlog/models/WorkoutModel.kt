package org.wit.workoutlog.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class WorkoutModel(
    var fBid: String = "",
    var workout_name: String = "",
    var workout_focus: String = "",
    var selected: ArrayList<ExerciseModel> = ArrayList<ExerciseModel>()
) : Parcelable