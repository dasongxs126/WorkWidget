package com.beardip.work.widget.provider

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.beardip.work.widget.R

class OverTimeWidgetProvider : AppWidgetProvider() {
    companion object {
        const val TAG = "OverTimeWidgetProvider"
        const val CLICK_ACTION = "com.beardip.work.widget.CLICK"
        const val SP_KEY_STATE = "key_state"
        const val SP_FILE_NAME = "work_state_data"
        const val SP_STATE_WORK = 1
        const val SP_STATE_REST = 0
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.i(TAG, "onReceive")
        val appWidgetManager = AppWidgetManager.getInstance(context)
        if (intent?.action.equals(CLICK_ACTION)) {
            context?.apply {
                val remoteViews = RemoteViews(packageName, R.layout.widget_overt_time)
                getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE).apply {
                    when (getInt(SP_KEY_STATE,SP_STATE_REST)) {
                        SP_STATE_WORK -> {
                            remoteViews.setTextViewText(R.id.state, "双")
                            remoteViews.setTextColor(R.id.state,getColor(R.color.green))
                            remoteViews.setImageViewResource(R.id.meme_img, R.drawable.img_rest)
                            edit()?.putInt(SP_KEY_STATE, SP_STATE_REST)?.apply()
                        }
                        SP_STATE_REST -> {
                            remoteViews.setTextViewText(R.id.state, "单")
                            remoteViews.setTextColor(R.id.state,getColor(R.color.red))
                            remoteViews.setImageViewResource(R.id.meme_img, R.drawable.img_work)
                            edit()?.putInt(SP_KEY_STATE, SP_STATE_WORK)?.apply()
                        }
                    }
                }
                appWidgetManager.updateAppWidget(
                    ComponentName(
                        this,
                        OverTimeWidgetProvider::class.java
                    ), remoteViews
                )
            }
        }
    }

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        Log.i(TAG, "onUpdate")
        appWidgetIds?.apply {
            this.forEach {
                context?.apply {
                    val remoteViews = RemoteViews(packageName, R.layout.widget_overt_time)
                    val clickIntent = Intent()
                    clickIntent.setClass(this,OverTimeWidgetProvider::class.java)
                    clickIntent.setAction(CLICK_ACTION)
                    val pi = PendingIntent.getBroadcast(this,0, clickIntent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
                    remoteViews.setOnClickPendingIntent(R.id.state, pi)
                    appWidgetManager?.updateAppWidget(it, remoteViews)
                }
            }
        }
    }
}