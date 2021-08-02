package br.com.casa.william.validator

import io.grpc.Status
import io.grpc.stub.StreamObserver
import io.micronaut.aop.InterceptorBean
import io.micronaut.aop.MethodInterceptor
import io.micronaut.aop.MethodInvocationContext
import javax.inject.Singleton
import javax.validation.ConstraintViolationException

@Singleton
@InterceptorBean(ErrorHandler::class)
class ErrorHandlerInterceptor : MethodInterceptor<Any, Any> {
    override fun intercept(context: MethodInvocationContext<Any, Any>): Any? {
        try {
            return context.proceed()
        } catch (e: Exception) {
            val responseObserver = context.parameterValues[1] as StreamObserver<*>

            val status = when (e) {

                is ConstraintViolationException -> Status.INVALID_ARGUMENT
                    .withDescription("Nenhum campo pode estar vazio ou com dados inválidos")

                else -> Status.INTERNAL.withDescription("Aconteceu algo insesperado! Tente novamente!")
            }
            responseObserver.onError(status.asRuntimeException())
        }
        return null
    }
}