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
import com.erhodes.arsmagica.MainActivity
import com.erhodes.arsmagica.R
import com.erhodes.arsmagica.model.*
import java.lang.IllegalArgumentException
import javax.inject.Inject

class MagicFragment: BaseFragment() {

    @Inject lateinit var characterRepository: CharacterRepository
    private lateinit var character: Character

    private var artView: RecyclerView? = null
    private lateinit var artViewAdapter: CharacterFragment.StatAdapter
    private lateinit var artManager: RecyclerView.LayoutManager

    private var spellView: RecyclerView? = null
    private lateinit var spellViewAdapter: RecyclerView.Adapter<*>
    private lateinit var spellViewManager: RecyclerView.LayoutManager

    companion object {
        fun newInstance(): MagicFragment {
            return MagicFragment()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        ArsMagicaApplication.instance.appComponent.inject(this)

        character = characterRepository.getCharacter("Ferrus")
        characterRepository.characterLiveData.observe(this, Observer {
            if (it != null) {
                updateCharacter(it)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_magic, container, false)

        val spontaneousButton: Button = view.findViewById(R.id.button3)
        spontaneousButton.setOnClickListener {
            mainActivity.launchSpontaneousMagicFragment()
        }

        artManager = LinearLayoutManager(context)
        artViewAdapter = CharacterFragment.StatAdapter(character.getArts(), context, characterRepository)

        artView = view?.findViewById(R.id.artView)
        artView.apply {
            this?.setHasFixedSize(true)
            this?.layoutManager = artManager
            this?.adapter = artViewAdapter
        }

        spellViewManager = LinearLayoutManager(context)
        spellViewAdapter = SpellAdapter(character.spells) { pos ->
            mainActivity.launchSpellFragment(pos)
        }
        spellView = view?.findViewById(R.id.spellRecyclerView)
        spellView.apply {
            this?.setHasFixedSize(true)
            this?.layoutManager = spellViewManager
            this?.adapter = spellViewAdapter
        }

        return view;
    }

    private fun updateCharacter(character: Character) {
        this.character = character
        artViewAdapter.myDataset = character.getArts()
        artViewAdapter.notifyDataSetChanged()
    }

    class SpellViewHolder(view: View, val titleView: TextView, val scoreView: TextView) : RecyclerView.ViewHolder(view)

    class SpellAdapter(private val dataset: ArrayList<FormulaicSpell>, val listener: (Int) -> Unit) :
            RecyclerView.Adapter<SpellViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpellViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_spell, parent, false)

            val titleView = view.findViewById<TextView>(R.id.spellNameView)
            val scoreView = view.findViewById<TextView>(R.id.spellDescView);

            return SpellViewHolder(view, titleView, scoreView)
        }

        override fun onBindViewHolder(holder: SpellViewHolder, position: Int) {
            holder.titleView.text = dataset[position].name
            holder.scoreView.text = dataset[position].description
            holder.itemView.setOnClickListener {
                listener(position)
            }
        }

        override fun getItemCount() = dataset.size
    }
}