package com.example.geoquiz_summer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.geoquiz_summer.Question
import com.example.geoquiz_summer.R
import com.example.geoquiz_summer.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    //private lateinit var trueButton: Button
    //private lateinit var falseButton: Button

    private lateinit var binding: ActivityMainBinding

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //trueButton = findViewById(R.id.true_button)
        //falseButton = findViewById(R.id.false_button)

        binding.trueButton.setOnClickListener { view: View ->

            checkAnswer(true)

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

            //Snackbar.make(view, R.string.incorrect_toast, Snackbar.LENGTH_SHORT).show()

            /*
            Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT
            ).show()             */
        }

        updateQuestion()

        binding.questionText.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.prevButton.setOnClickListener{
            if(currentIndex == 0){
                currentIndex = questionBank.size
            }
            currentIndex = (currentIndex - 1) % questionBank.size
            updateQuestion()
        }

    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionText.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val messageResId = if (userAnswer  == correctAnswer){
            R.string.correct_toast
        }
        else{
            R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}