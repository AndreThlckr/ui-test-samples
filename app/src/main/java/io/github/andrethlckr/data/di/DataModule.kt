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

package io.github.andrethlckr.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import io.github.andrethlckr.data.SampleModelRepository
import io.github.andrethlckr.data.DefaultSampleModelRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsSampleModelRepository(
        sampleModelRepository: DefaultSampleModelRepository
    ): SampleModelRepository
}

class FakeSampleModelRepository @Inject constructor() : SampleModelRepository {

    private val _sampleModels = MutableStateFlow(fakeSampleModels)
    override val sampleModels: Flow<List<String>> = _sampleModels

    override suspend fun add(name: String) {
        _sampleModels.update { it + name }
    }
}

val fakeSampleModels = listOf("One", "Two", "Three")
