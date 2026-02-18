package com.dev2026047.berlinclock.shared.riverpod

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

abstract class Notifier<T>(initialState: T) {
    var state: T by mutableStateOf(initialState)
        protected set
}
