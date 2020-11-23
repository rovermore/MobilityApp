package com.example.mobilityapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.mobilityapp.R
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class IconCreator(private val hashMapOfColors: HashMap<Int, Int>, private val companyZoneId: Int, val context: Context) {

    fun getIcon(): BitmapDescriptor {
        val color = hashMapOfColors[companyZoneId]
        val icon: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_baseline_location_on_24)
        icon?.colorFilter = color?.let { LightingColorFilter(it, color) }
        val bitmap = icon?.let { drawableToBitmap(it) }
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap? {
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            if (drawable.bitmap != null) {
                return drawable.bitmap
            }
        }
        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            Bitmap.createBitmap(
                1,
                1,
                Bitmap.Config.ARGB_8888
            )
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }
}