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

import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

// 모듈 : 추상 클래스를 생성하고, 추상 메서드를 통해서 기능 구현 (인터페이스에 의존성 주입)
// 추상 클래스 생성
// 효율적인 구성을 위해 모듈 이름은 제공하는 정보 유형을 전달해야 함.
// DatabaseModule이 기존에 존재하지만, 모듈에 탐색 결함을 포함하는 것은 좋지 않음
// DatabaseModule은 ApplicationComponent에 설치되므로 애플리케이션 컨테이너 결합에 사용
// 따라서 Activity에 결합할 새로운 모듈이 필요하다
// 이를 위해서 NavigationModule 제작
// -> ActivityComponent 사용 (Activity를 종속 항목으로 포함하기 때문 )
// ! Hilt 모듈에는 비정적 결합 메서드와 추상 결합 메서드를 모두 포함할 수 없으므로
// 동일한 클래스에 @Binds와 @Provides 주석을 배치하면 안 됨
@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    // 모듈 내에 AppNavigator(인터페이스) 결합 추가
    // bindNavigator: 이를 Hilt에 알려주고 인터페이스를 반환하는 추상함수
    // 매개변수로 AppNavigatorImpl(인터페이스 구현)을 받음
    @Binds
    abstract fun bindNavigator(impl: AppNavigatorImpl): AppNavigator
}
