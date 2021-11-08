package com.example.pokdex.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.pokdex.Models.PokemonModel
import com.example.pokdex.R
import com.example.pokdex.ViewModel.DisplayPokemonViewModel
import com.squareup.picasso.Picasso
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokdex.Adapter.AbilitiesAdapter
import com.example.pokdex.Adapter.DisplayEvolutionsAdapter
import com.example.pokdex.Models.EvolutionModel
import com.example.pokdex.Models.PokemonSpeciesModel
import com.example.pokdex.Utility
import com.synnapps.carouselview.ImageListener
import kotlinx.android.synthetic.main.fragment_display_pokemon.*


class DisplayPokemonFragment : Fragment() {

    private val args : DisplayPokemonFragmentArgs by navArgs()
    private lateinit var pokemon : PokemonModel
    private lateinit var displayPokemonVM : DisplayPokemonViewModel
    private lateinit var spritesUrls : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_pokemon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        initializeMemberVariables()
        //Display the pokemon chosen on the previous fragment
        getAndDisplayPokemon(args.id)

        super.onViewCreated(view, savedInstanceState)
    }
    // Initialize as many variables as possible
    private fun initializeMemberVariables()
    {
        displayPokemonVM = DisplayPokemonViewModel()
        pokemon = PokemonModel("")

    }

    private fun getPokemonSpecies(id : Int)
    {
        lateinit var pokemonSpecies  : PokemonSpeciesModel
        displayPokemonVM.getPokemonSpecies(id).observe(viewLifecycleOwner, {
            pokemonSpecies ->
            getAndDisplayEvolutions(pokemonSpecies.evolution_chain.getChainId())
        })

    }
    private fun getAndDisplayEvolutions(id : Int)
    {
        var c : EvolutionModel

        displayPokemonVM.getEvolutionChain(id).observe(viewLifecycleOwner, {
                evolutionChain ->
            c = evolutionChain
            displayEvolutions(c.getListOfPokemonNames())
        })
    }

    private fun displayEvolutions(listOfEvolutions: List<String>)
    {
        display_pokemon_evolutions_recyclerview.adapter = DisplayEvolutionsAdapter(listOfEvolutions, pokemon) {
            position -> onEvolutionsItemClick(position)
        }
        display_pokemon_evolutions_recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }

    //When clicking on a pokemon, go their specific pokemon fragment
    private fun onEvolutionsItemClick(position : Int)
    {
        val id : Int = position + 1
        val action =  DisplayAllPokemonFragmentDirections.actionDisplayAllPokemonFragmentToDisplayPokemonFragment(id)
        findNavController().navigate(action)
    }
    private fun getAndDisplayPokemon(id : Int)
    {
        animationView.playAnimation()
        displayPokemonVM.getPokemon(id).observe(viewLifecycleOwner, {
                newPokemon ->
            pokemon = newPokemon
            displayPokemon()
            getPokemonSpecies(id)

        })
    }
    private fun displayPokemon()
    {
        val url =  pokemon.getOfficialArtworkFrontDefault()


        if(url != "")
        {
            spritesUrls = pokemon.sprites?.getListOfUrls()!!
            loadCarousel()
            display_pokemon_name.text = Utility.capitalizeFirstCharacter(pokemon.name)
            display_pokemon_weight.text = "Weight: "+  pokemon.getWeightInKilograms() + " kg"
            display_pokemon_experience.text = "Base experience: " + pokemon.base_experience
            display_pokemon_height.text = "Height: " + pokemon.getHeightInCentimeters() + " cm"
            displayAbilitiesRecyclerView()
           // Toast.makeText()
        }
        animationView.visibility=View.GONE
    }

    fun loadCarousel()
    {
        carouselView.setImageListener(imageListener)
        carouselView.pageCount = spritesUrls.size
    }
    //Image listener for the carousel
    var imageListener: ImageListener = object : ImageListener {
        override fun setImageForPosition(position: Int, imageView: ImageView?) {
            if (imageView != null) {
                loadImageView(spritesUrls[position], imageView)
            }
        }
    }
    private fun loadImageView(url : String, imageView : ImageView)
    {
        Picasso.get().load(url).into(imageView)
    }

    private fun displayAbilitiesRecyclerView()
    {
        display_pokemon_ability_recyclerview.adapter = AbilitiesAdapter(pokemon.abilities)
        display_pokemon_ability_recyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


}