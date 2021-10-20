package com.example.pokdex

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.net.toUri
import java.io.IOException
import java.net.URL

object Utility
{
    fun capitalizeFirstCharacter(string : String) : String
    {
        if(string.length > 1)
        {
            return Character.toUpperCase(string[0]) + string.substring(1)
        }

        return string
    }

}
