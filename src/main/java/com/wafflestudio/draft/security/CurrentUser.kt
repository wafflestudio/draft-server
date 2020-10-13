package com.wafflestudio.draft.security

import org.springframework.security.core.annotation.AuthenticationPrincipal
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
annotation class CurrentUser(val require: Boolean = true) 