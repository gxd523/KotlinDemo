package com.demo.kotlin.sp

import android.content.Context
import android.content.SharedPreferences
import com.demo.kotlin.MyApplication
import kotlin.reflect.KProperty

/**
 * Created by guoxiaodong on 2020/9/27 21:05
 */
class SharedPreferencesDelegate<T>(
    private val sharedPreferences: SharedPreferences,
    private val editor: SharedPreferences.Editor,
    private val default: T,
) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return with(sharedPreferences) {
            val result = when (default) {
                is Boolean -> getBoolean(property.name, default)
                is Int -> getInt(property.name, default)
                is Long -> getLong(property.name, default)
                is String -> getString(property.name, default)
                is Float -> getFloat(property.name, default)
                else -> throw IllegalArgumentException("error type of defaultValue")
            }
            result as T
        }
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        editor.apply {
            when (value) {
                is Boolean -> putBoolean(property.name, value)
                is Int -> putInt(property.name, value)
                is Long -> putLong(property.name, value)
                is String -> putString(property.name, value)
                is Float -> putFloat(property.name, value)
                else -> throw IllegalArgumentException("error type of value")
            }.apply()
        }
    }
}

abstract class AbsSp(private val context: Context) {
    private val sharedPreferences by lazy {
        context.applicationContext.getSharedPreferences(this::class.simpleName!!, Context.MODE_PRIVATE)
    }

    private val editor by lazy {
        sharedPreferences.edit()
    }

    fun <T> default(default: T): SharedPreferencesDelegate<T> {
        return SharedPreferencesDelegate(sharedPreferences, editor, default)
    }
}


object DefaultSp : AbsSp(MyApplication.instance!!)
object GlobalSp : AbsSp(MyApplication.instance!!)