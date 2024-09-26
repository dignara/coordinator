package com.musinsa.coordinator.annotation

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ExcludeFromJacocoGeneratedReport