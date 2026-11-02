package me.algosketch.newportfolio

import androidx.compose.runtime.Composable
import me.algosketch.newportfolio.app.PortfolioApp
import me.algosketch.newportfolio.designsystem.theme.PortfolioTheme

@Composable
fun App() {
    PortfolioTheme {
        PortfolioApp()
    }
}
