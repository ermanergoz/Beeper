package com.erman.beeper

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class BatteryOptimizationDialog : DialogFragment() {
    private lateinit var listener: BatteryDialogListener
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            builder.setMessage(getString(R.string.turn_off_battery_optimization))
                .setPositiveButton(R.string.ok) { _, _ ->
                    listener.navigateToSettings()
                }
                .setNegativeButton(R.string.cancel) { _, _ ->
                    dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = context as BatteryDialogListener
        } catch (err: ClassCastException) {
            throw ClassCastException(("$context must implement BatteryDialogListener"))
        }
    }

    interface BatteryDialogListener {
        fun navigateToSettings()
    }
}