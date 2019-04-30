package com.erhodes.arsmagica.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.erhodes.arsmagica.ArsMagicaApplication
import com.erhodes.arsmagica.R
import com.erhodes.arsmagica.model.*
import javax.inject.Inject

class SpellFragment : BaseFragment() {

    @Inject lateinit var characterRepository: CharacterRepository
    private lateinit var character: Character
    private lateinit var spell: FormulaicSpell
    private var resultView: TextView? = null
    private var castButton: Button? = null
    private var castPenalty: Int = 0

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
        val view = inflater.inflate(R.layout.fragment_spell, container, false)

        val nameView = view?.findViewById<TextView>(R.id.titleView)
        nameView?.text = spell.name

        val descriptionView = view?.findViewById<TextView>(R.id.descriptionView)
        descriptionView?.text = spell.description

        val castSummaryView = view?.findViewById<TextView>(R.id.summaryView)
        castSummaryView?.text = "bla bla bla"
//        castSummaryView?.text = getString(R.string.spell_total_summary, getString(spell.technique.resourceId),
//                character.getStatScore(spell.technique),
//                getString(spell.form.resourceId),
//                character.getStatScore(spell.form),
//                character.getStatScore(StatEnum.STAMINA))

        val fastcastBox: CheckBox? = view?.findViewById(R.id.checkBox)
        fastcastBox?.setOnCheckedChangeListener {
            _, isChecked ->
            if (isChecked) {
                castPenalty = 10
            } else {
                castPenalty = 0
            }
            updateCastButton()
        }

        resultView = view?.findViewById(R.id.resultView)
        castButton = view?.findViewById<Button>(R.id.castButton)
        castButton?.setOnClickListener(View.OnClickListener {
            castSpell()
        })
        updateCastButton()

        return view;
    }

    private fun updateCastButton() {
        castButton?.text = getString(R.string.cast_button, spell.getCastingValue(character) - castPenalty)
    }

    private fun castSpell() {
        val castResult = characterRepository.castSpell(character, spell, castPenalty)
        var resultString: String = ""
        when(castResult.result) {
            0 -> resultString = getString(R.string.cast_success, castResult.roll, castResult.total, castResult.penetration)
            1 -> resultString = getString(R.string.cast_partial_success, castResult.total, castResult.roll)
            2 -> resultString = getString(R.string.cast_failure, castResult.roll)
        }
        resultView?.text = resultString
    }
}