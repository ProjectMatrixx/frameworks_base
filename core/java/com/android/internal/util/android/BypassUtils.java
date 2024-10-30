/*
 * Copyright (C) 2023-2024 the risingOS Android Project
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
package com.android.internal.util.android;

import android.app.ActivityThread;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.RemoteException;
import android.os.UserHandle;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BypassUtils {

    private static Set<String> mLauncherPkgs;
    private static Set<String> mExemptedUidPkgs;

    private static Set<String> getLauncherPkgs() {
        if (mLauncherPkgs == null) {
            Context context = getContext();
            if (context != null) {
                mLauncherPkgs = new HashSet<>(Arrays.asList(context.getResources()
                        .getStringArray(com.android.internal.R.array.config_launcherPackages)));
            } else {
                mLauncherPkgs = new HashSet<>();
            }
        }
        return mLauncherPkgs;
    }

    private static Set<String> getExemptedUidPkgs() {
        if (mExemptedUidPkgs == null) {
            mExemptedUidPkgs = new HashSet<>();
            mExemptedUidPkgs.add("com.google.android.gms");
            mExemptedUidPkgs.addAll(getLauncherPkgs());
        }
        return mExemptedUidPkgs;
    }

    public static boolean isSystemLauncher(int callingUid) {
        try {
            String callerPackage = ActivityThread.getPackageManager().getNameForUid(callingUid);
            return getLauncherPkgs().contains(callerPackage);
        } catch (Exception e) {
            return false;
        }
    }

    public static Context getContext() {
        return ActivityThread.currentApplication().getApplicationContext();
    }

    public static boolean shouldBypassTaskPermission(int callingUid) {
        for (String pkg : getExemptedUidPkgs()) {
            try {
                ApplicationInfo appInfo = ActivityThread.getPackageManager()
                        .getApplicationInfo(pkg, 0, UserHandle.getUserId(callingUid));
                if (appInfo.uid == callingUid) {
                    return true;
                }
            } catch (Exception e) {}
        }
        return false;
    }
}
