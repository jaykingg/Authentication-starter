package org.example.sample.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {
    @Bean
    fun defaultGroup1(): GroupedOpenApi {
        val packagesToScan = "org.example.sample"
        return GroupedOpenApi.builder()
            .group("jwt")
            .packagesToScan(packagesToScan)
            .build()
    }

//    @Bean
//    fun sampleGroup2(): GroupedOpenApi {
//        /* Group 에 포함시킬 Path*/
//        val pathToMatch = "/sample/**"
//
//        /* Group 에서 제외시킬 Paths */
//        val pathsToExclude = arrayOf("/sample/exclude1/**", "/sample/exclude2/**")
//        return GroupedOpenApi.builder()
//            .group("sample")
//            .pathsToMatch(pathToMatch)
//            .pathsToExclude(pathsToExclude[0], pathsToExclude[1])
//            .build()
//    }

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI().info(
            Info()
                .title(
                    "Sample JWT API"
                )
                .description(
                    "COLO 2.0 Sample JWT project"
                )
                .version(
                    "v0.0.1"
                )
        )
    }
}