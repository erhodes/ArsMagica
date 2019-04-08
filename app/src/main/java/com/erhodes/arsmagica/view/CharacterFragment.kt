package com.erhodes.arsmagica.view

import android.content.Context
import android.os.Bundle
import android.support.constraint.Group
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erhodes.arsmagica.ArsMagicaApplication
import com.erhodes.arsmagica.R
import com.erhodes.arsmagica.model.*
import javax.inject.Inject

class CharacterFragment : BaseFragment() {
    private lateinit var mCharacter: Character
    @Inject lateinit var characterRepository: CharacterRepository
    private var characteristicView: RecyclerView? = null
    private var abilityView: RecyclerView? = null

    private lateinit var characteristicViewAdapter: RecyclerView.Adapter<*>
    private lateinit var characteristicViewManager: RecyclerView.LayoutManager
    private lateinit var abilityViewAdapter: RecyclerView.Adapter<StatViewHolder>
    private lateinit var abilityViewManager: RecyclerView.LayoutManager

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ArsMagicaApplication.instance.appComponent.inject(this)

        mCharacter = characterRepository.getCharacter("Ferrus")
        characterRepository.characterLiveData.observe(this, Observer {
            characteristicViewAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_character, container, false);

        val magicButton: Button? = view?.findViewById<Button?>(R.id.magicButton)
        magicButton?.setOnClickListener(View.OnClickListener {
            mainActivity.launchMagicFragment()
        })

        val nameView = view?.findViewById<TextView>(R.id.charNameView)
        nameView?.text = mCharacter.name

        characteristicViewManager = LinearLayoutManager(context)
        val stuff = mCharacter.characteristics.values.toTypedArray()
        Log.d("Eric","characteristics " + stuff.size)

        characteristicViewAdapter = StatAdapter(mCharacter.characteristics.values.toTypedArray(), context, characterRepository)

        characteristicView = view?.findViewById(R.id.characteristicView)
        characteristicView.apply {
            this?.setHasFixedSize(true)
            this?.layoutManager = characteristicViewManager
            this?.adapter = characteristicViewAdapter
        }

        abilityViewAdapter = AbilityAdapter(mCharacter.abilities.values.toTypedArray(), context)
        abilityViewManager = LinearLayoutManager(context)

        abilityView = view?.findViewById(R.id.abilityView)
        abilityView.apply {
            this?.setHasFixedSize(true)
            this?.layoutManager = abilityViewManager
            this?.adapter = abilityViewAdapter
        }

        return view;
    }

    class StatViewHolder(view: View, val titleView: TextView, val scoreView: TextView, val minusButton: Button, val plusButton: Button, val buttonGroup: Group) : RecyclerView.ViewHolder(view)

    class StatAdapter(private val myDataset: Array<Stat>, private val context: Context?, private val charRepo: CharacterRepository) :
            RecyclerView.Adapter<StatViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_stat, parent, false)

            val titleView = view.findViewById<TextView>(R.id.title)
            val scoreView = view.findViewById<TextView>(R.id.score)
            val buttonGroup = view.findViewById<Group>(R.id.buttonGroup)
            val minusButton = view.findViewById<Button>(R.id.button)
            val plusButton = view.findViewById<Button>(R.id.button2)

            return StatViewHolder(view, titleView, scoreView, minusButton, plusButton, buttonGroup)
        }

        override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
            holder.titleView.text = context?.getString(myDataset[position].type.resourceId)
            holder.scoreView.text = myDataset[position].score.toString()

            holder.itemView.setOnClickListener {
                if (holder.buttonGroup.visibility == View.GONE) {
                    holder.buttonGroup.visibility = View.VISIBLE
                } else {
                    holder.buttonGroup.visibility = View.GONE
                }
            }

            holder.minusButton.setOnClickListener {
                charRepo.decreaseStat(myDataset[position])
            }

            holder.plusButton.setOnClickListener {
                charRepo.increaseStat(myDataset[position])
            }
        }

        override fun getItemCount() = myDataset.size
    }

    class AbilityAdapter(private val dataset: Array<Ability>, private val context: Context?) :
            RecyclerView.Adapter<StatViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_stat, parent, false)

            val titleView = view.findViewById<TextView>(R.id.title)
            val scoreView = view.findViewById<TextView>(R.id.score)
            val buttonGroup = view.findViewById<Group>(R.id.buttonGroup)
            val minusButton = view.findViewById<Button>(R.id.button)
            val plusButton = view.findViewById<Button>(R.id.button2)

            return StatViewHolder(view, titleView, scoreView, minusButton, plusButton, buttonGroup)
        }

        override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
            holder.titleView.text = context?.getString(dataset[position].type.resourceId)
            holder.scoreView.text = dataset[position].score.toString()
        }

        override fun getItemCount() = dataset.size
    }
}