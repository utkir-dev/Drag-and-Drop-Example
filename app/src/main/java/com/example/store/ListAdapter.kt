package com.example.store

val list =
    listOf(
        "Rossiya",
        "Aqsh",
        "Germaniya",
        "Shveciya",
        "Franciya",
        "Polsha",
        "Italiya",
        "Xitoy",
        "Korea",
        "Vetnam"
    )
val map=HashMap<String,ArrayList<String>>()

fun fillMap(){
    for (key in 1..10){
        map.put(key.toString(),list.map { it+key } as ArrayList)
    }
}