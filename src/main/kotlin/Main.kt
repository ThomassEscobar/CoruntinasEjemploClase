package org.example

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() = runBlocking {
    println("***** Monitor de clima de ciudades ************")

    //Datos de prueba
    val ciudades = listOf(
        "Santiago",
        "Valparaiso",
        "",
        "Concepcion",
        "Antofagasta",
        "Aa",
        "Puerto Montt"
    )
    val registrosExitosos = mutableListOf<RegistroClima>()
    println("Consultando ${ciudades.size} ciudades de forma simultanea...")
    val jobs = ciudades.map { ciudad ->
        launch {
            val etiqueta = ciudad.ifBlank { "Vacio" }
            val estado = procesarCiudad(ciudad)
            println("Iniciando consulta de: ${etiqueta}")
            //validar que estado devolvio la consulta
            when(estado){
                is EstadoConsulta.Consultando -> println("$etiqueta esta consultando...")
                is EstadoConsulta.Exitoso -> {
                    val r = estado.registro
                    println("${r.ciudad}: ${r.temperatura} °C" +
                        "${r.humedad}%, ${r.viento} km/h" +
                        "----- ${r.descripcion}")
                    registrosExitosos.add(r)
                }
                is EstadoConsulta.Error -> println("Error en $etiqueta: ${estado.mensaje}")
            }
        }
    }
    //esperar a que todos los jobs terminen
    //para poder seguir con el codigo que esta debajo
    jobs.forEach { it.join() }
    //llamar a la funcionn que ejecuta el analisis
    generarAnalisis(registrosExitosos, ciudades.size)

    println("*******************************************")

}