package com.theovier.athena.client.ecs.prefabs.loaders

import com.badlogic.gdx.utils.JsonValue

data class ComponentData(val json: JsonValue, val id: String, val belongsTo: String, val dependencies: Array<String>) {
    override fun toString(): String {
        return "$id (requires ${dependencies.joinToString(",", "'", "'")})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ComponentData

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}