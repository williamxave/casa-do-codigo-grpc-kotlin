package br.com.casa.william.category

import br.com.casa.william.CategoryRecordGrpc
import br.com.casa.william.CategoryRequest
import io.grpc.ManagedChannel
import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import io.micronaut.grpc.server.GrpcServerChannel
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Singleton
import org.junit.jupiter.api.assertThrows

@MicronautTest(transactional = false)
internal class RegisterCategoryEndPointTest(
    val grpcClient: CategoryRecordGrpc.CategoryRecordBlockingStub,
    val categoryRepository: CategoryRepository
){

    @BeforeEach
    internal fun setUp() {
        categoryRepository.deleteAll()
    }

    @Test
    fun `deve cadastrar uma categoria`(){
        //cenario
        val request = CategoryRequest.newBuilder().setName("Drama").build()
        //acao
        val response = grpcClient.categoryRegistry(request)
        //validacao
        assertEquals(request.name, response.name)
    }

    @Test
    fun `nao deve cadastrar uma categoria se o campo estiver vazio`(){
        //cenario
        val request = CategoryRequest.newBuilder().setName("").build()
        //acao
        val response = assertThrows<StatusRuntimeException>(){
            grpcClient.categoryRegistry(request)
        }
        //validacao
        with(response) {
            assertEquals(Status.INVALID_ARGUMENT.code, response.status.code)
            assertEquals("Nenhum campo pode estar vazio ou com dados inválidos", response.status.description)

        }
    }

    @Test
    fun `nao deve cadastrar uma categoria se já existir uma com o nome igual`(){
        //cenario
        categoryRepository.save(Category(name =  "Drama"))

        val request = CategoryRequest.newBuilder().setName("Drama").build()

        //acao
        val response = assertThrows<StatusRuntimeException> {
            grpcClient.categoryRegistry(request)
        }

        //validacao
        with(response) {
            assertEquals(Status.ALREADY_EXISTS.code, response.status.code)
            assertEquals("Category already registered", response.status.description)
        }
    }


    @Factory
    class grpcCategory {
        @Singleton
        fun mockStubCategory(@GrpcChannel(GrpcServerChannel.NAME) channel: ManagedChannel): CategoryRecordGrpc.CategoryRecordBlockingStub {
            return CategoryRecordGrpc.newBlockingStub(channel)
        }
    }
}