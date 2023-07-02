package com.example.store.example_compose

import androidx.annotation.DrawableRes
import com.example.store.R

data class FoodItem(
    val id: Int,
    val name: String = "",
    val price: Double = 0.0,
    @DrawableRes val image: Int = 0
)


data class Person(val id: Int, val name: String, @DrawableRes val profile: Int)

val persons = listOf(
    Person(1, "Jhone-1", R.drawable.user_one),
    Person(2, "Eyle-2", R.drawable.user_two),
    Person(3, "Tommy-3", R.drawable.user_three),
    Person(4, "Jhone-4", R.drawable.user_one),
    Person(5, "Eyle-5", R.drawable.user_two),
    Person(6, "Tommy-6", R.drawable.user_three),
    Person(7, "Jhone-7", R.drawable.user_one),
    Person(8, "Eyle-8", R.drawable.user_two),
    Person(9, "Tom-9", R.drawable.user_three),
    Person(10, "Jhon-10", R.drawable.user_one),
    Person(11, "Eyle-11", R.drawable.user_two),
    Person(12, "Tom-12", R.drawable.user_three),
    Person(13, "Jhone-13", R.drawable.user_one),
    Person(14, "Eyle-14", R.drawable.user_two),
    Person(15, "Tommy-15", R.drawable.user_three),
    Person(16, "Jhone-16", R.drawable.user_one),
    Person(17, "Eyle-17", R.drawable.user_two),
    Person(18, "Eyle-18", R.drawable.user_two),
    Person(19, "Tom-19", R.drawable.user_three),
    Person(20, "Jhon-20", R.drawable.user_one),
    Person(21, "Eyle-21", R.drawable.user_two),
    Person(22, "Tom-22", R.drawable.user_three)
)