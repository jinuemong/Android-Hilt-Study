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

package com.example.android.hilt.data

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Data manager class that handles data manipulation between the database and the UI.
 */
// @Inject constructor로 인스턴스화
// @Singleton을 ㅅㅏ용할 경우 인트턴스 범위를 애플리케이션 컨테이너로 지정할 수 있음
// 이를 통해서 다른 유형의 종속 항목으로 사용되는지 또는 삽입된 필드여야 하는지에 관계없이
// 애플리케이션 컨테이너에서 항상 같은 인스턴스를 제공
// @Singleton을 사용할 경우 하위 수준의 계층구조도 같은 결합을 사용
// @Singleton 을 적용한 LoggerLocalDateSource의 인스턴스를 사용하면
// activity, fragment 컨테이너에서도 동일한 인스턴스를 사용할 수 있음
@Singleton
class LoggerLocalDataSource @Inject constructor(private val logDao: LogDao) : LoggerDataSource {

    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun addLog(msg: String) {
        executorService.execute {
            logDao.insertAll(
                Log(
                    msg,
                    System.currentTimeMillis()
                )
            )
        }
    }

    override fun getAllLogs(callback: (List<Log>) -> Unit) {
        executorService.execute {
            val logs = logDao.getAll()
            mainThreadHandler.post { callback(logs) }
        }
    }

    override fun removeLogs() {
        executorService.execute {
            logDao.nukeTable()
        }
    }
}
