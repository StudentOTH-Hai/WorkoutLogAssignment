package org.wit.workoutlog.models

import android.graphics.Bitmap
import android.widget.Toast
import com.firebase.ui.auth.data.model.User
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseError
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import org.wit.workoutlog.R
import timber.log.Timber.i
import java.io.ByteArrayOutputStream
import java.io.File

class ExerciseFireBaseStore: ExerciseStore {
    private lateinit var database: DatabaseReference

    private lateinit var storage: StorageReference



    var exercises = ArrayList<ExerciseModel>()


    override fun findAll(): List<ExerciseModel> {
        return exercises
    }


    // Get the Realtime Database Data for the exercises List on App run;
    fun loadExerciseData(){
        database = FirebaseDatabase.getInstance("https://workoutlog-4c897-default-rtdb.europe-west1.firebasedatabase.app").getReference("exercises")
        database.get().addOnSuccessListener {
                if(it.exists()){
                    for(exerciseSnapshot in it.children){
                        val tempexercise = exerciseSnapshot.getValue(ExerciseModel::class.java)
                        exercises.add(tempexercise!!)
                    }
                }

        }
    }


   override fun create(exercise: ExerciseModel) {

       database = FirebaseDatabase.getInstance("https://workoutlog-4c897-default-rtdb.europe-west1.firebasedatabase.app").reference
       val key = database.push().key

        key?.let{
            exercise.fBid = key
            exercises.add(exercise)
            database.child("exercises").child(key).setValue(exercise)

       }

    }




    override fun update(exercise: ExerciseModel) {
        var foundExercise: ExerciseModel? = exercises.find { p -> p.fBid == exercise.fBid}

        if (foundExercise != null) {
            foundExercise.exercise_name = exercise.exercise_name
            foundExercise.muscle_group = exercise.muscle_group
            foundExercise.image = exercise.image
            logAll()
    }

        database.child("exercises").child(exercise.fBid).setValue(exercise)
}

    override fun delete(exercise: ExerciseModel) {
        database.child("exercises").child(exercise.fBid).removeValue()
        exercises.remove(exercise)
    }


    fun logAll() {
        exercises.forEach{ i("${it}") }
    }

    /*fun updateImage(exercise: ExerciseModel) {
        if (exercise.image != "") {
            val fileName = File(exercise.image)
            val imageName = fileName.getName()

            var imageRef = storage.child(imageName)
            val baos = ByteArrayOutputStream()
            val bitmap = exercise.image?.let { readImageFromPath(context, it) }

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        exercise.image = it.toString()
                        database.child("exercises").child(exercise.fBid).setValue(exercise)
                    }
                }
            }
        }*/
    }

    /*fun saveImage(exercise: ExerciseModel) {
        if (exercise.image != null) {
            val fileName = exercise.fBid
            storage = FirebaseStorage.getInstance().getReference("images/$fileName")
            storage.putFile(exercise.image).addOnSuccessListener {
                i("picture upload successful")
            }


        }
    }*/
