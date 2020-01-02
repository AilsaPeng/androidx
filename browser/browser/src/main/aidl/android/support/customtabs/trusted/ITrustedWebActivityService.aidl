/*
 * Copyright 2018 The Android Open Source Project
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

package android.support.customtabs.trusted;

/**
 * Interface to a TrustedWebActivityService.
 * @hide
 */
interface ITrustedWebActivityService {
    Bundle areNotificationsEnabled(in Bundle args) = 5;
    Bundle notifyNotificationWithChannel(in Bundle args) = 1;
    void cancelNotification(in Bundle args) = 2;
    Bundle getActiveNotifications() = 4;
    int getSmallIconId() = 3;
    Bundle getSmallIconBitmap() = 6;
    Bundle extraCommand(String commandName, in Bundle args) = 7;
}
