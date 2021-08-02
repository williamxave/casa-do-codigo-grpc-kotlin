package br.com.casa.william
import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("br.com.casa.william")
        .start()
}

