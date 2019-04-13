package com.erhodes.arsmagica.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.TextView
import com.erhodes.arsmagica.ArsMagicaApplication
import com.erhodes.arsmagica.R
import com.erhodes.arsmagica.model.*
import javax.inject.Inject

class SpontaneousFragment: BaseFragment(), RadioGroup.OnCheckedChangeListener {
    private val castAttempt: CastAttempt = CastAttempt()
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

        val radioGroup: RadioGroup = view.findViewById(R.id.techniqueGroup)
        radioGroup.setOnCheckedChangeListener(this)

        view.findViewById<RadioGroup>(R.id.formGroup).setOnCheckedChangeListener(this)
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

    override fun onCheckedChanged(p0: RadioGroup?, resId: Int) {
        when (resId) {
            R.id.creo -> castAttempt.technique = StatEnum.CREO
            R.id.intellego -> castAttempt.technique = StatEnum.INTELLEGO
            R.id.muto -> castAttempt.technique = StatEnum.MUTO
            R.id.perdo -> castAttempt.technique = StatEnum.PERDO
            R.id.rego -> castAttempt.technique = StatEnum.REGO

            R.id.animal -> castAttempt.form = StatEnum.ANIMAL
            R.id.auram -> castAttempt.form = StatEnum.AURAM
            R.id.aquam -> castAttempt.form = StatEnum.AQUAM
            R.id.corpus -> castAttempt.form = StatEnum.CORPUS
            R.id.herbam -> castAttempt.form = StatEnum.HERBAM
            R.id.ignem -> castAttempt.form = StatEnum.IGNEM
            R.id.imaginem -> castAttempt.form = StatEnum.IMAGINEM
            R.id.mentem -> castAttempt.form = StatEnum.MENTEM
            R.id.terram -> castAttempt.form = StatEnum.TERRAM
            R.id.vim -> castAttempt.form = StatEnum.VIM

            R.id.spontaneous -> castAttempt.mode = 0
            R.id.fatigued -> castAttempt.mode = 1
            R.id.diedne -> castAttempt.mode = 2
            R.id.normal -> castAttempt.mode = 3
        }
        updateCastTotal()
    }

    private fun updateCastTotal() {
        summaryView.text = getString(R.string.cast_button, castAttempt.getCastTotal(character))
    }

    private fun castSpell() {
        val roll = (0..9).shuffled().first()

        resultView.text = getString(R.string.spontaneous_success, roll+castAttempt.getCastTotal(character))
    }

    class CastAttempt() {
        public var technique: StatEnum = StatEnum.CREO
        public var form: StatEnum = StatEnum.ANIMAL

        var fastCast: Boolean = false
        var focused: Boolean = false

        var mode: Int = 0

        public fun getCastTotal(caster: Character): Int {
            var formScore = caster.getArtValue(form)
            var techniqueScore = caster.getArtValue(technique)

            if (mode == 2) {
                Log.d("Eric","diedne!" + formScore + " " + techniqueScore);
                if (formScore <= techniqueScore) {
                    formScore += formScore
                } else {
                    techniqueScore += techniqueScore
                }
            }
            var castingTotal: Int = caster.getCharacteristicValue(StatEnum.STAMINA) + formScore + techniqueScore

            when (mode) {
                0 -> castingTotal/=5
                1 -> castingTotal/=2
                2 -> castingTotal/=2
            }

            if (fastCast) {
                castingTotal-=10
            }

            return castingTotal
        }
    }
}