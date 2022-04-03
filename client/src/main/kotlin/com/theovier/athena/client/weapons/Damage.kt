package com.theovier.athena.client.weapons


data class Damage(var rawAmount: Int, val type: DamageType, var source: DamageSource?, var isCritical: Boolean = false) {
    val amount: Int
        get() {
            if(isCritical) {
                return rawAmount * CRIT_DAMAGE_MODIFIER
            }
            return rawAmount
        }

    companion object {
        const val CRIT_DAMAGE_MODIFIER = 2
    }
}