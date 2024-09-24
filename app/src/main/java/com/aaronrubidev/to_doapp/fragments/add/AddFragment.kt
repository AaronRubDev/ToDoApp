package com.aaronrubidev.to_doapp.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aaronrubidev.to_doapp.R
import com.aaronrubidev.to_doapp.databinding.FragmentAddBinding
import com.aaronrubidev.to_doapp.databinding.FragmentListBinding

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)

        return binding.root
    }
}