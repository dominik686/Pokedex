package com.example.pokdex.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Models.AllPokemonModel
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.ViewModel.DisplayAllPokemonViewModel
import kotlinx.android.synthetic.main.item_recyclerview_display_all.view.*


class DisplayAllPokemonFragment : Fragment()
{

   private lateinit var displayAllPokemonVM : DisplayAllPokemonViewModel
   private lateinit var allPokemon : AllPokemonModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getContext(): Context? {
        return super.getContext()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initializeVariables()
        displayRecyclerView()

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_all_pokemon, container, false)
    }

    //Initialize member variables
    private fun initializeVariables()
    {
        displayAllPokemonVM = DisplayAllPokemonViewModel()
        allPokemon = AllPokemonModel()

    }

    private fun displayRecyclerView()
    {
        displayAllPokemonVM.allPokemonModel.observe(viewLifecycleOwner, { newAllPokemon ->
            allPokemon = newAllPokemon

    })
}
    //Get an adapter of ALL pokemon
    //And then in the PokemonHolder download the rest of the data to display the pokemon on the screen?
}

class DisplayAllPokemonAdapter(allpokemon : AllPokemonModel) :
    RecyclerView.Adapter<PokemonHolder>()

{
    //List of the displayed Pokemon
    var allPokemon = allpokemon
    var pokemonList = allPokemon.results

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_display_all,
        parent, false)

        return PokemonHolder(view)

    }

    override fun onBindViewHolder(holder: PokemonHolder, position: Int) {
        holder.itemView.display_all_pokemon_name.text = pokemonList[position].name
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }

}

class PokemonHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{

}