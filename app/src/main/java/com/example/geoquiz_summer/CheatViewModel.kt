package com.example.geoquiz_summer


import androidx.lifecycle.ViewModel
import java.sql.Types.NULL

class CheatViewModel : ViewModel() {
     var answerText = 0
     var isAnswerTrue = false
     var answerIsTrue = false

     fun showAnswer(b: Boolean){
          answerIsTrue = b

          answerText = when{
               answerIsTrue -> R.string.true_button
               else -> R.string.false_button
          }

          if (answerIsTrue){
               isAnswerTrue = true
          }
     }
}