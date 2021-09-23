package com.android.unscramblegame.ui.game

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _count = MutableLiveData<Int>()
    val count: LiveData<Int> get() = _count

    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _currentWord = MutableLiveData<String>()
//    val currentWord: LiveData<String> get() = Transformations.map(_currentWord) {
//        var temp = it.toCharArray()
//        while (String(temp!!) == it)
//            temp.shuffle()
//
//        String(temp)
//    }
    val currentWord: LiveData<Spannable> get() = Transformations.map(_currentWord) {
        var temp = it.toCharArray()
        while (String(temp!!) == it)
            temp.shuffle()

        val currString = String(temp)

        run {
            val spannable: Spannable = SpannableString(currString)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(currString).build(),
                0,
                currString.length,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }

    private var wordsList = mutableListOf<String>()

    init {
        reset()
    }

    fun increaseCount(){ _count.value = _count.value?.plus(1) }

    fun increaseScore(){ _score.value = _score.value?.plus(SCORE_INCREASE) }

//    fun getUnscrambledWord(): String{
//        getNextWord()
//        var temp = _currentWord.value?.toCharArray()
//        while ( String(temp!!) == _currentWord.value )
//            temp.shuffle()
//
//        return String(temp)
//    }

    fun isCorrect(ans : String): Boolean{
        for ( word in allWordsList ){
            if ( word == ans ) return true
        }
        return false
    }

    /* private */ fun getNextWord(){
        if (_count.value?.equals(MAX_NO_OF_WORDS + 1) == true){
            return
        }
        val word = allWordsList.random()
        _currentWord.value = word
        if ( !(wordsList.contains(word)) ){
            wordsList.add(word)
            return
        }
        getNextWord()
    }

    fun reset() {
        _score.value = 0
        _count.value = 1
        wordsList.clear()
        getNextWord()
    }

    fun nextWord(): Boolean {
        if (_count.value!! >= MAX_NO_OF_WORDS){
            return false
        }
        return true
    }

}