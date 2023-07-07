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

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

/**
 * Data access object to query the database.
 */
// LoggerLocalDataSource 인스턴스를 제공하기 위해서
// Hilt에서 LogDao 인스턴스 제공 방법도 알아야 함
// 그래나 LogDao는 인터페이스이므로 생성자가 없음. -> @Inject 사용 불가
// 이러한 유형의 제공 방법 !
@Dao
interface LogDao {

    @Query("SELECT * FROM logs ORDER BY id DESC")
    fun getAll(): List<Log>

    @Insert
    fun insertAll(vararg logs: Log)

    @Query("DELETE FROM logs")
    fun nukeTable()

    @Query("SELECT * FROM logs ORDER BY id DESC")
    fun selectAllLogsCursor(): Cursor

    @Query("SELECT * FROM logs WHERE id = :id")
    fun selectLogById(id: Long): Cursor?
}
