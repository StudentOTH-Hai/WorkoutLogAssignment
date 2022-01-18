package org.wit.workoutlog.models

import android.net.Uri
import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseModel(
    var fBid: String = "",
    var exercise_name: String = "",
    var muscle_group: String = "",
    var image: String = "",
    var selected: Boolean = false
): Parcelable
