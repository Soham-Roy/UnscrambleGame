package com.android.unscramblegame.ui.game

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _count = 0
    val count get() = _count

    private var _score: Int = 0
    val score: Int get() = _score

    private lateinit var _currentWord: String

    private var wordsList = mutableListOf<String>()

    fun increaseCount(){
        _count += 1;
    }

    fun increaseScore(){
        _score += SCORE_INCREASE
    }

    fun getUnscrambledWord(): String{
        getNextWord()
        var temp = _currentWord.toCharArray()
        while ( String(temp) == _currentWord )
            temp.shuffle()

        return String(temp)
    }

    fun isCorrect(ans : String): Boolean{
        for ( word in allWordsList ){
            if ( word == ans ) return true
        }
        return false
    }

    private fun getNextWord(){
        val word = allWordsList.random()
        _currentWord = word
        if ( !(wordsList.contains(word)) ){
            wordsList.add(word)
            return
        }
        getNextWord()
    }

    fun reset() {
        _score = 0
        _count = 0
        wordsList.clear()
    }

}