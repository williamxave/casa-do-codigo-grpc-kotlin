package br.com.casa.william.category

import br.com.casa.william.CategoryRecordGrpc
import br.com.casa.william.CategoryRequest
import br.com.casa.william.CategoryResponse
import br.com.casa.william.validator.ErrorHandler
import io.grpc.stub.StreamObserver
import javax.inject.Singleton

@Singleton
@ErrorHandler
class RegisterCategoryEndPoint(
    val categoryValidate: CategoryValidate
) : CategoryRecordGrpc.CategoryRecordImplBase() {

    override fun categoryRegistry(request: CategoryRequest, responseObserver: StreamObserver<CategoryResponse>) {
        val categoryDto = request.toModel()

        val categoryModel = categoryValidate.validate(categoryDto)

        responseObserver.onNext(
            CategoryResponse
                .newBuilder()
                .setName(categoryModel.name)
                .setExternalId(categoryModel.externalId.toString())
                .build()
        )

        responseObserver.onCompleted()
    }
}
