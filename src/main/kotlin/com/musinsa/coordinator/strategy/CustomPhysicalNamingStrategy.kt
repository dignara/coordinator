package com.musinsa.coordinator.strategy

import org.hibernate.boot.model.naming.Identifier
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment
import java.util.*

class CustomPhysicalNamingStrategy : PhysicalNamingStrategyStandardImpl() {
    override fun toPhysicalCatalogName(identifier: Identifier?, jdbcEnv: JdbcEnvironment?): Identifier? {
        return convertToSnakeCase(identifier)
    }

    override fun toPhysicalColumnName(identifier: Identifier?, jdbcEnv: JdbcEnvironment?): Identifier? {
        return convertToSnakeCase(identifier)
    }

    override fun toPhysicalSchemaName(identifier: Identifier?, jdbcEnv: JdbcEnvironment?): Identifier? {
        return convertToSnakeCase(identifier)
    }

    override fun toPhysicalTableName(identifier: Identifier?, jdbcEnv: JdbcEnvironment?): Identifier? {
        return convertToSnakeCase(identifier)
    }

    private fun convertToSnakeCase(identifier: Identifier?): Identifier? {
        return identifier?.let {
            val regex = "([a-z])([A-Z])"
            val replacement = "$1_$2"
            val newName = identifier.text
                .replace(regex.toRegex(), replacement)
                .lowercase(Locale.getDefault())
            return Identifier.toIdentifier(newName, identifier.isQuoted)
        }
    }
}