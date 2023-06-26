package com.example.geoquiz_summer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.geoquiz_summer.Question
import com.example.geoquiz_summer.R
import com.example.geoquiz_summer.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    //private lateinit var trueButton: Button
    //private lateinit var falseButton: Button

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    /*READ THROUGH CODE BEFORE NEXT CHAPTER*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        // Alternative to View Binding
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        // Alternative to View Binding
        // trueButton = findViewById(R.id.true_button)
        // falseButton = findViewById(R.id.false_button)

        binding.trueButton.setOnClickListener { view: View ->

            checkAnswer(true)
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false

            //Snackbar.make(view, R.string.correct_toast, Snackbar.LENGTH_SHORT).show()
            /*
            Toast.makeText(
                this,
                R.string.correct_toast,
                Toast.LENGTH_SHORT
            ).show()
            */
        }

        binding.falseButton.setOnClickListener { view : View ->

            checkAnswer(false)
            binding.falseButton.isEnabled = false
            binding.trueButton.isEnabled = false

            //Snackbar.make(view, R.string.incorrect_toast, Snackbar.LENGTH_SHORT).show()
            /*
            Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT
            ).show()             */
        }

        // Updates the question for the first question
        updateQuestion()

        // Binding on click listener for question Text View
        binding.questionText.setOnClickListener{
            quizViewModel.currentIndex = (quizViewModel.currentIndex + 1) % quizViewModel.questionBankSize
            updateQuestion()
        }

        // Binding on click listener for next button
        binding.nextButton.setOnClickListener {
            //currentIndex = (currentIndex + 1) % questionBank.size
            quizViewModel.moveToNext()
            updateQuestion()

            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }

        // Binding on click listener for previous button
        binding.prevButton.setOnClickListener{
            if(quizViewModel.currentIndex == 0){
                quizViewModel.currentIndex = quizViewModel.questionBankSize
            }
            quizViewModel.currentIndex = (quizViewModel.currentIndex - 1) % quizViewModel.questionBankSize
            quizViewModel.moveBack()

            updateQuestion()

            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }

    }

    // Overrides of various lifecycle states
    override fun onStart(){
        super.onStart()
        Log.d(TAG, "onStart() Called")
    }

    override fun onResume(){
        super.onResume()
        Log.d(TAG, "onResume() Called")
    }

    override fun onPause(){
        super.onPause()
        Log.d(TAG, "onPause() Called")
    }

    override fun onStop(){
        super.onStop()
        Log.d(TAG, "onStop Called")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.d(TAG, "onDestroy Called")
    }

    // Updates the question based on the current index
    private fun updateQuestion(){
        //val questionTextResId = questionBank[currentIndex].textResId
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionText.setText(questionTextResId)
    }

    // Checks the answer to see if its correct and matches model data
    private fun checkAnswer(userAnswer: Boolean) {
        //val correctAnswer = questionBank[currentIndex].answer
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer  == correctAnswer){
            quizViewModel.correctAnswers += 1
            R.string.correct_toast
        }
        else{
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        if(quizViewModel.currentIndex == 5){
            Toast.makeText(this, (quizViewModel.correctAnswers.toDouble() / quizViewModel.questionBankSize.toDouble()).toString() + "%", Toast.LENGTH_SHORT).show()
            quizViewModel.correctAnswers = 0
        }
    }
}