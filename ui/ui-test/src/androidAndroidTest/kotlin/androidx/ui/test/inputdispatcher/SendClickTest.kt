/*
 * Copyright 2019 The Android Open Source Project
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

package androidx.ui.test.inputdispatcher

import android.view.MotionEvent
import androidx.test.filters.SmallTest
import androidx.compose.ui.geometry.Offset
import androidx.ui.test.InputDispatcher.Companion.eventPeriod
import androidx.ui.test.AndroidBaseInputDispatcher.InputDispatcherTestRule
import androidx.ui.test.android.AndroidInputDispatcher
import androidx.ui.test.util.MotionEventRecorder
import androidx.ui.test.util.assertHasValidEventTimes
import androidx.ui.test.util.verify
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

/**
 * Tests if [AndroidInputDispatcher.sendClick] works
 */
@SmallTest
@RunWith(Parameterized::class)
class SendClickTest(config: TestConfig) {
    data class TestConfig(
        val x: Float,
        val y: Float
    )

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun createTestSet(): List<TestConfig> {
            return listOf(0f, 10f, -10f, 1000000f).flatMap { x ->
                listOf(0f, 10f, -10f, 1000000f).map { y ->
                    TestConfig(x, y)
                }
            }
        }
    }

    @get:Rule
    val inputDispatcherRule: TestRule = InputDispatcherTestRule(disableDispatchInRealTime = true)

    private val position = Offset(config.x, config.y)

    private val recorder = MotionEventRecorder()
    private val subject = AndroidInputDispatcher(recorder::recordEvent)

    @After
    fun tearDown() {
        recorder.disposeEvents()
    }

    @Test
    fun testClick() {
        subject.sendClick(position)
        recorder.assertHasValidEventTimes()
        recorder.events.apply {
            assertThat(size).isEqualTo(3)
            this[0].verify(position, MotionEvent.ACTION_DOWN, 0)
            this[1].verify(position, MotionEvent.ACTION_MOVE, eventPeriod)
            this[2].verify(position, MotionEvent.ACTION_UP, eventPeriod)
        }
    }
}
