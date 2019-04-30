package com.erhodes.arsmagica.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.erhodes.arsmagica.ArsMagicaApplication
import com.erhodes.arsmagica.R
import com.erhodes.arsmagica.model.*
import javax.inject.Inject

class SpontaneousFragment: BaseFragment(), RadioGroup.OnCheckedChangeListener, CompoundButton.OnCheckedChangeListener {
    private val spell = SpontaneousSpell(0, StatEnum.CREO, StatEnum.ANIMAL)
    private val castAttempt: CastAttempt = CastAttempt(spell)
    private lateinit var summaryView: TextView
    private lateinit var resultView: TextView
    @Inject lateinit var characterRepository: CharacterRepository
    private lateinit var character: Character

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ArsMagicaApplication.instance.appComponent.inject(this)

        character = characterRepository.mCharacter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_spontaneous, container, false)

        summaryView = view.findViewById(R.id.summaryView)
        resultView = view.findViewById(R.id.resultView)

//        val radioGroup: RadioGroup = view.findViewById(R.id.techniqueGroup)
//        radioGroup.setOnCheckedChangeListener(this)

        view.findViewById<CheckBox>(R.id.creo).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.intellego).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.muto).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.perdo).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.rego).setOnCheckedChangeListener(this)

        view.findViewById<CheckBox>(R.id.animal).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.aquam).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.auram).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.corpus).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.herbam).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.ignem).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.imaginem).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.mentem).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.terram).setOnCheckedChangeListener(this)
        view.findViewById<CheckBox>(R.id.vim).setOnCheckedChangeListener(this)

        view.findViewById<RadioGroup>(R.id.modeGroup).setOnCheckedChangeListener(this)

        val fastCastButton: CheckBox = view.findViewById(R.id.fastCastCheck)
        fastCastButton.setOnCheckedChangeListener { compoundButton, checked ->
            castAttempt.fastCast = checked
            updateCastTotal()
        }
        view.findViewById<Button>(R.id.castButton).setOnClickListener(View.OnClickListener {
            castSpell()
        })
        updateCastTotal()
        return view
    }

    override fun onCheckedChanged(button: CompoundButton?, checked: Boolean) {
        when (button?.id) {
            R.id.creo -> spell.toggleTechnique(StatEnum.CREO, checked)
            R.id.intellego -> spell.toggleTechnique(StatEnum.INTELLEGO, checked)
            R.id.muto -> spell.toggleTechnique(StatEnum.MUTO, checked)
            R.id.perdo -> spell.toggleTechnique(StatEnum.PERDO, checked)
            R.id.rego -> spell.toggleTechnique(StatEnum.REGO, checked)

            R.id.animal -> spell.toggleForm(StatEnum.ANIMAL, checked)
            R.id.auram -> spell.toggleForm(StatEnum.AURAM, checked)
            R.id.aquam -> spell.toggleForm(StatEnum.AQUAM, checked)
            R.id.corpus -> spell.toggleForm(StatEnum.CORPUS, checked)
            R.id.herbam -> spell.toggleForm(StatEnum.HERBAM, checked)
            R.id.ignem -> spell.toggleForm(StatEnum.IGNEM, checked)
            R.id.imaginem -> spell.toggleForm(StatEnum.IMAGINEM, checked)
            R.id.mentem -> spell.toggleForm(StatEnum.MENTEM, checked)
            R.id.terram -> spell.toggleForm(StatEnum.TERRAM, checked)
            R.id.vim -> spell.toggleForm(StatEnum.VIM, checked)
        }
        updateCastTotal()
    }

    override fun onCheckedChanged(p0: RadioGroup?, resId: Int) {
        when (resId) {
            R.id.spontaneous -> spell.mode = SpontaneousSpell.Mode.SPONTANEOUS
            R.id.fatigued -> spell.mode = SpontaneousSpell.Mode.FATIGUED
            R.id.diedne -> spell.mode = SpontaneousSpell.Mode.DIEDNE
            R.id.normal -> spell.mode = SpontaneousSpell.Mode.REGULAR
        }
        updateCastTotal()
    }

    private fun updateCastTotal() {
        summaryView.text = getString(R.string.cast_button, castAttempt.getCastTotal(character))
    }

    private fun castSpell() {
        val roll = castAttempt.cast(character)

        resultView.text = getString(R.string.spontaneous_success, roll)
    }
}