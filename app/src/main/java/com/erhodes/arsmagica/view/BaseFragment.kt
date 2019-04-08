package com.erhodes.arsmagica.view

import android.content.Context
import androidx.fragment.app.Fragment
import com.erhodes.arsmagica.MainActivity
import java.lang.IllegalArgumentException

abstract class BaseFragment: Fragment() {
    protected lateinit var mainActivity: MainActivity

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainActivity = context
        } else {
            throw IllegalArgumentException("Must be attached to MainActivity")
        }
    }
}