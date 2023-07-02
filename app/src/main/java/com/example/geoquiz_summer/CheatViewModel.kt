package com.example.geoquiz_summer


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import org.jetbrains.annotations.Nullable
import java.sql.Types.NULL


const val ANSWER_TEXT = "ANSWER_TEXT"
const val IS_ANSWER_TRUE = "IS_ANSWER_TRUE"
const val ANSWER_IS_TRUE = "ANSWER_IS_TRUE"

class CheatViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

     var answerWasClicked = false

     /*
     var answerText: Int
          get() = savedStateHandle.get(ANSWER_TEXT) ?: 0
          set(value) = savedStateHandle.set(ANSWER_TEXT, value)

     var isAnswerTrue: Boolean
          get() = savedStateHandle.get(IS_ANSWER_TRUE) ?: false
          set(value) = savedStateHandle.set(IS_ANSWER_TRUE, value)

     private var answerIsTrue: Boolean
          get() = savedStateHandle.get(ANSWER_IS_TRUE) ?: false
          set(value) = savedStateHandle.set(ANSWER_IS_TRUE, value)
      */

}