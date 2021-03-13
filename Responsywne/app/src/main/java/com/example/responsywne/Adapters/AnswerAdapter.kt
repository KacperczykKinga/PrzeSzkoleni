package com.example.responsywne.Adapters

import android.app.Activity
import android.content.res.ColorStateList
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import androidx.core.content.ContextCompat
import com.example.responsywne.Model.Question
import com.example.responsywne.R
import kotlinx.android.synthetic.main.item_one_question.view.*

class AnswerAdapter (private val context: Activity, private val questionList: MutableList<Question>, private  val answersList: ArrayList<String>, private val grade: Int, private val displayMetrics: DisplayMetrics):
    ArrayAdapter<Question>(context,
        R.layout.item_one_question, questionList) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val convertView = inflater.inflate(R.layout.item_one_question, null, true)

        convertView.tVQuestion.setText(questionList[position].question)
        convertView.A.setText(questionList[position].A)
        convertView.B.setText(questionList[position].B)
        convertView.C.setText(questionList[position].C)

        convertView.A.isEnabled = false
        convertView.B.isEnabled = false
        convertView.C.isEnabled = false

        if(convertView.rGAnswers.orientation == RadioGroup.HORIZONTAL) {
            convertView.A.width = (displayMetrics.widthPixels * 0.3).toInt()
            convertView.B.width = (displayMetrics.widthPixels * 0.3).toInt()
            convertView.C.width = (displayMetrics.widthPixels * 0.3).toInt()
        }
        if(questionList[position].answer == "A")
        {
            convertView.A.setTextColor(ContextCompat.getColor(context,
                R.color.color1
            ))
            convertView.A.isChecked = true
            convertView.A.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context,
                R.color.color1
            ))
        }
        else if (questionList[position].answer == "B")
        {
            convertView.B.setTextColor(ContextCompat.getColor(context,
                R.color.color1
            ))
            convertView.B.isChecked = true
            convertView.B.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context,
                R.color.color1
            ))
        }
        else if (questionList[position].answer == "C")
        {
            convertView.C.setTextColor(ContextCompat.getColor(context,
                R.color.color1
            ))
            convertView.C.isChecked = true
            convertView.C.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context,
                R.color.color1
            ))
        }

        if(answersList[position] == "A")
        {
            convertView.A.isChecked = true
            if(questionList[position].answer != "A")
            {
                convertView.A.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context,
                    R.color.color4
                ))
            }
        }
        else if(answersList[position] == "B")
        {
            convertView.B.isChecked = true
            if(questionList[position].answer != "B")
            {
                convertView.B.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context,
                    R.color.color4
                ))
            }
        }
        else if(answersList[position] == "C")
        {
            convertView.C.isChecked = true
            if(questionList[position].answer != "C")
            {
                convertView.C.buttonTintList = ColorStateList.valueOf(ContextCompat.getColor(context,
                    R.color.color4
                ))
            }
        }

        if(position == 3)
        {
            convertView.toEndTest.visibility = View.VISIBLE
            convertView.okButton.visibility = View.VISIBLE
            convertView.tVGrade.visibility = View.VISIBLE
            convertView.tVGrade.setText(context.getString(R.string.grade) + grade)
            convertView.okButton.setOnClickListener {
                context.onBackPressed()
            }
        }

        return convertView
    }
}