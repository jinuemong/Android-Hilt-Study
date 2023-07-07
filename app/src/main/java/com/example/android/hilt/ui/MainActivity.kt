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

package com.example.android.hilt.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.hilt.R
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Main activity of the application.
 *
 * Container for the Buttons & Logs fragments. This activity simply tracks clicks on buttons.
 */
// LogsFragment에 인스턴스를 삽입하는 데 필요한 조건을 갖춤
// 하지만 프래그먼트를 호스팅하는 액티비티를 알아야 하므로,
// Activity에서도 AndroidEntryPoint 어노테이션 추가
// 모든 코드를 작성하면 Hilt에 AppNavigator 인스턴스를 삽입할 수 있는 정보가 저장 됨
// MainActivity에서 아래와 같이 사용 할 수 있음
// -> 앱을 싫행하면, 의존성 주입이 된 AppNavigator를 사용할 수 있다.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Hilt에서 가져오는 동작 (private 사용 불가능 !)
    // onCreate 함수에서 navigator 초기화 코드를 작성할 필요 없음 (삭제 )
    @Inject lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigator.navigateTo(Screens.BUTTONS)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}
