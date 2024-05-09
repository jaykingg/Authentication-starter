package org.example.sample.config

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator


@Configuration
@Profile("test")
class TestDBInitializer {
    @Bean
    fun testProfileInitializer(connectionFactory: ConnectionFactory?): ConnectionFactoryInitializer {
        val initializer = ConnectionFactoryInitializer()
        initializer.setConnectionFactory(connectionFactory!!)

        val populator = CompositeDatabasePopulator()
        populator.addPopulators(
            ResourceDatabasePopulator(
                ClassPathResource("schema/Account-Create.sql"),
            )
        )
        initializer.setDatabasePopulator(populator)

        return initializer
    }
}