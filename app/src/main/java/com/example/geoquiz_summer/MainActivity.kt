package com.example.geoquiz_summer
import android.app.Activity
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.example.geoquiz_summer.Question
import com.example.geoquiz_summer.R
import com.example.geoquiz_summer.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result ->
        // Handle the result
        if (result.resultCode == Activity.RESULT_OK) {
            quizViewModel.isCheater =
                result.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        // True Button Listener
        binding.trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            binding.trueButton.isEnabled = false
            binding.falseButton.isEnabled = false

        }


        // False Button Listener
        binding.falseButton.setOnClickListener { view : View ->
            checkAnswer(false)
            binding.falseButton.isEnabled = false
            binding.trueButton.isEnabled = false

        }

        // Updates the question for the first question
        updateQuestion()

        // Example for different Android SDK Versions
        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            blurCheatButton()
        }
        */

        // Question Text View Listener
        binding.questionText.setOnClickListener{
            quizViewModel.currentIndex = (quizViewModel.currentIndex + 1) % quizViewModel.questionBankSize
            updateQuestion()
        }

        // Next Button Listener
        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()

            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true

            quizViewModel.isCheater = false
        }

        // Previous Button Listener
        binding.prevButton.setOnClickListener{
            if(quizViewModel.currentIndex == 0){
                quizViewModel.currentIndex = quizViewModel.questionBankSize
            }
            quizViewModel.currentIndex = (quizViewModel.currentIndex - 1) % quizViewModel.questionBankSize
            quizViewModel.moveBack()

            updateQuestion()

            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true

            quizViewModel.isCheater = false
        }

        // Cheat Button Listener
        binding.cheatButton.setOnClickListener{

            if(quizViewModel.numberOfCheats < 3) {
                quizViewModel.numberOfCheats++
            }

            // Starts a new activity
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            cheatLauncher.launch(intent)

            if(quizViewModel.numberOfCheats >= 3){
                binding.cheatButton.isEnabled = false
            }

            Toast.makeText(this, ("Number of Cheats Remaining: ${3 - quizViewModel.numberOfCheats}"), Toast.LENGTH_SHORT).show()
        }

        // whhyyyyyy doesnt this work???? Had to put it up top
        // but this should work :/
        if(quizViewModel.numberOfCheats >= 3){
            binding.cheatButton.isEnabled = false
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
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionText.setText(questionTextResId)
    }

    // Checks the answer to see if its correct and matches model data
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        if(quizViewModel.currentIndex == 5){
            Toast.makeText(this, (quizViewModel.correctAnswers.toDouble() / quizViewModel.questionBankSize.toDouble()).toString() + "%", Toast.LENGTH_SHORT).show()
            quizViewModel.correctAnswers = 0
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun blurCheatButton() {
        val effect = RenderEffect.createBlurEffect(
            10.0f,
            10.0f,
            Shader.TileMode.CLAMP
        )
        binding.cheatButton.setRenderEffect(effect)
    }
}