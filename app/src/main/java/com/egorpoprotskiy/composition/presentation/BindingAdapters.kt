package com.egorpoprotskiy.composition.presentation

import android.content.Context
import android.content.res.ColorStateList
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.egorpoprotskiy.composition.domain.entity.GameResult
import com.egorpoprotskiy.composition.R

interface OnOptionClickListener {
    fun onOptionClick(option: Int)
}


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

@BindingAdapter("enoughCount")
fun bindEnoughCount(textView: TextView, enough: Boolean){
    textView.setTextColor(getColorByState(textView.context, enough))
}
private fun getColorByState(context: Context, goodState: Boolean): Int {
    val colorResId = if (goodState) {
        android.R.color.holo_green_light
    } else {
        android.R.color.holo_red_light
    }
    return ContextCompat.getColor(context, colorResId)
}

@BindingAdapter("enoughPercent")
fun bindEnoughPercent(progressBar: ProgressBar, enough: Boolean){
    val color = getColorByState(progressBar.context, enough)
    progressBar.progressTintList = ColorStateList.valueOf(color)
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textView: TextView, number: Int) {
    textView.text = number.toString()
}

@BindingAdapter("onOptionClickListener")
fun bindOnOptionCLickListener(textView: TextView, clickListener: OnOptionClickListener) {
    textView.setOnClickListener {
        clickListener.onOptionClick(textView.text.toString().toInt())
    }
}
