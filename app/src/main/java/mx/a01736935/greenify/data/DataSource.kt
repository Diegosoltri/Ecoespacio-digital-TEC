package mx.a01736935.greenify.data

import mx.a01736935.greenify.R
import mx.a01736935.greenify.model.ArticleTransport
import mx.a01736935.greenify.model.ArticleEnergy
import mx.a01736935.greenify.model.ArticleConsumption
import mx.a01736935.greenify.model.ArticleWaste
import mx.a01736935.greenify.model.ArticleItem
import mx.a01736935.greenify.model.BadgeItem
import mx.a01736935.greenify.model.EcoChallenge

class DataSource {
    fun loadEcoChallenges(): List<EcoChallenge> {
        return listOf(
            EcoChallenge(R.drawable.basura, R.string.name1, R.string.location1, R.string.bounty1),
            EcoChallenge(R.drawable.basura, R.string.name1, R.string.location1, R.string.bounty1),
            EcoChallenge(R.drawable.basura, R.string.name1, R.string.location1, R.string.bounty1),
            EcoChallenge(R.drawable.basura, R.string.name1, R.string.location1, R.string.bounty1),
        )
    }

    fun loadBadges(): List<BadgeItem> {
        return listOf(
            BadgeItem(
                R.drawable.badge1,
                R.string.badgeName1,
                R.integer.progress1,
                R.integer.maxProgress1,
                R.integer.score1,
                R.integer.percentage1
            ),
            BadgeItem(
                R.drawable.badge2,
                R.string.badgeName2,
                R.integer.progress2,
                R.integer.maxProgress2,
                R.integer.score2,
                R.integer.percentage2
            ),
            BadgeItem(
                R.drawable.badge3,
                R.string.badgeName3,
                R.integer.progress3,
                R.integer.maxProgress3,
                R.integer.score3,
                R.integer.percentage3
            ),
            BadgeItem(
                R.drawable.badge4,
                R.string.badgeName4,
                R.integer.progress4,
                R.integer.maxProgress4,
                R.integer.score4,
                R.integer.percentage4
            )
        )
    }

    fun loadArticleTransport(): List<ArticleTransport> {
        return listOf(
            ArticleTransport(R.string.transport1),
            ArticleTransport(R.string.transport2),
            ArticleTransport(R.string.transport3),
            ArticleTransport(R.string.transport4),
            ArticleTransport(R.string.transport5),
            ArticleTransport(R.string.transport6),
            ArticleTransport(R.string.transport7),
            ArticleTransport(R.string.transport8),
            ArticleTransport(R.string.transport9),
            ArticleTransport(R.string.transport10)
        )
    }

    fun loadArticleEnergy(): List<ArticleEnergy> {
        return listOf(
            ArticleEnergy(R.string.energy1),
            ArticleEnergy(R.string.energy2),
            ArticleEnergy(R.string.energy3),
            ArticleEnergy(R.string.energy4),
            ArticleEnergy(R.string.energy5),
            ArticleEnergy(R.string.energy6),
            ArticleEnergy(R.string.energy7),
            ArticleEnergy(R.string.energy8),
            ArticleEnergy(R.string.energy9),
            ArticleEnergy(R.string.energy10)
        )
    }

    fun loadArticleConsumption(): List<ArticleConsumption> {
        return listOf(
            ArticleConsumption(R.string.consumption1),
            ArticleConsumption(R.string.consumption2),
            ArticleConsumption(R.string.consumption3),
            ArticleConsumption(R.string.consumption4),
            ArticleConsumption(R.string.consumption5),
            ArticleConsumption(R.string.consumption6),
            ArticleConsumption(R.string.consumption7),
            ArticleConsumption(R.string.consumption8),
            ArticleConsumption(R.string.consumption9),
            ArticleConsumption(R.string.consumption10)
        )
    }

    fun loadArticleWaste(): List<ArticleWaste> {
        return listOf(
            ArticleWaste(R.string.waste1),
            ArticleWaste(R.string.waste2),
            ArticleWaste(R.string.waste3),
            ArticleWaste(R.string.waste4),
            ArticleWaste(R.string.waste5),
            ArticleWaste(R.string.waste6),
            ArticleWaste(R.string.waste7),
            ArticleWaste(R.string.waste8),
            ArticleWaste(R.string.waste9),
            ArticleWaste(R.string.waste10)
        )
    }

    fun loadArticle(): List<ArticleItem> {
        return listOf(
            ArticleItem(R.string.transport1),
            ArticleItem(R.string.transport2),
            ArticleItem(R.string.transport3),
            ArticleItem(R.string.transport4),
            ArticleItem(R.string.transport5),
            ArticleItem(R.string.transport6),
            ArticleItem(R.string.transport7),
            ArticleItem(R.string.transport8),
            ArticleItem(R.string.transport9),
            ArticleItem(R.string.transport10),
            ArticleItem(R.string.energy1),
            ArticleItem(R.string.energy2),
            ArticleItem(R.string.energy3),
            ArticleItem(R.string.energy4),
            ArticleItem(R.string.energy5),
            ArticleItem(R.string.energy6),
            ArticleItem(R.string.energy7),
            ArticleItem(R.string.energy8),
            ArticleItem(R.string.energy9),
            ArticleItem(R.string.energy10),
            ArticleItem(R.string.consumption2),
            ArticleItem(R.string.consumption1),
            ArticleItem(R.string.consumption3),
            ArticleItem(R.string.consumption4),
            ArticleItem(R.string.consumption5),
            ArticleItem(R.string.consumption6),
            ArticleItem(R.string.consumption7),
            ArticleItem(R.string.consumption8),
            ArticleItem(R.string.consumption9),
            ArticleItem(R.string.consumption10),
            ArticleItem(R.string.waste1),
            ArticleItem(R.string.waste2),
            ArticleItem(R.string.waste3),
            ArticleItem(R.string.waste4),
            ArticleItem(R.string.waste5),
            ArticleItem(R.string.waste6),
            ArticleItem(R.string.waste7),
            ArticleItem(R.string.waste8),
            ArticleItem(R.string.waste9),
            ArticleItem(R.string.waste10)
        )
    }
}