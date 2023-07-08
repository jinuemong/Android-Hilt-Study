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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.android.hilt.R
import com.example.android.hilt.data.LoggerDataSource
import com.example.android.hilt.di.InMemoryLogger
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Fragment that displays buttons whose interactions are recorded.
 */
// LogsFragment에서 의존성 주입을 완료했으므로, 이번에는 ButtonsFragment에 적용한다
// 둘 모두 같은 액티비티를 기반으로 동작

// 클래스를 Hilt에서 삽입한 필드로 만들기 위해서 @AndroidEntryPoint 주석 추가
// logger와 navigator 필드의 비공개 수정자(private)를 삭제하고 @Inject 주석을 추가
// 필드 초기화 코드 (onAttach, populateFields 메서드 ) 삭ㅈ ㅔ
@AndroidEntryPoint
class ButtonsFragment : Fragment() {

    // 한정자를 통해서 Hilt에 알림
    @InMemoryLogger
    // 필드 초기화 코드 삭제 후 logger, navigator 주입 추가
    @Inject lateinit var logger: LoggerDataSource //애플리케이션 컨테이너
    @Inject lateinit var navigator: AppNavigator // 액티비티 컨테이너 

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_buttons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.button1).setOnClickListener {
            logger.addLog("Interaction with 'Button 1'")
        }

        view.findViewById<Button>(R.id.button2).setOnClickListener {
            logger.addLog("Interaction with 'Button 2'")
        }

        view.findViewById<Button>(R.id.button3).setOnClickListener {
            logger.addLog("Interaction with 'Button 3'")
        }

        view.findViewById<Button>(R.id.all_logs).setOnClickListener {
            navigator.navigateTo(Screens.LOGS)
        }

        view.findViewById<Button>(R.id.delete_logs).setOnClickListener {
            logger.removeLogs()
        }
    }
}
