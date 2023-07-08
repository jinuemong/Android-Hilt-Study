/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.example.android.hilt.di

import com.example.android.hilt.data.LoggerDataSource
import com.example.android.hilt.data.LoggerInMemoryDataSource
import com.example.android.hilt.data.LoggerLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton
// 동일한 프로젝트에서 LoggerInMemoryDataSource, LoggerLocalDataSource를 구현해야 하는 경우
// 앱이 실행 되는 동안 LoggerInMemoryDataSource 사용
// Service에서는 LoggerLocalDataSource 사용
// 한정자를 사용하지 않는 경우, Hilt는 같은 유형에 두 개의 결합이 있어
// 어떤 구현을 사용해야 하는지 모름 -> 아래의 에러 발생
// error: [Dagger/DuplicateBindings] com.example.android.hilt.data.LoggerDataSource is bound multiple times

// 동일한 유형의 다른 구현 (여러 개의 결합)을 제공하기 위해서 한정자 사용
@Qualifier
annotation class InMemoryLogger

@Qualifier
annotation class DatabaseLogger

// 각 구현을 제공하는 @Binds 함수에 한정자 주석을 달아줌
// 한정자는 반드시 삽입하려는 구현과 함께 삽입 지점에서 사용해야 함
//Fragment로 이동 !
@InstallIn(SingletonComponent::class)
@Module
abstract class LoggingDatabaseModule {

    @DatabaseLogger
    @Singleton // 범위 지정 주석
    @Binds
    abstract fun bindDatabaseLogger(impl: LoggerLocalDataSource): LoggerDataSource
}

@InstallIn(ActivityComponent::class)
@Module
abstract class LoggingInMemoryModule {

    @InMemoryLogger
    @ActivityScoped
    @Binds
    abstract fun bindInMemoryLogger(impl: LoggerInMemoryDataSource): LoggerDataSource
}
