package com.alierdemalkoc.noteapp.view

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.alierdemalkoc.noteapp.R
import com.alierdemalkoc.noteapp.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var _binding : FragmentSplashBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addButton = activity?.findViewById<Button>(R.id.adButton)
        addButton!!.visibility = View.GONE
        binding.animationView.addAnimatorListener(object : AnimatorListener{
            override fun onAnimationEnd(p0: Animator) {
                findNavController().navigate(R.id.splashToNotes)
            }

            override fun onAnimationStart(p0: Animator) = Unit

            override fun onAnimationCancel(p0: Animator) = Unit

            override fun onAnimationRepeat(p0: Animator) = Unit
        })
    }
}