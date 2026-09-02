package org.example

//Escribir toda la logica de negocios del sistema
//suspend me permite pausar y reanudar la funcion
fun consultarClima(ciudad: String): RegistroClima{
    if(ciudad.isBlank())
        throw IllegalArgumentException("El nombre de la ciudad no puede estar vacio")
    if(ciudad.length < 3)
        throw IllegalArgumentException("Nombre demasiado corto: $ciudad")


}