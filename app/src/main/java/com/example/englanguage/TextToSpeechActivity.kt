package com.example.englanguage

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.englanguage.databinding.ActivityTextToSpeechBinding
import com.example.englanguage.model.vocabulary.Vocabulary
import com.example.englanguage.viewmodel.SignUpViewModel
import java.util.*

class TextToSpeechActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTextToSpeechBinding
    private var vocabulary: Vocabulary? = null
    private var mTTS: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_to_speech)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_text_to_speech)

        callMTTS()
        binding.mButtonSpeak.setOnClickListener {
            speak()
        }
    }

    private fun callMTTS() {
        mTTS = TextToSpeech(this, object : TextToSpeech.OnInitListener {
            override fun onInit(i: Int) {
                if (i == TextToSpeech.SUCCESS) {
                    val result = mTTS!!.setLanguage(Locale.ENGLISH)
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
        var text: String? = binding.edtTextToSpeech.text.toString().trim()
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