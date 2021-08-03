package br.com.casa.william.customexceptions

class EmailValidatorException(val msg: String) : RuntimeException(msg)