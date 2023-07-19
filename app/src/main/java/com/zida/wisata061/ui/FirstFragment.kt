package com.zida.wisata061.ui



import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zida.wisata061.application.TourApp
import com.zida.wisata061.databinding.FragmentFirstBinding


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    private val binding get() = _binding!!
    private lateinit var applicationContext: Context
    private val tourViewModel: TourViewModel by viewModels {
        TourViewModelFactory((applicationContext as TourApp).repository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        applicationContext = requireContext().applicationContext
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TourListAdapter{tour->
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(tour)
            findNavController().navigate(action)
        }

        binding.DataRecyclerView.adapter = adapter
        binding.DataRecyclerView.layoutManager = LinearLayoutManager(context)
        tourViewModel.AllTours.observe(viewLifecycleOwner){tours ->
            tours.let {
                if (tours.isEmpty()){
                    binding.emptyTextView.visibility = View.VISIBLE
                    binding.Ilustrationimageview.visibility = View.VISIBLE
                }else {
                    binding.emptyTextView.visibility = View.GONE
                    binding.Ilustrationimageview.visibility = View.GONE
                }

                adapter.submitList(tours)
            }

        }

        binding.AddFAB.setOnClickListener {

            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(null)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}