package org.wit.player.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.player.models.PlayerJSONStore
import org.wit.player.models.PlayerMemStore
import org.wit.player.models.PlayerModel
import org.wit.player.models.PlayerStore


class MainApp : Application(), AnkoLogger {

    lateinit var players: PlayerStore


    override fun onCreate() {
        super.onCreate()
        players = PlayerJSONStore(applicationContext)
        info("Player started")
    }
}