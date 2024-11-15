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
            EcoChallenge(R.drawable.imgbici, R.string.name1),
            EcoChallenge(R.drawable.imgcamina, R.string.name2),
            EcoChallenge(R.drawable.imgauto, R.string.name3),
            EcoChallenge(R.drawable.imgcomposta, R.string.name4),
            EcoChallenge(R.drawable.imgreciclado, R.string.name5),
            EcoChallenge(R.drawable.imgdesechos, R.string.name6),
            EcoChallenge(R.drawable.imgenergia, R.string.name7),
            EcoChallenge(R.drawable.imgflora, R.string.name8),
            EcoChallenge(R.drawable.imgregar, R.string.name9),
            EcoChallenge(R.drawable.imgtermo, R.string.name10),
            EcoChallenge(R.drawable.imgresiduos, R.string.name11),
        )
    }

    fun loadBadges(): List<BadgeItem> {
        return listOf(
            BadgeItem(
                R.drawable.bicicleta,
                R.string.badgeName1,
                R.integer.progress1,
                R.integer.maxProgress1
            ),
            BadgeItem(
                R.drawable.camina,
                R.string.badgeName2,
                R.integer.progress2,
                R.integer.maxProgress2
            ),
            BadgeItem(
                R.drawable.carro,
                R.string.badgeName3,
                R.integer.progress3,
                R.integer.maxProgress3
            ),
            BadgeItem(
                R.drawable.composta,
                R.string.badgeName4,
                R.integer.progress4,
                R.integer.maxProgress4
            ),
            BadgeItem(
                R.drawable.reciclado,
                R.string.badgeName5,
                R.integer.progress5,
                R.integer.maxProgress5
            ),
            BadgeItem(
                R.drawable.desechos,
                R.string.badgeName6,
                R.integer.progress6,
                R.integer.maxProgress6
            ),
            BadgeItem(
                R.drawable.energia,
                R.string.badgeName7,
                R.integer.progress7,
                R.integer.maxProgress7
            ),
            BadgeItem(
                R.drawable.flora,
                R.string.badgeName8,
                R.integer.progress8,
                R.integer.maxProgress8
            ),
            BadgeItem(
                R.drawable.regar,
                R.string.badgeName9,
                R.integer.progress9,
                R.integer.maxProgress9
            ),
            BadgeItem(
                R.drawable.thermo,
                R.string.badgeName10,
                R.integer.progress10,
                R.integer.maxProgress10
            ),
            BadgeItem(
                R.drawable.residuos,
                R.string.badgeName10,
                R.integer.progress10,
                R.integer.maxProgress10
            ),
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