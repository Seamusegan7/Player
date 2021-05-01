package org.wit.player.models

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

var lastId = 0L

internal fun getId(): Long {

    return lastId++
}

class PlayerMemStore : PlayerStore, AnkoLogger {

    val players = ArrayList<PlayerModel>()

    override fun findAll(): List<PlayerModel> {
        return players
    }

    override fun create(player: PlayerModel) {
        player.id = getId()
        players.add(player)
        logAll()
    }


    override fun update(player: PlayerModel) {
        var foundPlayer: PlayerModel? = players.find { p -> p.id == player.id }
        if (foundPlayer != null) {
            foundPlayer.title = player.title
            foundPlayer.description = player.description
            foundPlayer.image = player.image
            foundPlayer.lat = player.lat
            foundPlayer.lng = player.lng
            foundPlayer.zoom = player.zoom
            logAll()
        }
    }


//method to log all players
    fun logAll() {

        players.forEach{ info("${it}")}
    }

    override fun delete(player: PlayerModel) {
        players.remove(player)
    }


}