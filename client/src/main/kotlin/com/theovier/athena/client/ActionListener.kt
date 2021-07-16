package com.theovier.athena.client

interface ActionListener {
    fun onAction(action: InputAction): Boolean {
        return false;
    }
}