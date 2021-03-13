package com.example.responsywne.Managers

import android.util.Log
import com.example.responsywne.Listeners.BytesListener
import com.google.firebase.storage.FirebaseStorage

class PhotoManager {
    companion object {
        val storageReference = FirebaseStorage.getInstance().reference

        fun readConcretePhoto(photo: String, listener: BytesListener)
        {
            val offerPhotoReference = storageReference.child(photo)
            val ONE_MEGABYTE: Long = 1024 * 1024
            offerPhotoReference.getBytes(ONE_MEGABYTE).addOnSuccessListener { bytes ->
                listener.onSuccess(bytes)
            }
        }
    }
}