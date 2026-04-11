/*
 * Copyright (C) 2024 Paranoid Android
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
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

package com.android.systemui.usb

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import com.android.internal.app.AlertActivity
import com.android.systemui.broadcast.BroadcastDispatcher
import javax.inject.Inject

class UsbFunctionActivity @Inject constructor(
    @Suppress("UNUSED_PARAMETER") broadcastDispatcher: BroadcastDispatcher,
) : AlertActivity(), DialogInterface.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate: deferring to UsbModePickerDialogDelegate, finishing immediately.")
        super.onCreate(savedInstanceState)
        finish()
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
    }

    private companion object {
        const val TAG = "UsbFunctionActivity"
    }
}
