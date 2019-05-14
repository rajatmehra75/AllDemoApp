package com.rajat.alldemoapp.app_manager

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.rajat.alldemoapp.R
import kotlinx.android.synthetic.main.activity_app_manager.*


class AppManagerActivity : AppCompatActivity() {

    private var mActivity: Activity? = null


    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var mAdapter: RecyclerView.Adapter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_manager)
        setSupportActionBar(toolbar)

        // Get the application context
        val mContext = applicationContext
        mActivity = this

        // Get the widget reference from XML layout
        val mFab = findViewById<FloatingActionButton>(R.id.fab_refresh)
        val mRecyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        // Define a layout for RecyclerView
        mLayoutManager = GridLayoutManager(mActivity, 1)
        if (mRecyclerView != null) {
            mRecyclerView.layoutManager = mLayoutManager
        }

        // Initialize a new adapter for RecyclerView
        mAdapter = UninstallAppsAdapter(
                AppsManager(mContext).getInstalledPackages(), mContext
        )

        // Set the adapter for RecyclerView
        mRecyclerView.adapter = mAdapter

        // Set a click listener for floating action button
        mFab.setOnClickListener(View.OnClickListener {
            mAdapter = UninstallAppsAdapter(
                    AppsManager(mContext).getInstalledPackages(), mContext
            )

            // Set the adapter for Recycler view
            // Refresh recycler view with updated data
            mRecyclerView.adapter = mAdapter
        })
//        mFab.setOnClickListener(object : View.OnClickListener() {
//            fun onClick(view: View) {
//                // Recreate the adapter with installed apps list
//                mAdapter = UninstallAppsAdapter(
//                        mContext,
//                        AppsManager(mContext).getInstalledPackages()
//                )
//
//                // Set the adapter for Recycler view
//                // Refresh recycler view with updated data
//                mRecyclerView.adapter = mAdapter
//            }
//        })

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }
    }

}
