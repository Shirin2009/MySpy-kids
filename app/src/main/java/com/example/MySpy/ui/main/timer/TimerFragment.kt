package com.example.MySpy.ui.main.timer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.MySpy.R
import com.example.MySpy.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_main_timer.*
import kotlinx.android.synthetic.main.activity_main_timer_settings.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TimerFragment : Fragment() {

    private val vm: TimerViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_main_timer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (vm.isPinSet()) {

            root_timer_settings.visibility = View.GONE

            otp_view.requestFocus()
            otp_view.setOtpCompletionListener { otp ->

                if (otp != null && vm.isPinCorrect(otp)) {
                    otp_view.visibility = View.GONE
                    otp_view.setText("")
                    root_timer_settings.visibility = View.VISIBLE

                    root_timer.cancelTimer()
                    hideKeyboard()

                    root_timer.setTitle("Change timer settings")

                } else {
                    Toast.makeText(activity, "Error $otp, is the wrong code", Toast.LENGTH_SHORT).show()
                }
            }

            root_timer.setTitle("Enter your pin code")
            root_timer.onDone = {
                (activity as MainActivity).resetExtraUi()
            }

            showKeyboard()
        } else {
            otp_view.visibility = View.GONE
            root_timer_settings.visibility = View.VISIBLE
            root_timer.cancelTimer()
            root_timer.setTitle("Change timer settings")
        }

        root_timer.onClose = {
            (activity as MainActivity).resetExtraUi()
        }
    }

    fun showKeyboard() {
        val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(otp_view, InputMethodManager.SHOW_FORCED)
    }

    fun hideKeyboard() {
        val manager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(otp_view.windowToken, 0)
    }
}
