package com.android.unscramblegame.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.android.unscramblegame.R
import com.android.unscramblegame.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Fragment where the game is played, contains the game logic.
 */
class GameFragment : Fragment() {

    private lateinit var bind: GameFragmentBinding
    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = DataBindingUtil.inflate(inflater, R.layout.game_fragment, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bind.gameViewModel = viewModel
        bind.maxNoOfWords = MAX_NO_OF_WORDS
        bind.lifecycleOwner = viewLifecycleOwner

//        viewModel.currentWord.observe(viewLifecycleOwner, {
//            bind.textViewUnscrambledWord.text = it
//        })
//        viewModel.count.observe(viewLifecycleOwner, {
//            if ( it > MAX_NO_OF_WORDS ){
//                showFinalDialog()
//            }
//            else {
//                bind.wordCount.text = "$it of $MAX_NO_OF_WORDS words"
//            }
//        })
//        viewModel.score.observe(viewLifecycleOwner, {
//            bind.score.text = "SCORE: $it"
//        })

        bind.skip.setOnClickListener { onSkip() }
        bind.submit.setOnClickListener { onSubmit() }
    }

    private fun onSkip(){
        if ( !viewModel.nextWord() ){
            showFinalDialog()
            return
        }
        bind.textField.editText?.text?.clear()
        viewModel.increaseCount()
        setErrorTextField(false)

        viewModel.getNextWord()
//        updateUI()
    }

    private fun onSubmit() {
        val ans: String = bind.textField.editText?.text.toString()
        if ( viewModel.isCorrect(ans.lowercase()) ){
            viewModel.increaseScore()
            onSkip()
        }
        else {
            setErrorTextField(true)
        }
    }

    private fun setErrorTextField(error : Boolean){
        if ( error ) {
            bind.textField.isErrorEnabled = true
            bind.textField.error = getString(R.string.try_again)
        }
        else {
            bind.textField.isErrorEnabled = false
        }
    }

//    private fun updateUI() {
//        if ( viewModel.count > MAX_NO_OF_WORDS ) {
//            showFinalDialog()
//            return
//        }
//        bind.textViewUnscrambledWord.text = viewModel.getUnscrambledWord()
//        bind.textField.editText?.text?.clear()
//    }

    private fun showFinalDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle( getString(R.string.congratulations) )
            .setMessage( getString(R.string.you_scored, viewModel.score.value) )
            .setNegativeButton( getString(R.string.exit) ){ _, _ -> activity?.finish() }
            .setPositiveButton( getString(R.string.play_again) ){ _, _ ->
                restartGame()
            }
            .show()
    }

    private fun restartGame(){
        viewModel.reset()
        setErrorTextField(false)
//        updateUI()
    }

}