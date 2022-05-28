package com.example.pokdex.models


data class PokemonListModel(var results : MutableList<PokemonModel>)
{

    fun attachIdsToPokemon()
    {
        var id = 1

        for (pokemon in results)
        {
            pokemon.id = id
            // Normal pokemon end at 898, and mega pokemon ids start
            if(id == 898)
            {
                id = 10000
            }
            id++

        }

    }
    fun sortPokemon(sortBy : String)
    {
        if(sortBy == "Alphabetically")
        {
            results =  results.sortedBy { it.name }.toMutableList()
        }
        else{

        }
    }
}
