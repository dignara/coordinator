package com.musinsa.coordinator.exception

import com.musinsa.coordinator.annotation.ExcludeFromJacocoGeneratedReport
import graphql.GraphQLError
import graphql.GraphqlErrorBuilder
import graphql.schema.DataFetchingEnvironment
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter
import org.springframework.stereotype.Component
import org.springframework.graphql.execution.ErrorType

@ExcludeFromJacocoGeneratedReport
@Component
class GraphQLExceptionResolver : DataFetcherExceptionResolverAdapter() {
    override fun resolveToSingleError(ex: Throwable, env: DataFetchingEnvironment): GraphQLError? {
        return when (ex) {
            is MusinsaException -> {
                GraphqlErrorBuilder.newError()
                    .errorType(ex.errorType)
                    .message(ex.message)
                    .path(env.executionStepInfo.path)
                    .location(env.field.sourceLocation)
                    .build()
            }
            else -> null
        }
    }
}