package com.dev2026047.berlinclock.shared.riverpod

import androidx.compose.runtime.Composable

@Composable
fun ProviderScope(content: @Composable () -> Unit) {
    content()
}
