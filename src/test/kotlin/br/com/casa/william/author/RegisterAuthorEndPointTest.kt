package br.com.casa.william.author

import br.com.casa.william.AuthorRecordGrpc
import br.com.casa.william.AuthorRequest
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime
import javax.inject.Singleton

@MicronautTest(transactional = false)
internal class RegisterAuthorEndPointTest(
    val grpcClient: AuthorRecordGrpc.AuthorRecordBlockingStub,
    val authorRepository: AuthorRepository
) {

    @BeforeEach
    internal fun setUp() {
        authorRepository.deleteAll()
    }

    @Test
    fun `deve cadastra um novo autor`() {
        //cenario
        val request = AuthorRequest.newBuilder()
            .setEmail("william@email.com")
            .setName("william")
            .setDescription("Programador coisa mais linda")
            .build()

        //acao
        val response = grpcClient.registry(request)

        //validacao
        with(response) {
            assertEquals(request.name, response.name)
        }
    }

    @Test
    fun `nao deve cadastrar um novo autor com  dados de entrada invalido`() {
        //cenario
        val request = AuthorRequest.newBuilder()
            .setEmail("william@email.com")
            .setName("")
            .setDescription("Programador coisa mais linda")
            .build()

        //acao
        val response = assertThrows<StatusRuntimeException> {
            grpcClient.registry(request)
        }

        //validacao
        with(response) {
            assertEquals(Status.INVALID_ARGUMENT.code, response.status.code)
            assertEquals("Nenhum campo pode estar vazio ou com dados inválidos", response.status.description)
        }
    }

    @Test
    fun `nao deve cadastrar autor se email já existir`() {
       //cenario
        val newAuthor = Author(
            "william@email.com",
            "william",
            "Programador",
            LocalDateTime.now()
        )
        authorRepository.save(newAuthor)

        //acao
        val request = AuthorRequest.newBuilder()
            .setEmail("william@email.com")
            .setName("william")
            .setDescription("Programador coisa mais linda")
            .build()

        //validacao
        val response = assertThrows<StatusRuntimeException> {
            grpcClient.registry(request)
        }
        with(response) {
            assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
            assertEquals("Email already registered", response.status.description)
        }
    }


    @Factory
    class grpc {
        @Singleton
        fun blockStunbAuthor(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel):
                AuthorRecordGrpc.AuthorRecordBlockingStub {
            return AuthorRecordGrpc.newBlockingStub(channel)
        }
    }
}

