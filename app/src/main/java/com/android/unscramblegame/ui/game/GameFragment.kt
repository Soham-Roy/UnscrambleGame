package com.android.unscramblegame.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        bind = GameFragmentBinding.inflate(layoutInflater)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateUI()
        bind.skip.setOnClickListener { onSkip() }
        bind.submit.setOnClickListener { onSubmit() }
    }

    private fun onSkip(){
        viewModel.increaseCount()
        setErrorTextField(false)
        updateUI()
    }

    private fun onSubmit() {
        val ans: String = bind.textField.editText?.text.toString()
        if ( viewModel.isCorrect(ans) ){
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

    private fun updateUI() {
        if ( viewModel.count > MAX_NO_OF_WORDS ) {
            showFinalDialog()
            return
        }
        bind.textViewUnscrambledWord.text = viewModel.getUnscrambledWord()
        bind.textField.editText?.text?.clear()
    }

    private fun showFinalDialog(){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle( getString(R.string.congratulations) )
            .setMessage( getString(R.string.you_scored) )
            .setNegativeButton( getString(R.string.exit) ){ _, _ -> activity?.finish() }
            .setPositiveButton( getString(R.string.play_again) ){ _, _ ->
                restartGame()
            }
            .show()
    }

    private fun restartGame(){
        viewModel.reset()
        setErrorTextField(false)
        updateUI()
    }

}