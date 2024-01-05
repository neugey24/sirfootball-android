package com.sirfootball.android.ui.draft

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sirfootball.android.databinding.FragmentDraftBinding

class DraftFragment : Fragment() {

    private var _binding: FragmentDraftBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val draftViewModel =
                ViewModelProvider(this).get(DraftViewModel::class.java)

        _binding = FragmentDraftBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDraft
        draftViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}