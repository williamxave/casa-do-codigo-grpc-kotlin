package br.com.casa.do.cogido.william

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("br.com.casa.do.cogido.william")
		.start()
}

