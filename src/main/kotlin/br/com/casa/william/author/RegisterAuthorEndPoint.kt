package br.com.casa.william.author

import br.com.casa.william.AuthorRecordGrpc
import br.com.casa.william.AuthorRequest
import br.com.casa.william.AuthorResponse
import br.com.casa.william.validator.ErrorHandler
import io.grpc.stub.StreamObserver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
@ErrorHandler
class RegisterAuthorEndPoint(
    val authorValidate: AuthorValidate
) : AuthorRecordGrpc.AuthorRecordImplBase() {

    private val logger: Logger = LoggerFactory.getLogger(RegisterAuthorEndPoint::class.java)

    override fun registry(request: AuthorRequest, responseObserver: StreamObserver<AuthorResponse>) {
        logger.info("Receive request")
        val authorDto = request.toModel()

        logger.info("Transforming dto to model and saving to the bank ")
        val newAuthor =  authorValidate.validate(authorDto)

        logger.info("Author save")
        responseObserver.onNext(AuthorResponse.newBuilder().setName(newAuthor.name).build())
        responseObserver.onCompleted()

    }
}