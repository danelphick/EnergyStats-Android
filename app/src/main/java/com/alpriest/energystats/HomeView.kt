package com.alpriest.energystats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.alpriest.energystats.models.RawDataStore
import com.alpriest.energystats.models.RawDataStoring
import com.alpriest.energystats.preview.FakeConfigStore
import com.alpriest.energystats.preview.FakeUserManager
import com.alpriest.energystats.services.DemoNetworking
import com.alpriest.energystats.services.Networking
import com.alpriest.energystats.stores.ConfigManaging
import com.alpriest.energystats.ui.flow.PowerFlowTabView
import com.alpriest.energystats.ui.flow.home.preview
import com.alpriest.energystats.ui.login.ConfigManager
import com.alpriest.energystats.ui.login.UserManaging
import com.alpriest.energystats.ui.settings.SettingsView
import com.alpriest.energystats.ui.theme.AppTheme
import com.alpriest.energystats.ui.theme.EnergyStatsTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun HomeView(
    configManager: ConfigManaging,
    network: Networking,
    userManager: UserManaging,
    onLogout: () -> Unit,
    themeStream: MutableStateFlow<AppTheme>,
    rawDataStore: RawDataStoring,
    onRateApp: () -> Unit,
    onSendUsEmail: () -> Unit
) {
    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = state,
        topBar = {
            TopAppBar(
                title = { Text("Energy Stats") },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            scope.launch { if (state.drawerState.isClosed) state.drawerState.open() else state.drawerState.close() }
                        }
                    ) {
                        Icon(
                            Icons.Filled.Menu, "Menu"
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.surface
            )
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)) {
                PowerFlowTabView(network, configManager, rawDataStore).Content(themeStream = themeStream)
            }
        },
        drawerBackgroundColor = MaterialTheme.colors.background,
        drawerContent = {
            SettingsView(
                config = configManager,
                userManager = userManager,
                onLogout = onLogout,
                rawDataStore = rawDataStore,
                onRateApp = onRateApp,
                onSendUsEmail = onSendUsEmail
            )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun HomepagePreview() {
    EnergyStatsTheme(darkTheme = true) {
        HomeView(
            ConfigManager(
                config = FakeConfigStore(),
                networking = DemoNetworking(),
                rawDataStore = RawDataStore()
            ),
            network = DemoNetworking(),
            userManager = FakeUserManager(),
            {},
            themeStream = MutableStateFlow(AppTheme.preview()),
            rawDataStore = RawDataStore(),
            {},
            {}
        )
    }
}
