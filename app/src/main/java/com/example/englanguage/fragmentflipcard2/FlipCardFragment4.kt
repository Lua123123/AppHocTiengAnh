package com.example.englanguage.fragmentflipcard2

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import com.example.englanguage.R
import com.example.englanguage.databinding.FragmentFlipCard4Binding
import com.example.englanguage.model.vocabulary.SuccessVocabulary
import com.example.englanguage.model.vocabulary.Vocabulary
import com.example.englanguage.network.API
import com.example.englanguage.viewmodel.FlipViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FlipCardFragment4 : Fragment() {
    lateinit var front_anim: AnimatorSet
    lateinit var behind_anim: AnimatorSet
    var isFront = true;
    private lateinit var binding: FragmentFlipCard4Binding
    var vocabulary: Vocabulary? = null
    private var mTTS: TextToSpeech? = null
    private val koinViewModel: FlipViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flip_card4, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        flipCart()
        callMTTS()

        binding.mButtonSpeak.setOnClickListener {
            speak()
        }

        koinViewModel.mutableLiveDataClickGetVocabulary()

        koinViewModel.mVocabulary.observe(this)
        {
            binding.cartFront2!!.text = it!!.success.get(3).word
            binding.cartBehind2!!.text = it!!.success.get(3).mean
        }
    }

    private fun callMTTS() {
        mTTS = TextToSpeech(context, object : TextToSpeech.OnInitListener {
            override fun onInit(i: Int) {
                if (i == TextToSpeech.SUCCESS) {
                    val result = mTTS!!.setLanguage(Locale.GERMAN)
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported")
                    } else {
                        binding.mButtonSpeak.setEnabled(true)
                    }
                    run { Log.e("TTS", "Initialization failed") }
                }
            }
        })
    }

    private fun speak() {
        var text: String? = koinViewModel.mVocabulary.value?.success?.get(3)?.word
        if (text != null) {
            Log.d("TTS", text)
        }
        var pitch = binding.seekBarPitch.progress.toFloat() / 50
        if (pitch < 0.1) pitch = 0.1F
        var speed: Float = binding.seekBarSpeed.progress.toFloat() / 50
        if (speed < 0.1) speed = 0.1F

        mTTS!!.setPitch(pitch) //pitch
        mTTS!!.setSpeechRate(speed) //speed

        mTTS!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
    }

    private fun flipCart() {
        val scale: Float? = activity?.resources?.displayMetrics?.density
        //2
        binding.cartFront2?.cameraDistance = 8000 * scale!!
        binding.cartBehind2?.cameraDistance = 8000 * scale

        front_anim = AnimatorInflater.loadAnimator(
            activity,
            R.animator.front_animator
        ) as AnimatorSet
        behind_anim = AnimatorInflater.loadAnimator(
            activity,
            R.animator.behind_animation
        ) as AnimatorSet

        //2
        binding.flipLayout2?.setOnClickListener {
            if (isFront) {
                front_anim.setTarget(binding.cartFront2)
                behind_anim.setTarget(binding.cartBehind2)
                front_anim.start()
                behind_anim.start()
                isFront = false
            } else {
                front_anim.setTarget(binding.cartBehind2)
                behind_anim.setTarget(binding.cartFront2)
                behind_anim.start()
                front_anim.start()
                isFront = true
            }
        }
    }
}