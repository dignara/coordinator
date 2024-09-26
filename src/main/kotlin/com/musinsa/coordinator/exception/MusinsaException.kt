package com.musinsa.coordinator.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.musinsa.coordinator.constants.MusinsaError
import graphql.ErrorClassification
import graphql.GraphQLError
import graphql.language.SourceLocation
import java.util.*

@JsonIgnoreProperties("locations", "path")
class MusinsaException(
    private val error: MusinsaError,

    @JvmField
    @Suppress("INAPPLICABLE_JVM_FIELD")
    override val message: String? = null
) : RuntimeException(message?.let { error.desc }), GraphQLError {
    override fun getMessage(): String {
        return error.desc
    }

    override fun getLocations(): MutableList<SourceLocation>? {
        return null
    }

    override fun getErrorType(): ErrorClassification {
        return error
    }

    override fun getExtensions(): MutableMap<String, Any> {
        return Collections.singletonMap(EXTENSION_CODE, error.code)
    }

    companion object {
        val EXTENSION_CODE = "code"
    }
}