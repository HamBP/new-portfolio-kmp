package me.algosketch.newportfolio.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.algosketch.newportfolio.data.Profile
import me.algosketch.newportfolio.designsystem.component.ContentContainer
import me.algosketch.newportfolio.designsystem.component.IconTextLink
import me.algosketch.newportfolio.designsystem.icon.LucideIcons
import me.algosketch.newportfolio.designsystem.theme.PortfolioColors
import me.algosketch.newportfolio.designsystem.theme.PortfolioTextStyle

@Composable
fun Footer(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(PortfolioColors.SurfaceAlt),
    ) {
        HorizontalDivider(thickness = 1.dp, color = PortfolioColors.Border)
        ContentContainer(modifier = Modifier.padding(vertical = 40.dp)) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                itemVerticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = Profile.NAME,
                        modifier = Modifier.padding(bottom = 4.dp),
                        style = PortfolioTextStyle.sans(
                            size = 15.sp,
                            color = PortfolioColors.Heading,
                            weight = FontWeight.SemiBold,
                        ),
                    )
                    Text(
                        text = Profile.JOB_TITLE,
                        style = PortfolioTextStyle.sans(size = 12.sp, color = PortfolioColors.Subtle),
                    )
                }
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    itemVerticalAlignment = Alignment.CenterVertically,
                ) {
                    FooterLink(Profile.EMAIL, LucideIcons.Mail) { uriHandler.openUri(Profile.EMAIL_URL) }
                    FooterLink("LinkedIn", LucideIcons.Linkedin) { uriHandler.openUri(Profile.LINKEDIN_URL) }
                    FooterLink("Blog", LucideIcons.BookOpen) { uriHandler.openUri(Profile.BLOG_URL) }
                }
            }
        }
    }
}

@Composable
private fun FooterLink(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    IconTextLink(
        text = text,
        onClick = onClick,
        icon = icon,
        color = PortfolioColors.Muted,
        gap = 6.dp,
    )
}
