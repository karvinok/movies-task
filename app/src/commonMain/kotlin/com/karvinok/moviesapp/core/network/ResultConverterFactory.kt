package com.karvinok.moviesapp.core.network

import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.converter.Converter
import de.jensklingenberg.ktorfit.converter.KtorfitResult
import de.jensklingenberg.ktorfit.converter.TypeData
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

internal class ResultConverterFactory : Converter.Factory {

    override fun suspendResponseConverter(
        typeData: TypeData,
        ktorfit: Ktorfit
    ): Converter.SuspendResponseConverter<HttpResponse, *>? {
        if (typeData.typeInfo.type == Result::class) {
            return object : Converter.SuspendResponseConverter<HttpResponse, Any> {
                override suspend fun convert(result: KtorfitResult): Any {
                    return when (result) {
                        is KtorfitResult.Failure -> {
                            Result.failure<Any>(result.throwable)
                        }

                        is KtorfitResult.Success -> {
                            if (result.response.status.value in 200..299) {
                                val type = typeData.typeArgs.first()
                                val responseType = type.typeInfo.type

                                if (responseType == Unit::class) {
                                    Result.success(Unit)
                                } else {
                                    if (type.isNullable) {
                                        try {
                                            Result.success(result.response.body(type.typeInfo))
                                        } catch (e: Exception) {
                                            Result.success(null)
                                        }
                                    } else {
                                        try {
                                            Result.success(result.response.body(type.typeInfo))
                                        } catch (e: Exception) {
                                            Result.failure<Any>(e)
                                        }
                                    }
                                }
                            } else {
                                Result.failure<Any>(Throwable(result.response.toString()))
                            }
                        }
                    }
                }
            }
        }
        return null
    }
}
