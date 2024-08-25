package tech.splitexpense

import io.micronaut.runtime.Micronaut



class Application

fun main(args: Array<String>) {
	Micronaut.run(Application::class.java, *args)
}
