package com.theovier.athena.client.inputs

interface ActionInputListener {
    fun onAction(action: ActionInput): Boolean {
        return false;
    }
}