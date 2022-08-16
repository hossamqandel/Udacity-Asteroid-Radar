package com.android.udacitynasa.feature_asteroid_details.presentation.asteroid_details

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.udacitynasa.R
import com.android.udacitynasa.databinding.FragmentAsteroidDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AsteroidDetailsFragment : Fragment() {

    private var _binding: FragmentAsteroidDetailsBinding? = null
    private val binding get() = _binding!!
    private var id: Long = -1
    private var closeApproachDate: String = ""
    private var absoluteMagnitude: Float = -1.0F
    private var estimatedDiameter: Float = -1.0F
    private var relativeVelocity: Float = -1.0F
    private var distanceFromEarth: Float = -1.0F
    private var isPotentiallyHazardous: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        receiveArguments()
        setHasOptionsMenu(true)
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentAsteroidDetailsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        onClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
    }

    private fun onClick(){
        binding.apply {
            btnHelper.setOnClickListener {
                displayAstronomicalUnitExplanationDialog()
            }
        }
    }

    private fun initViews(){
        binding.apply {
            if (isPotentiallyHazardous!!){
                ivAsteroid.setBackgroundResource(R.drawable.asteroid_hazardous)
                ivAsteroid.contentDescription = getString(R.string.potentially_hazardous)
            } else {
                ivAsteroid.setBackgroundResource(R.drawable.asteroid_safe)
                ivAsteroid.contentDescription = getString(R.string.not_hazardous)
            }
            tvClosedApproachDate.text = closeApproachDate
            tvAbsolutemagnitude.text = absoluteMagnitude.toString()
            tvEstimateddiameter.text = estimatedDiameter.toString()
            tvRelativevelocity.text = relativeVelocity.toString()
            tvDistancefromEarth.text = distanceFromEarth.toString()
        }
    }

    private fun receiveArguments(){
        id = AsteroidDetailsFragmentArgs.fromBundle(requireArguments()).id
        closeApproachDate = AsteroidDetailsFragmentArgs.fromBundle(requireArguments()).closeApproachDate
        absoluteMagnitude = AsteroidDetailsFragmentArgs.fromBundle(requireArguments()).absoluteMagnitude
        estimatedDiameter = AsteroidDetailsFragmentArgs.fromBundle(requireArguments()).estimatedDiameter
        relativeVelocity = AsteroidDetailsFragmentArgs.fromBundle(requireArguments()).relativeVelocity
        distanceFromEarth = AsteroidDetailsFragmentArgs.fromBundle(requireArguments()).distanceFromEarth
        isPotentiallyHazardous = AsteroidDetailsFragmentArgs.fromBundle(requireArguments()).isPotentiallyHazardous
    }


    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(requireActivity())
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
             builder
            .create()
            .show()
    }
}