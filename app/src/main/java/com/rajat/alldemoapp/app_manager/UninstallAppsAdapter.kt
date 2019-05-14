package com.rajat.alldemoapp.app_manager

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.rajat.alldemoapp.R


/**
 * Created by ist on 29/11/18.
 */
class UninstallAppsAdapter(val mDataSet: List<String>, val mContext: Context) : RecyclerView.Adapter<UninstallAppsAdapter.ViewHolder>() {

//    private lateinit var mContext: Context
//    private lateinit var mDataSet: List<String>


//    constructor(mDataSet: List<String>,mContext: Context) {
//        this.mContext = mContext
//        this.mDataSet = mDataSet
//    }

//    fun UninstallAppsAdapter(context: Context, list: List<String>): ??? {
//        mContext = context
//        mDataSet = list
//    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UninstallAppsAdapter.ViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.custom_view_uninstall_apps, parent, false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
        // Count the installed apps
        return mDataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Initialize a new instance of AppManager class
        val appsManager = AppsManager(mContext)

        // Get the current package name
        val packageName = mDataSet[position]

        // Get the current app icon
        val icon = appsManager.getAppIconByPackageName(packageName)

        // Get the current app label
        val label = appsManager.getApplicationLabelByPackageName(packageName)

        // Set the current app label
        holder.mTextViewLabel.text = label

        // Set the current app package name
        holder.mTextViewPackage.text = packageName

        // Set the current app icon
        holder.mImageViewIcon.setImageDrawable(icon)

        // Set a click listener for ImageButton
        holder.mImageButtonUninstall.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                // Initialize a new Intent to uninstall an app/package
                val intent = Intent(Intent.ACTION_DELETE)
                intent.data = Uri.parse("package:" + packageName)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                mContext.startActivity(intent)
            }
        })
    }


    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var mTextViewLabel: TextView
        var mTextViewPackage: TextView
        var mImageViewIcon: ImageView
        var mImageButtonUninstall: ImageButton

        init {
            // Get the widgets reference from custom layout
            mTextViewLabel = v.findViewById(R.id.app_label)
            mTextViewPackage = v.findViewById(R.id.app_package)
            mImageViewIcon = v.findViewById(R.id.iv_icon)
            mImageButtonUninstall = v.findViewById(R.id.ib_uninstall)
        }
    }
}