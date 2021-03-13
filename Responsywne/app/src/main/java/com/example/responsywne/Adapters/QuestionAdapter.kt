package com.example.responsywne.Adapters

import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.responsywne.Listeners.AnswerListener
import com.example.responsywne.Model.Question
import com.example.responsywne.R
import com.example.responsywne.Listeners.SuccessListener
import kotlinx.android.synthetic.main.item_one_question.view.*


class QuestionAdapter(private val context: Activity, private val questionList: MutableList<Question>, private val displayMetrics: DisplayMetrics, private val listener: AnswerListener, private val endListener: SuccessListener):
    ArrayAdapter<Question>(context,
        R.layout.item_one_question, questionList) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val convertView = inflater.inflate(R.layout.item_one_question, null, true)

        convertView.tVQuestion.setText(questionList[position].question)
        convertView.A.setText(questionList[position].A)
        convertView.B.setText(questionList[position].B)
        convertView.C.setText(questionList[position].C)

        convertView.A.setOnClickListener { view -> onRadioButtonClicked(view, position) }
        convertView.B.setOnClickListener { view -> onRadioButtonClicked(view, position) }
        convertView.C.setOnClickListener { view -> onRadioButtonClicked(view, position) }

        if(convertView.rGAnswers.orientation == RadioGroup.HORIZONTAL) {
            convertView.A.width = (displayMetrics.widthPixels * 0.3).toInt()
            convertView.B.width = (displayMetrics.widthPixels * 0.3).toInt()
            convertView.C.width = (displayMetrics.widthPixels * 0.3).toInt()
        }

        if(position == 3)
        {
            convertView.testButton.visibility = View.VISIBLE
            convertView.testButton.setOnClickListener { endListener.onSuccess() }
        }

        return convertView
    }

    private fun onRadioButtonClicked(view: View, position: Int)
    {
        val checked = (view as RadioButton).isChecked
        // Check which radio button was clicked
        when (view.getId()) {
            R.id.A -> if (checked) listener.onCheckAnswer(position, "A")
            R.id.B -> if (checked) listener.onCheckAnswer(position, "B")
            R.id.C -> if (checked) listener.onCheckAnswer(position, "C")
        }
    }
}