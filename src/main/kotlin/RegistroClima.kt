package org.example

data class RegistroClima(
    val ciudad: String,
    val temperatura: Double,
    val humedad : Int,
    val viento: double
){
    val descripcion: String
        get() = when(){
        temperatura < 0  -> "Polar"
        temperatura < 10 -> "Frio"
        temperatura < 20 -> "Templado"
        temperatura < 30 -> "Calido"
        else -> "Caluroso"

    }
}


