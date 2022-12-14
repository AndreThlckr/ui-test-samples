/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.andrethlckr.ui.samplemodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.repeatOnLifecycle
import io.github.andrethlckr.ui.theme.SampleUiTesterTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.res.stringResource
import io.github.andrethlckr.R

@Composable
fun SampleModelScreen(modifier: Modifier = Modifier, viewModel: SampleModelViewModel = hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val items by produceState<SampleModelUiState>(
        initialValue = SampleModelUiState.Loading,
        key1 = lifecycle,
        key2 = viewModel
    ) {
        lifecycle.repeatOnLifecycle(state = STARTED) {
            viewModel.uiState.collect { value = it }
        }
    }
    if (items is SampleModelUiState.Success) {
        SampleModelScreen(
            items = (items as SampleModelUiState.Success).data,
            onSave = viewModel::addSampleModel,
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SampleModelScreen(
    items: List<String>,
    onSave: (name: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        var nameSampleModel by remember { mutableStateOf("") }
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                label = { Text(text = stringResource(id = R.string.item)) },
                value = nameSampleModel,
                onValueChange = { nameSampleModel = it }
            )

            Button(modifier = Modifier.width(96.dp), onClick = { onSave(nameSampleModel) }) {
                Text(stringResource(R.string.save))
            }
        }
        items.forEach {
            Text(
                "Saved item: $it",
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    SampleUiTesterTheme {
        SampleModelScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    SampleUiTesterTheme {
        SampleModelScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}
