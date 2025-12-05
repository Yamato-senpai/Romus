package com.example.romus.controller

import com.example.romus.R
import com.example.romus.model.GameItem
import com.example.romus.model.PurchaseItem

object GameCatalog {
    fun sampleGames(): List<GameItem> = listOf(
        GameItem("Fortnite", R.drawable.fortnite1, R.drawable.fortnite),
        GameItem("Call of Duty: Warzone", R.drawable.warzone1, R.drawable.warzone)
    )
    fun descriptionFor(game: GameItem): String = when {
        game.title.contains("Fortnite", ignoreCase = true) ->
            "Fortnite é um jogo eletrônico multijogador online e uma plataforma de jogos desenvolvida pela Epic Games e lançada em 2017. Está disponível em sete modos distintos"
        game.title.contains("Warzone", ignoreCase = true) ->
            "Call of Duty: Warzone é um jogo eletrônico free-to-play do gênero battle royale desenvolvido pela Infinity Ward e Raven Software e publicado pela Activision."
        else -> ""
    }

    fun purchaseItemsFor(game: GameItem): List<PurchaseItem> = when {
        game.title.contains("Fortnite", ignoreCase = true) -> listOf(
            PurchaseItem(
                title = "V-Bucks 1,000",
                subtitle = "Compre 1.000 V-bucks do Fortnite, a moeda do jogo que pode ser gasta no Fortnite",
                price = kwz(9000),
                imageRes = R.drawable.vbucks
            ),
            PurchaseItem(
                title = "V-Bucks 5,000",
                subtitle = "Compre 5.000 V-bucks do Fortnite, a moeda do jogo que pode ser gasta no Fortnite",
                price = kwz(38000),
                imageRes = R.drawable.vbucks
            ),
            PurchaseItem(
                title = "V-bucks 13,500",
                subtitle = "Compre 13.500 V-Bucks do Fortnite, a moeda do jogo que pode ser gasta no Fortnite. Com elas, você pode adquirir novos itens de personalização na Loja de Itens, além de conteúdo adicional como o Passe de Batalha da Temporada atual!",
                price = kwz(90000),
                imageRes = R.drawable.vbucks
            )
        )
        game.title.contains("Warzone", ignoreCase = true) -> listOf(
            PurchaseItem(
                title = "COD Points 1,100",
                subtitle = "Moeda para bundles e Battle Pass.",
                price = kwz(10000),
                imageRes = R.drawable.cod
            ),
            PurchaseItem(
                title = "COD Points 4,000",
                subtitle = "Pacote ampliado para bundles premium.",
                price = kwz(39000),
                imageRes = R.drawable.cod
            ),
            PurchaseItem(
                title = "COD Points 5,000",
                subtitle = "Pacote base da Season",
                price = kwz(89000),
                imageRes = R.drawable.cod
            )
        )
        else -> emptyList()
    }

    private fun kwz(amount: Int): String {
        val nf = java.text.NumberFormat.getCurrencyInstance(java.util.Locale.getDefault())
        nf.currency = java.util.Currency.getInstance("AOA")
        return nf.format(amount)
    }
}
