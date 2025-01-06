package com.beardip.work.widget.activity

import android.os.Bundle
import android.os.PersistableBundle
import com.beardip.work.widget.R
import com.beardip.work.widget.common.BaseActivity

class MainActivity: BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
    }
}