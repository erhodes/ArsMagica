package com.erhodes.arsmagica

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.erhodes.arsmagica.model.Spell
import com.erhodes.arsmagica.view.CharacterFragment
import com.erhodes.arsmagica.view.MagicFragment
import com.erhodes.arsmagica.view.SpellFragment

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.contentPanel, CharacterFragment()).commit()
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount >= 1) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
    public fun launchSpellFragment(spellId: Int) {
        addFragment(SpellFragment.newInstance(spellId))
    }

    public fun launchMagicFragment() {
        addFragment(MagicFragment.newInstance())
    }

    fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.contentPanel, fragment).addToBackStack(fragment.javaClass.name).commit()
    }

}
