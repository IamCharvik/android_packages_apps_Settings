/*
 * Copyright (C) 2026 The Android Open Source Project
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

package com.android.settings.privacy;

import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;

import com.android.settings.core.TogglePreferenceController;

/**
 * Toggle for denying internet access to newly installed apps.
 *
 * <p>Backs {@link Settings.Secure#DEFAULT_RESTRICT_NETWORK_DATA}. Unset counts as
 * disabled (allow), matching the enforcement-side fallback in
 * NetworkPolicyManagerService.
 */
public class RestrictInternetNewAppsPreferenceController extends TogglePreferenceController {
    public RestrictInternetNewAppsPreferenceController(Context context, String key) {
        super(context, key);
    }

    @Override
    public boolean isChecked() {
        int val = Settings.Secure.getIntForUser(mContext.getContentResolver(),
                Settings.Secure.DEFAULT_RESTRICT_NETWORK_DATA, /* def */ 0,
                UserHandle.myUserId());
        return val == 1;
    }

    @Override
    public boolean setChecked(boolean isChecked) {
        int val = isChecked ? 1 : 0;
        return Settings.Secure.putIntForUser(mContext.getContentResolver(),
                Settings.Secure.DEFAULT_RESTRICT_NETWORK_DATA, val,
                UserHandle.myUserId());
    }

    @Override
    public int getAvailabilityStatus() {
        return AVAILABLE;
    }

    @Override
    public boolean isSliceable() {
        return false;
    }

    @Override
    public int getSliceHighlightMenuRes() {
        // not needed since it's not sliceable
        return NO_RES;
    }
}
