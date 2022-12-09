package io.github.andrethlckr.kaspresso

import androidx.annotation.StringRes
import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import androidx.test.platform.app.InstrumentationRegistry
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.andrethlckr.R
import io.github.kakaocup.compose.node.element.KNode

class ComposeMainScreen(
    semanticsProvider: SemanticsNodeInteractionsProvider
) : ComposeScreen<ComposeMainScreen>(semanticsProvider = semanticsProvider) {

    val itemTextField: KNode = child {
        hasText(getString(R.string.item))
    }

    val saveButton: KNode = child {
        hasText(getString(R.string.save))
    }

    fun onListItem(
        withText: String,
        function: KNode.() -> Unit
    ) = child<KNode> {
        hasText("Saved item: $withText")
    }.invoke(function)

    fun hasListItem(withText: String) {
        onListItem(withText) {
            assertIsDisplayed()
        }
    }
}

private fun getString(@StringRes res: Int) = InstrumentationRegistry
    .getInstrumentation()
    .targetContext
    .getString(res)