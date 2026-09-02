package org.example

//Define los posibles valores que se pueden usar
//Al ser sellada, el compilador reconoce todos sus subtipos
//y exige a los bloques when que ls manejen tranajar con todos y no incluir el else


sealed class EstadoConsulta {
    object Consultando: EstadoConsulta() //singleton
    data class Exitoso(val registro: RegistroClima): EstadoConsulta()
    data class Error(val mensaje: String): EstadoConsulta()





}