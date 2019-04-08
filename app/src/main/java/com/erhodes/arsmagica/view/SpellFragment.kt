package com.erhodes.arsmagica.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.erhodes.arsmagica.ArsMagicaApplication
import com.erhodes.arsmagica.R
import com.erhodes.arsmagica.model.Character
import com.erhodes.arsmagica.model.CharacterRepository
import com.erhodes.arsmagica.model.Spell
import com.erhodes.arsmagica.model.StatEnum
import javax.inject.Inject

class SpellFragment : BaseFragment() {

    @Inject lateinit var characterRepository: CharacterRepository
    private lateinit var character: Character
    private lateinit var spell: Spell
    private var resultView: TextView? = null

    companion object {
        val ARG_SPELL: String = "arg_spell"

        fun newInstance(spellId: Int): SpellFragment {
            val fragment = SpellFragment()
            val args = Bundle()
            args.putInt(ARG_SPELL, spellId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ArsMagicaApplication.instance.appComponent.inject(this)

        character = characterRepository.mCharacter
        spell = character.spells[arguments!!.getInt(ARG_SPELL)]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_spell, container, false)

        val nameView = view?.findViewById<TextView>(R.id.titleView)
        nameView?.text = spell.name

        val descriptionView = view?.findViewById<TextView>(R.id.descriptionView)
        descriptionView?.text = spell.description

        val castSummaryView = view?.findViewById<TextView>(R.id.summaryView)
        castSummaryView?.text = getString(R.string.spell_total_summary, getString(spell.technique.resourceId),
                character.getArtValue(spell.technique),
                getString(spell.form.resourceId),
                character.getArtValue(spell.form),
                character.getCharacteristicValue(StatEnum.STAMINA))

        resultView = view?.findViewById(R.id.resultView)
        val castButton = view?.findViewById<Button>(R.id.castButton)
        castButton?.setOnClickListener(View.OnClickListener {
            castSpell()
        })

        return view;
    }

    private fun castSpell() {
        val castResult = characterRepository.castSpell(character, spell)
        var resultString: String = ""
        when(castResult.result) {
            0 -> resultString = getString(R.string.cast_success, castResult.roll, castResult.total, castResult.penetration)
            1 -> resultString = getString(R.string.cast_partial_success, castResult.total, castResult.roll)
            2 -> resultString = getString(R.string.cast_failure, castResult.roll)
        }
        resultView?.text = resultString
    }
}