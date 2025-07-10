package com.aristidevs.infovotouns.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aristidevs.infovotouns.R

/**
 * A simple [Fragment] subclass.
 * Use the [ProposalsFieldsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProposalsFieldsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_proposals_fields, container, false)
    }

}