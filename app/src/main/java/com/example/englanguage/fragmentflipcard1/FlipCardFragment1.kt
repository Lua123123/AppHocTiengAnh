package com.example.englanguage.fragmentflipcard1

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.englanguage.R
import com.example.englanguage.databinding.FragmentFlipCard1Binding
import com.example.englanguage.viewmodel.FlipViewModel
import com.example.englanguage.model.vocabulary.Vocabulary
import com.example.englanguage.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class FlipCardFragment1() : Fragment() {
    private lateinit var front_anim: AnimatorSet
    private lateinit var behind_anim: AnimatorSet
    private var isFront = true;
    private lateinit var binding: FragmentFlipCard1Binding
    private var vocabulary: Vocabulary? = null
    private var mTTS: TextToSpeech? = null

    private val flipViewModel: FlipViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flip_card1, container, false)
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

        flipViewModel.mutableLiveDataClickGetVocabulary()

        flipViewModel.mVocabulary.observe(this)
        {
            binding.cartFront!!.text = it!!.success.get(0).word
            binding.cartBehind!!.text = it!!.success.get(0).mean
        }
    }

    private fun flipCart() {
        val scale: Float? = activity?.resources?.displayMetrics?.density
        //1
        binding.cartFront?.cameraDistance = 8000 * scale!!
        binding.cartBehind?.cameraDistance = 8000 * scale

        front_anim = AnimatorInflater.loadAnimator(
            activity,
            R.animator.front_animator
        ) as AnimatorSet
        behind_anim = AnimatorInflater.loadAnimator(
            activity,
            R.animator.behind_animation
        ) as AnimatorSet

        binding.flipLayout?.setOnClickListener {
            if (isFront) {
                front_anim.setTarget(binding.cartFront)
                behind_anim.setTarget(binding.cartBehind)
                front_anim.start()
                behind_anim.start()
                isFront = false
            } else {
                front_anim.setTarget(binding.cartBehind)
                behind_anim.setTarget(binding.cartFront)
                behind_anim.start()
                front_anim.start()
                isFront = true
            }
        }
    }

    private fun callMTTS() {
        mTTS = TextToSpeech(context, object : OnInitListener {
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
        var text: String? = flipViewModel.mVocabulary.value?.success?.get(0)?.word
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

    override fun onDestroy() {
        if (mTTS != null) {
            mTTS?.stop()
            mTTS?.shutdown()
        }
        super.onDestroy()
    }
}