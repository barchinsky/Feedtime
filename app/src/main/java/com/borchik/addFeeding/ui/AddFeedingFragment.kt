package com.borchik.addFeeding.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import com.borchik.feedtime.databinding.FragmentAddFeedingBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.Calendar
import java.util.Date

class AddFeedingFragment : Fragment() {

    private var binding: FragmentAddFeedingBinding? = null

    private val viewModel by viewModel<AddFeedingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddFeedingBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddFeedingFragment.viewModel
        }

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select feeding date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setTitleText("Select feeding time")
            .setHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
            .setMinute(Calendar.getInstance().get(Calendar.MINUTE))
            .build()

        viewModel.openDatePicker.observe(viewLifecycleOwner) {
            Timber.i("Open date picker")
            datePicker.apply {
                addOnPositiveButtonClickListener {
                    Timber.i("Date: ${Date(it)}")
                    viewModel.onDateSelected(it)
                }
                show(this@AddFeedingFragment.parentFragmentManager, "datePicker")
            }
        }

        viewModel.openTimePicker.observe(viewLifecycleOwner) {
            Timber.i("Open time picker")
            timePicker.apply {
                addOnPositiveButtonClickListener {
                    Timber.i("Time: ${timePicker.hour}:${timePicker.minute}")
                    viewModel.onTimeSelected(timePicker.hour, timePicker.minute)
                }
                show(this@AddFeedingFragment.parentFragmentManager, "timePicker")
            }
        }

        viewModel.navigateUp.observe(viewLifecycleOwner) {
            setFragmentResult(ADD_FEEDING_REQUEST_KEY, bundleOf())
            findNavController().navigateUp()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }

    companion object {

        const val ADD_FEEDING_REQUEST_KEY = "add_feeding"
    }
}