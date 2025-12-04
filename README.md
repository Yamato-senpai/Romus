# Romus – Game Store

## Visão Geral
- Aplicação Android em Kotlin com Jetpack Compose que demonstra uma loja de jogos simples.
- Navegação entre duas Activities (`MainActivity` e `GameDetailActivity`) usando apenas `Intent` (sem NavHost).
- Passagem de objetos de modelo entre Activities via `putExtra`/`getParcelableExtra` com `GameItem` (`Parcelable`).
- UI composta por telas (`views`) e componentes reutilizáveis (`views/ui/components`), seguindo boas práticas de separação.

## Funcionalidades
- Lista de jogos em destaque com cards 
- Detalhe do jogo com:
  - Descrição do jogo.
  - Lista de itens compráveis (moedas/points) com preço formatado em kwanza e imagem.
  - Fluxo de compra via `ModalBottomSheet` com botão de confirmação.
  - Feedback ao confirmar a compra.
- Previews para todas as Composables principais e componentes.

## Tipos de Jogos (catálogo atual)
- Fortnite
  - Itens: V-Bucks (1.000, 5.000, 13.500).
- Call of Duty: Warzone
  - Itens: COD Points (1.100, 4.000, 5.000).

## Arquitetura
- `views/*`: telas (Activities e Composables de páginas).
  - `MainActivity` apresenta a lista de jogos e aciona a navegação por `Intent`.
  - `GameDetailActivity` recebe o `GameItem` e rende as telas de detalhe (Fortnite/Warzone).
- `views/ui/components/*`: componentes reutilizáveis (TopBar, Card de jogo, linha de item, conteúdo do bottom sheet, barra inferior).
- `romus/model/*`: modelos de dados (`GameItem`, `PurchaseItem`).
- `romus/controller/*`: controladores e catálogos.
  - `Navigator`: cria `Intent` para detalhe e define a chave `EXTRA_GAME`.
  - `GameCatalog`: devolve descrição e lista de `PurchaseItem` por jogo.
  - `sampleGames()`: fornece a lista de jogos de exemplo.


## Componentes Reutilizáveis
- `AppTopBar`: barra superior com título e ação de voltar.
- `GameCard`: card clicável de jogo com imagem e thumbnail.
- `PurchasableItemRow`: linha de item comprável com preço e botão.
- `PurchaseBottomSheetContent`: conteúdo do modal de compra.
- `BottomNavBar`: barra inferior com tabs (Histórico/Perfil desativados).

## Previews
- Previews individuais para cada componente em `views/ui/components/*`.
- Previews das telas principais (`EcraPrincipal`, detalhes) para facilitar desenvolvimento.

## Build e Execução
- Requisitos: Android Studio recente, Gradle/AGP compatíveis, JDK 17.
- Compilar debug:
  - Windows: `.\n+gradlew.bat :app:assembleDebug`
- Nota Windows: se ocorrer erro “Couldn't delete R.jar”, feche processos Gradle/AS em background e execute `:app:clean` antes de `:app:assembleDebug`.

## Estrutura de Pastas
```
app/src/main/java/com/example/
├─ views/
│  ├─ MainActivity.kt
│  ├─ GameDetailActivity.kt
│  └─ ui/components/
│     ├─ AppTopBar.kt
│     ├─ GameCard.kt
│     ├─ PurchasableItemRow.kt
│     ├─ PurchaseBottomSheetContent.kt
│     └─ BottomNavBar.kt
├─ romus/
│  ├─ model/
│  │  ├─ GameItem.kt
│  │  └─ PurchaseItem.kt
│  └─ controller/
│     ├─ GameCatalog.kt
│     
```

## Decisões Técnicas
- Separação clara entre UI (views), modelos (model) e controladores (controller).
- Centralização de dados estáticos (descrições e itens) em `GameCatalog`.
- Navegação por `Intent` e chave única (`EXTRA_GAME`) em `Navigator`.


