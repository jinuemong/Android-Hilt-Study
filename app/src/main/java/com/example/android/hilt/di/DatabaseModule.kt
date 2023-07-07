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

import android.content.Context
import androidx.room.Room
import com.example.android.hilt.data.AppDatabase
import com.example.android.hilt.data.LogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// 모듈 생성
// 모듈을 사용해서 Hilt에 결합을 추가
// 인터페이스나 프로젝트에 포함되지 않은 클래스와 같이 생성자가 삽입될 수 없는 유형
// 의 결합을 Hilt 모듈에 포함. 빌더를 사용하여 인스턴스를 생성해야 하는 OkHttpClient에서는
// InstallIn, Module 주석이 필요
// @Module 어노테이션은 Hilt에 모듈임을 알려줌
// @InstallIn은 어느 컨테이너에서 Hilt 구성요소를 지정하여 결합을 사용할 수 있는지 Hilt에 알려줌
// Hilt에서 삽입할 수 있는 Android 클래스마다 연결된 Hilt 구성 요소가 있음
// ex> Application - ApplicationComponent, Fragment - FragmentComponent
// LoggerLocalDataSource는 애플리케이션 컨테이너로 범위가 지정
// 애플리케이션 컨테이너에서 LogDao 결합을 사용할 수 있어야 함
// 여기서는 애플리케이션 컨테이너에 연결 된 Hilt 구성요소 클래스에 ApplicationComponent:class
// 를 전달해서 @InstallIn 주석으로 요구사항 지정
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    // 코틀린에서 @Provides 함수만 포함하는 모듈은 object 클래스가 될 수 있다
    // 이를 통해서 Provider가 최적화되고, 생성된 코드에 대부분 인라인 됨
    // @Provides 어노테이션을 통해서 Hilt에 생성자가 삽입될 수 없는 유형의 제공 방법을 알려줌
    // DatabaseModule에 이 함수가 포함 됨
    @Provides
    // 항상 동일한 방식의 데이터베이스 인스턴스를 제공하기 위해서 @Singleton 어노테이션 추가
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDatabase {
        // 각 Hilt 컨테이너는 맞춤 결합에 종속 항목으로 삽입될 수 있는 일련의 기본 결합을 제공
        // applicationContext의 사례로, 엑세스하려면 필드에 반드시 @ApplicationContext 주석이 필요
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "logging.db"
        ).build()
    }

    @Provides
    fun provideLogDao(database: AppDatabase): LogDao {
        //LogDao 인스턴스가 제공될 때, database.logDao()가 실행되어야 한다고 명시
        // AppDatabase가 전이 종속 항목이므로 Hilt에 이 유형의 인스턴스 제공 방법도 알려주어야 함

        return database.logDao()
    }
}

