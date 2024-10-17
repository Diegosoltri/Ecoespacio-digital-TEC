package mx.a01736935.greenify.data

import mx.a01736935.greenify.R
import mx.a01736935.greenify.model.EcoChallenge

class DataSource {
    fun loadEcoChallenges(): List<EcoChallenge>{
        return listOf(
            EcoChallenge(R.drawable.basura, R.string.name1, R.string.location1, R.string.bounty1)
        )
    }
}