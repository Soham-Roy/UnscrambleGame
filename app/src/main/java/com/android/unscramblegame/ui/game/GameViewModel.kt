package com.android.unscramblegame.ui.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _count = MutableLiveData<Int>()
    val count: LiveData<Int> get() = _count

    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int> get() = _score

    private val _currentWord = MutableLiveData<String>()
    val currentWord: LiveData<String> get() = _currentWord

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

}