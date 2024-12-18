package com.aaronrubidev.to_doapp.fragments.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aaronrubidev.to_doapp.R
import com.aaronrubidev.to_doapp.data.viewmodel.ToDoViewModel
import com.aaronrubidev.to_doapp.databinding.FragmentListBinding
import com.aaronrubidev.to_doapp.fragments.SharedViewModel

class ListFragment : Fragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: FragmentListBinding
    private val adapter: ListAdapter by lazy { ListAdapter() }
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater, container, false)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDataBaseEmpty(data)
            adapter.setData(data)
        })
        mSharedViewModel.emptyDataBase.observe(viewLifecycleOwner, Observer {
            showEmptyDataBaseViews(it)
        })

        binding.floatingActionBottom.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        binding.listLayout.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_updateFragment)
        }

        return binding.root
    }

    private fun showEmptyDataBaseViews(emptyDataBase: Boolean) {
        if (emptyDataBase) {
            binding.imageViewNoData.visibility = View.VISIBLE
            binding.textViewNoData.visibility = View.VISIBLE
        } else {
            binding.imageViewNoData.visibility = View.INVISIBLE
            binding.textViewNoData.visibility = View.INVISIBLE
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.list_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                if (menuItem.itemId == R.id.menu_delete_all) {
                    confirmRemoval()
                }
                return false
            }

            // Show Alert Dialog to confirm of all items from dataBase table
            private fun confirmRemoval() {
                val builder = AlertDialog.Builder(requireContext())
                builder.setPositiveButton("Yes") { _, _ ->
                    mToDoViewModel.deleteAll()
                    Toast.makeText(
                        requireContext(), "Successfully removed everything!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                builder.setNegativeButton("No") { _, _ -> }
                builder.setTitle("Delete Everything!")
                builder.setMessage("Are you sure you want to remove everything?")
                builder.create().show()
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

}