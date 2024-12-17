package com.aaronrubidev.to_doapp.fragments.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aaronrubidev.to_doapp.R
import com.aaronrubidev.to_doapp.data.models.Priority
import com.aaronrubidev.to_doapp.data.models.ToDoData
import com.aaronrubidev.to_doapp.data.viewmodel.ToDoViewModel
import com.aaronrubidev.to_doapp.databinding.FragmentUpdateBinding
import com.aaronrubidev.to_doapp.fragments.SharedViewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var binding: FragmentUpdateBinding
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(layoutInflater, container, false)

        binding.etCurrentTitle.setText(args.currentItem.title)
        binding.etCurrentDescription.setText(args.currentItem.description)
        binding.currentPrioritiesSpinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
        binding.currentPrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.update_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.menu_save) {
                    updateItem()
                }
                return false
            }

            private fun updateItem() {
                val title = binding.etCurrentTitle.text.toString()
                val description = binding.etCurrentDescription.text.toString()
                val getPriority = binding.currentPrioritiesSpinner.selectedItem.toString()

                val validation = mSharedViewModel.verifyDataFromUser(title, description)
                if (validation) {
                    val updatedItem = ToDoData(
                        args.currentItem.id,
                        title,
                        mSharedViewModel.parsePriority(getPriority),
                        description
                    )
                    mToDoViewModel.updateData(updatedItem)
                    Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_SHORT).show()
                    // Navigate back
                    findNavController().navigate(R.id.action_updateFragment_to_listFragment)
                } else {
                    Toast.makeText(requireContext(), "Please, fill out all fields", Toast.LENGTH_SHORT).show()
                }
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


}