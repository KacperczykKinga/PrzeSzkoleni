package com.example.responsywne.PopUpShowers

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import com.example.responsywne.Listeners.SuccessListener
import com.example.responsywne.R
import kotlinx.android.synthetic.main.pop_up_confirmation.*

class ConfirmationShower {
    companion object{
        fun showConfirmationPopUp(
            context: Context,
            listener: SuccessListener, confirmationText: String
        ) {
            //ustawienie odpowiedniego pop upu
            val confirmationPopUp = Dialog(context)
            confirmationPopUp.setCancelable(true)
            confirmationPopUp.setCanceledOnTouchOutside(false)
            confirmationPopUp.setContentView(R.layout.pop_up_confirmation)
            confirmationPopUp.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            confirmationPopUp.confirmationText.setText(confirmationText)

            //działanie przycisków
            confirmationPopUp.yesButton.setOnClickListener(
                View.OnClickListener
            {
                listener.onSuccess()
            })

            confirmationPopUp.noButton.setOnClickListener(
                View.OnClickListener
            {
                confirmationPopUp.dismiss()
            })

            confirmationPopUp.show()
        }
    }
}