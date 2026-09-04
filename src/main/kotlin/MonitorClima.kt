package org.example

//Escribir toda la logica de negocios del sistema
//suspend me permite pausar y reanudar la funcion
fun consultarClima(ciudad: String): RegistroClima{
    if(ciudad.isBlank())
        throw IllegalArgumentException("El nombre de la ciudad no puede estar vacio")
    if(ciudad.length < 3)
        throw IllegalArgumentException("Nombre demasiado corto: $ciudad")

    //Construir el proceso de generacion de datos del clima
    //generar de manera aleatoria la latencia simulada
    //nextlong; numeros enteros largos
    val latenciaMs = Random.nextLong(1_000, 3_000)

    //threads.sleep() -> no se puede usar porque bloquea el hilo completo
    delay(latenciaMs)

    //genera los datos aleatorios
    val temperatura = Random.nextDouble(-30.0, 45.0)
    val humedad = Random.nextInt(0, 100)
    val viento = Random.nextDouble(0.0, 130.0)
    return RegistroClima(ciudad, temperatura, humedad, viento)
}

suspend fun procesarCiudad(ciudad: String): EstadoConsulta{
    return try {
        val registro = consultarClima(ciudad)
        EstadoConsulta.Exitoso(registro)
    }catch (e: IllegalArgumentException){
        EstadoConsulta.Error("ciudad invalidad: ${e.message}")
    }catch (e: Exception){
        EstadoConsulta.Error("Error Inesperado: ${e.message}")
    }
}

fun generarAnalisis(val registros: List<RegistroClima>, totalCiudades: Int){
    println("========= Analisis De Resultados ========")
    //validar si la lista contiene informacion
    if(registros.isEmpty()){
        println("No hay registros para analizar")
        return
    }
    //podemos analizar la lista ya que tiene datos detro
    //mostrar las ciudades con temp mayor a 20 ordenadas de mayor a menor por su temp


    //filtrar solo las que cumplan la condicion
    val ciudadesCalidas = registros.filter{ it.temperatura > 20}.sortedByDescending { it.temperatura }
    println("Ciudades con temperatura mayor a 20°C:")
    if(ciudadesCalidas.isEmpty()) println("No hay ninguna ciudad que supera los 20°C ")
    else ciudadesCalidas.forEach{ println("${it.ciudad}: ${it.temperatura} C") }

    //mostrar la temperatura promedio

    val promedioTemp = registros.map { it.temperatura }.average()
    println("Promedio Temperatura: ${promedioTemp} °C")

    //la ciudad con mayor velocidad de viento
    registros.maxByOrNull { it.viento }?.left{
        println("Mayor Velocidad de viento ${it.ciudad}: ${it.viento} km/h")
    }

    val porClima = registros.groupBy{it.descripcion}
    println("Distribucion por tipo de clima: ")
    porClima.forEach { (tipo, lista) ->
        {
            println("${tipo}: ${lista.map { it.ciudad }.joinToString(", ")}")
        }
        registros.also {
            println("Consultas Exitosas: ${registros.size} de $totalCiudades")
        }}

        println("=========================================")
}