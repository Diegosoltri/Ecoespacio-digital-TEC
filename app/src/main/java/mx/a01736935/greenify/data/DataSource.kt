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
                R.string.badgeName11,
                R.integer.progress11,
                R.integer.maxProgress11
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
            ArticleTransport(R.string.transport10),
            ArticleTransport(R.string.transport11),
            ArticleTransport(R.string.transport12),
            ArticleTransport(R.string.transport13),
            ArticleTransport(R.string.transport14),
            ArticleTransport(R.string.transport15),
            ArticleTransport(R.string.transport16),
            ArticleTransport(R.string.transport17),
            ArticleTransport(R.string.transport18),
            ArticleTransport(R.string.transport19),
            ArticleTransport(R.string.transport20),
            ArticleTransport(R.string.transport21),
            ArticleTransport(R.string.transport22),
            ArticleTransport(R.string.transport23),
            ArticleTransport(R.string.transport24),
            ArticleTransport(R.string.transport25)


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
            ArticleEnergy(R.string.energy10),
            ArticleEnergy(R.string.energy11),
            ArticleEnergy(R.string.energy12),
            ArticleEnergy(R.string.energy13),
            ArticleEnergy(R.string.energy14),
            ArticleEnergy(R.string.energy15),
            ArticleEnergy(R.string.energy16),
            ArticleEnergy(R.string.energy17),
            ArticleEnergy(R.string.energy18),
            ArticleEnergy(R.string.energy19),
            ArticleEnergy(R.string.energy20),
            ArticleEnergy(R.string.energy21),
            ArticleEnergy(R.string.energy22),
            ArticleEnergy(R.string.energy23),
            ArticleEnergy(R.string.energy24),
            ArticleEnergy(R.string.energy25)
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
            ArticleConsumption(R.string.consumption10),
            ArticleConsumption(R.string.consumption11),
            ArticleConsumption(R.string.consumption12),
            ArticleConsumption(R.string.consumption13),
            ArticleConsumption(R.string.consumption14),
            ArticleConsumption(R.string.consumption15),
            ArticleConsumption(R.string.consumption16),
            ArticleConsumption(R.string.consumption17),
            ArticleConsumption(R.string.consumption18),
            ArticleConsumption(R.string.consumption19),
            ArticleConsumption(R.string.consumption20),
            ArticleConsumption(R.string.consumption21),
            ArticleConsumption(R.string.consumption22),
            ArticleConsumption(R.string.consumption23),
            ArticleConsumption(R.string.consumption24),
            ArticleConsumption(R.string.consumption25)
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
            ArticleWaste(R.string.waste10),
            ArticleWaste(R.string.waste11),
            ArticleWaste(R.string.waste12),
            ArticleWaste(R.string.waste13),
            ArticleWaste(R.string.waste14),
            ArticleWaste(R.string.waste15),
            ArticleWaste(R.string.waste16),
            ArticleWaste(R.string.waste17),
            ArticleWaste(R.string.waste18),
            ArticleWaste(R.string.waste19),
            ArticleWaste(R.string.waste20),
            ArticleWaste(R.string.waste21),
            ArticleWaste(R.string.waste22),
            ArticleWaste(R.string.waste23),
            ArticleWaste(R.string.waste24),
            ArticleWaste(R.string.waste25)
        )
    }

    fun loadArticle(): List<ArticleItem> {
        return listOf(
            /* Transporte */
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
            ArticleItem(R.string.transport11),
            ArticleItem(R.string.transport12),
            ArticleItem(R.string.transport13),
            ArticleItem(R.string.transport14),
            ArticleItem(R.string.transport15),
            ArticleItem(R.string.transport16),
            ArticleItem(R.string.transport17),
            ArticleItem(R.string.transport18),
            ArticleItem(R.string.transport19),
            ArticleItem(R.string.transport20),
            ArticleItem(R.string.transport21),
            ArticleItem(R.string.transport22),
            ArticleItem(R.string.transport23),
            ArticleItem(R.string.transport24),
            ArticleItem(R.string.transport25),
            /* Energía */
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
            ArticleItem(R.string.energy11),
            ArticleItem(R.string.energy12),
            ArticleItem(R.string.energy13),
            ArticleItem(R.string.energy14),
            ArticleItem(R.string.energy15),
            ArticleItem(R.string.energy16),
            ArticleItem(R.string.energy17),
            ArticleItem(R.string.energy18),
            ArticleItem(R.string.energy19),
            ArticleItem(R.string.energy20),
            ArticleItem(R.string.energy21),
            ArticleItem(R.string.energy22),
            ArticleItem(R.string.energy23),
            ArticleItem(R.string.energy24),
            ArticleItem(R.string.energy25),
            /* Consumo */
            ArticleItem(R.string.consumption1),
            ArticleItem(R.string.consumption2),
            ArticleItem(R.string.consumption3),
            ArticleItem(R.string.consumption4),
            ArticleItem(R.string.consumption5),
            ArticleItem(R.string.consumption6),
            ArticleItem(R.string.consumption7),
            ArticleItem(R.string.consumption8),
            ArticleItem(R.string.consumption9),
            ArticleItem(R.string.consumption10),
            ArticleItem(R.string.consumption11),
            ArticleItem(R.string.consumption12),
            ArticleItem(R.string.consumption13),
            ArticleItem(R.string.consumption14),
            ArticleItem(R.string.consumption15),
            ArticleItem(R.string.consumption16),
            ArticleItem(R.string.consumption17),
            ArticleItem(R.string.consumption18),
            ArticleItem(R.string.consumption19),
            ArticleItem(R.string.consumption20),
            ArticleItem(R.string.consumption21),
            ArticleItem(R.string.consumption22),
            ArticleItem(R.string.consumption23),
            ArticleItem(R.string.consumption24),
            ArticleItem(R.string.consumption25),
            /* Desecho */
            ArticleItem(R.string.waste1),
            ArticleItem(R.string.waste2),
            ArticleItem(R.string.waste3),
            ArticleItem(R.string.waste4),
            ArticleItem(R.string.waste5),
            ArticleItem(R.string.waste6),
            ArticleItem(R.string.waste7),
            ArticleItem(R.string.waste8),
            ArticleItem(R.string.waste9),
            ArticleItem(R.string.waste10),
            ArticleItem(R.string.waste11),
            ArticleItem(R.string.waste12),
            ArticleItem(R.string.waste13),
            ArticleItem(R.string.waste14),
            ArticleItem(R.string.waste15),
            ArticleItem(R.string.waste16),
            ArticleItem(R.string.waste17),
            ArticleItem(R.string.waste18),
            ArticleItem(R.string.waste19),
            ArticleItem(R.string.waste20),
            ArticleItem(R.string.waste21),
            ArticleItem(R.string.waste22),
            ArticleItem(R.string.waste23),
            ArticleItem(R.string.waste24),
            ArticleItem(R.string.waste25)
        )
    }
}