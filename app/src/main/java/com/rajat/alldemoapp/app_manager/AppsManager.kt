package com.rajat.alldemoapp.app_manager

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.rajat.alldemoapp.R


/**
 * Created by ist on 29/11/18.
 */
class AppsManager {
    private lateinit var mContext: Context

    constructor(mContext: Context) {
        this.mContext = mContext
    }

    // Get a list of installed app
    fun getInstalledPackages(): List<String> {
        // Initialize a new Intent which action is main
        val intent = Intent(Intent.ACTION_MAIN, null)

        // Set the newly created intent category to launcher
//        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.addCategory(Intent.CATEGORY_DEFAULT)

        // Set the intent flags
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED

        // Generate a list of ResolveInfo object based on intent filter
        val resolveInfoList = mContext.packageManager.queryIntentActivities(intent, 0)

        // Initialize a new ArrayList for holding non system package names
        val packageNames = arrayListOf<String>()

        // Loop through the ResolveInfo list
        for (resolveInfo in resolveInfoList) {
            // Get the ActivityInfo from current ResolveInfo
            val activityInfo = resolveInfo.activityInfo

            // If this is not a system app package
            if (!isSystemPackage(resolveInfo) && !isSelfAppPackage(activityInfo.applicationInfo.packageName)) {
                // Add the non system package to the list
                packageNames.add(activityInfo.applicationInfo.packageName)
            }
        }

        return packageNames
    }

    // Custom method to check the package name is not this app package name
    fun isSelfAppPackage(packageName: String): Boolean {
        val thisAppPackageName = mContext.packageName
        return (thisAppPackageName == packageName)
    }

    // Custom method to determine an app is system app
    fun isSystemPackage(resolveInfo: ResolveInfo): Boolean {
        return resolveInfo.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0
    }

    // Custom method to get application icon by package name
    fun getAppIconByPackageName(packageName: String): Drawable {
        var icon: Drawable
        try {
            icon = mContext.packageManager.getApplicationIcon(packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            // Get a default icon
            icon = ContextCompat.getDrawable(mContext, R.drawable.navigation_empty_icon)
        }

        return icon
    }

    // Custom method to get application label by package name
    fun getApplicationLabelByPackageName(packageName: String): String {
        val packageManager = mContext.packageManager
        val applicationInfo: ApplicationInfo?
        var label = "Unknown"
        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, 0)
            if (applicationInfo != null) {
                label = packageManager.getApplicationLabel(applicationInfo) as String
            }

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return label
    }
}