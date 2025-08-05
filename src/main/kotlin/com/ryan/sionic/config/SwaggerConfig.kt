package com.ryan.sionic.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.MediaType
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.responses.ApiResponse
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springdoc.core.customizers.OperationCustomizer
import org.springdoc.core.customizers.ServerBaseUrlCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpRequest
import org.springframework.web.bind.annotation.*
import java.lang.reflect.ParameterizedType
import java.time.Instant

@Configuration
class SwaggerConfig {

    @Bean
    fun openApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .version("v1")
                    .title("Sionic AI 챗봇 서비스")
                    .description("AI 챗봇 API 서비스")
            )
            .components(
                Components().addSecuritySchemes(
                    "bearerAuth", SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .`in`(SecurityScheme.In.HEADER)
                        .name("Authorization")
                )
            )
            .security(listOf(SecurityRequirement().addList("bearerAuth")))
    }

    @Bean
    fun serverBaseUrlCustomizer(): ServerBaseUrlCustomizer {
        return ServerBaseUrlCustomizer { serverBaseUrl: String, _: HttpRequest ->
            if (serverBaseUrl.contains("localhost") || serverBaseUrl.contains("127.0.0.1")) {
                serverBaseUrl
            } else {
                serverBaseUrl.replace("http:", "https:")
            }
        }
    }


    @Bean
    fun customizeOperations(): OperationCustomizer {
        return OperationCustomizer { operation, handlerMethod ->
            val controllerPath = handlerMethod.method.declaringClass.annotations
                .filterIsInstance<RequestMapping>()
                .firstOrNull()?.value?.firstOrNull() ?: ""

            val methodAnnotation = handlerMethod.method.annotations
                .firstOrNull { it.annotationClass.simpleName?.endsWith("Mapping") == true }

            val methodPath = when (val annotation = methodAnnotation) {
                is RequestMapping -> annotation.value.firstOrNull()
                is GetMapping -> annotation.value.firstOrNull()
                is PostMapping -> annotation.value.firstOrNull()
                is PutMapping -> annotation.value.firstOrNull()
                is DeleteMapping -> annotation.value.firstOrNull()
                is PatchMapping -> annotation.value.firstOrNull()
                else -> null
            } ?: ""

            val fullPath = when {
                controllerPath.isNotEmpty() && methodPath.isNotEmpty() -> {
                    val cleanControllerPath = controllerPath.trimStart('/')
                    val cleanMethodPath = methodPath.trimStart('/')
                    if (cleanMethodPath.startsWith(cleanControllerPath)) {
                        "/$cleanMethodPath"
                    } else {
                        "/$cleanControllerPath/$cleanMethodPath"
                    }
                }
                controllerPath.isNotEmpty() -> "/$controllerPath"
                methodPath.isNotEmpty() -> "/$methodPath"
                else -> ""
            }.replace("//", "/")

            operation.responses.forEach { (statusCode, response) ->
                val returnType = handlerMethod.returnType
                val genericReturnType = handlerMethod.method.genericReturnType
                val isBinaryResponse = returnType == ByteArray::class.java ||
                        (genericReturnType is ParameterizedType &&
                                genericReturnType.actualTypeArguments.isNotEmpty() &&
                                genericReturnType.actualTypeArguments[0] == ByteArray::class.java)

                if (isBinaryResponse) {
                    return@OperationCustomizer operation
                }

                if (statusCode == "200" || statusCode == "201") {
                    this.wrapperSuccessResponseBody(response)
                } else {
                    this.wrapperErrorResponseBody(response)
                }
            }

            operation
        }
    }

    private fun wrapperSuccessResponseBody(response: ApiResponse) {
        response.content?.forEach { mediaTypeKey: String?, mediaType: MediaType ->
            val wrapperSchema: Schema<*> = Schema<Any>()

            if (mediaType.schema.`$ref`.equals("#/components/schemas/ApiResponseDtoUnit")) return@forEach

            wrapperSchema.addProperty(
                "timeStamp", Schema<Any>().type("string")
                    .title("응답 시간")
                    .format("date-time")
                    .example(Instant.now().toString())
            )
            wrapperSchema.addProperty("data", mediaType.schema)

            mediaType.schema = wrapperSchema
        }
    }

    private fun wrapperErrorResponseBody(response: ApiResponse) {
        response.content?.forEach { mediaTypeKey: String?, mediaType: MediaType ->
            val wrapperSchema: Schema<*> = Schema<Any>()
            wrapperSchema.addProperty(
                "timeStamp", Schema<Any>().type("string").format("date-time")
                    .title("응답 시간")
                    .example(Instant.now().toString())
            )
            wrapperSchema.addProperty(
                "message", Schema<Any>().type("string")
                    .title("에러 메시지")
                    .example(mediaType.schema.default)
            )

            mediaType.schema = wrapperSchema
        }
    }
}