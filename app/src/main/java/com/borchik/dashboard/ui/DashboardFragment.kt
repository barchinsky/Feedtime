package com.borchik.dashboard.ui

import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import com.borchik.addFeeding.ui.AddFeedingFragment
import com.borchik.dashboard.adapter.FeedingAdapter
import com.borchik.dashboard.model.FeedingAction
import com.borchik.feedtime.databinding.DashboardBinding
import com.borchik.infrastructure.utils.SwipeToDeleteCallback
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment() {

    private lateinit var binding: DashboardBinding

    private val viewModel by viewModel<DashboardViewModel>()

    private val vibrator by inject<Vibrator>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DashboardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeToDeleteCallback = SwipeToDeleteCallback(viewModel, vibrator)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)

        val adapter = FeedingAdapter(binding.feedings)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@DashboardFragment.viewModel
            itemTouchHelper.attachToRecyclerView(feedings)

            feedings.adapter = adapter
        }

        viewModel.feedings.observe(viewLifecycleOwner) {
            adapter.addFeedings(it)
        }

        viewModel.vibrate.observe(viewLifecycleOwner) {
            vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.EFFECT_TICK))
        }

        viewModel.snackBarMessage.observe(viewLifecycleOwner) {
            val snackBar = Snackbar.make(
                view,
                it.resId,
                Snackbar.LENGTH_LONG
            )

            if (it == FeedingAction.REMOVED) {
                snackBar.setAction("Undo") { viewModel.undoDeletion() }
            }

            snackBar.show()
        }

        viewModel.direction.observe(viewLifecycleOwner) {
            findNavController().navigate(it)
        }

        setFragmentResultListener(AddFeedingFragment.ADD_FEEDING_REQUEST_KEY) { _, _ ->
            vibrator.vibrate(VibrationEffect.createOneShot(20, VibrationEffect.EFFECT_TICK))
            Snackbar.make(view, "Feeding added", Snackbar.LENGTH_LONG)
                .show()
            viewModel.onSwipeToRefresh()
        }
    }
}