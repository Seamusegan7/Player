package org.wit.player.activities

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.wit.player.R

class SplashActivity : AppCompatActivity(), AnkoLogger {

    private val SPLASH_TIME_OUT:Long = 3000 // 1 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            //once timer is over the Main activity will start

            startActivity<PlayerListActivity>()
            // close this activity
            finish()
        }, SPLASH_TIME_OUT)
    }

}



