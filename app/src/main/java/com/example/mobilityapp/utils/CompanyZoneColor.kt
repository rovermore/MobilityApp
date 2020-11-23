package com.example.mobilityapp.utils

import android.graphics.Color
import java.util.*
import kotlin.collections.HashMap

class CompanyZoneColor(private val companyZoneIdList: List<Int>) {

    fun getHashMapOfColors(): HashMap<Int, Int> {
        val hashMapOfColors = hashMapOf<Int, Int>()
        for (companyZoneId in companyZoneIdList) {
            val rnd = Random()
            val color = Color.argb(
                255,
                rnd.nextInt(256),
                rnd.nextInt(256),
                rnd.nextInt(256)
            )
            hashMapOfColors[companyZoneId] = color
        }
        return hashMapOfColors
    }

}