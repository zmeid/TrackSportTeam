package com.zmeid.tracksportteam.util

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * It trims the value whenever a new value is set to String.
 */
class StringTrimDelegate : ReadWriteProperty<Any?, String> {

    private var trimmedValue: String = ""

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        trimmedValue = value.trim()
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return trimmedValue
    }
}