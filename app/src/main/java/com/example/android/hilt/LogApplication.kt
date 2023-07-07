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

package com.example.android.hilt

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// 종속 항목 삽입을 사용할 수 있는 애플리케이션의 기본 클래스가 포함된 Hilt 코드 생성을 트리거
// 애플리케이션 컨테이너는 앱의 상위 컨테이너 이므로, 다른 컨테이너는 이 상위 컨테이너에서 제공하는
// 종속 항목에 엑세스할 수 있음
@HiltAndroidApp
class LogApplication : Application()
