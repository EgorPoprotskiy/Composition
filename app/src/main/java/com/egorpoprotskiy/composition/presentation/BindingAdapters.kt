package com.egorpoprotskiy.composition.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.egorpoprotskiy.composition.domain.entity.GameResult
import com.egorpoprotskiy.composition.R

@BindingAdapter("requiredAnswers")
fun bindRequiredAnswers(textView: TextView, count: Int) {
    textView.text = String.format(textView.context.getString(R.string.required_score), count)
}

@BindingAdapter("score")
fun bindScoreAnswers(textView: TextView, count: Int) {
    textView.text = String.format(textView.context.getString(R.string.score_answer), count)
}

@BindingAdapter("requiredPercentage")
fun bindRequiredPercentage(textView: TextView, percantage: Int) {
    textView.text = String.format(textView.context.getString(R.string.reauired_percentage),
        percantage)
}

@BindingAdapter("scorePercentage")
fun bindScorePercentage(textView: TextView, gameResult: GameResult){
    textView.text = String.format(textView.context.getString(R.string.score_percentage), getPercentOfRightAnswers(gameResult))
}

private fun getPercentOfRightAnswers(gameResult: GameResult) = with(gameResult) {
    if (countOfQuestions == 0) {
        0
    } else {
        ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }
}

@BindingAdapter("resultEmoji")
fun bindResultEmoji(imageView: ImageView, winner: Boolean){
    imageView.setImageResource(getSmileResId(winner))
}


private fun getSmileResId(winner: Boolean): Int {
    return if (winner) {
        R.drawable.smile
    } else {
        R.drawable.smile_sad
    }
}
