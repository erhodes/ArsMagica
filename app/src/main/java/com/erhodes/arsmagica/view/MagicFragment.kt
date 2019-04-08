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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erhodes.arsmagica.ArsMagicaApplication
import com.erhodes.arsmagica.MainActivity
import com.erhodes.arsmagica.R
import com.erhodes.arsmagica.model.Art
import com.erhodes.arsmagica.model.Character
import com.erhodes.arsmagica.model.CharacterRepository
import com.erhodes.arsmagica.model.Spell
import java.lang.IllegalArgumentException
import javax.inject.Inject

class MagicFragment: BaseFragment() {

    @Inject lateinit var characterRepository: CharacterRepository
    private lateinit var character: Character

    private var artView: RecyclerView? = null
    private lateinit var artViewAdapter: RecyclerView.Adapter<*>
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

        character = characterRepository.mCharacter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_magic, container, false)

        artManager = LinearLayoutManager(context)
        artViewAdapter = CharacterFragment.StatAdapter(character.arts.values.toTypedArray(), context, characterRepository)

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

    class SpellViewHolder(view: View, val titleView: TextView, val scoreView: TextView) : RecyclerView.ViewHolder(view)

    class SpellAdapter(private val dataset: ArrayList<Spell>, val listener: (Int) -> Unit) :
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