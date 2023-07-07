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

package com.example.android.hilt.navigator

import androidx.fragment.app.FragmentActivity
import com.example.android.hilt.R
import com.example.android.hilt.ui.ButtonsFragment
import com.example.android.hilt.ui.LogsFragment
import javax.inject.Inject

/**
 * Navigator implementation.
 */
//AppNavigator 인터페이스를 구현하는 AppNavigatorImpl 클래스
// AppNavigator는 인터페이스이므로 생성자 삽입을 사용할 수 없다 (@Inject constructor)
// 따라서 인터페이스에 사용할 구현을 Hilt에 알리려면 Hilt 모듈 내 함수에 @Binds 주석을 사용
// @Binds 어노테이션은 반드시 추상 클래스에 달아야 한다 -> abstract class NavigationModule
// AppNavigatorImpl -> AppNavigator 인터페이스를 구현하기 위함
class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {
    // @Inject 어노테이션을 통해서 Hilt에 인스턴스 제공 방식을 알려줌
    // AppNavigatorImpl는 FragmentActivity에 종속 됨
    // AppNavigator가 Activity 컨테이너에 제공될 때, 사전 정의된 결합으로 FragmentActivity를 사용 가능
    // 이 인스턴스는 NavigationModule이 ActivityComponent에 설치되어 있으므로,
    // Fragment 컨테이너와 View 컨테이너에서도 사용할 수 있음
    override fun navigateTo(screen: Screens) {
        val fragment = when (screen) {
            Screens.BUTTONS -> ButtonsFragment()
            Screens.LOGS -> LogsFragment()
        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}
