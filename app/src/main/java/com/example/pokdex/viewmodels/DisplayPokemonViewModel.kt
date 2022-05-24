package com.example.pokdex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.pokdex.models.EvolutionModel
import com.example.pokdex.models.PokemonModel
import com.example.pokdex.models.PokemonSpeciesModel
import com.example.pokdex.PokemonRepository
import kotlinx.coroutines.Dispatchers

class DisplayPokemonViewModel : ViewModel() {

   var pokemon : LiveData<PokemonModel> = MutableLiveData<PokemonModel>(PokemonModel())
   lateinit var evolutionChain : LiveData<EvolutionModel>
   lateinit var pokemonSpecies: LiveData<PokemonSpeciesModel>

   fun getPokemon(id : Int) : LiveData<PokemonModel> {
      pokemon = liveData(Dispatchers.IO)
      {
         val retrievedPokemon = PokemonRepository.getPokemonId(id)

         emit(retrievedPokemon)
      }
      return pokemon
   }

   fun getPokemon(name: String) : LiveData<PokemonModel>
   {
      pokemon = liveData(Dispatchers.IO)
      {
         val retrievedPokemon = PokemonRepository.getPokemonName(name)

         emit(retrievedPokemon)
      }
      return pokemon
   }
   fun getEvolutionChain(id : Int ) : LiveData<EvolutionModel>
   {
      evolutionChain = liveData(Dispatchers.IO)
      {
         val retrievedEvolutionChain =  PokemonRepository.getEvolutionChain(id)

         emit(retrievedEvolutionChain)
      }

      return evolutionChain
   }


fun getPokemonSpecies(id : Int) : LiveData<PokemonSpeciesModel>
{
   pokemonSpecies = liveData(Dispatchers.IO)
   {
      val retrievedPokemonSpecies = PokemonRepository.getPokemonSpecies(id)
      emit(retrievedPokemonSpecies)
   }
   return pokemonSpecies
}




}