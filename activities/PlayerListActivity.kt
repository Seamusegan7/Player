package org.wit.player.activities

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_player_list.*
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.startActivityForResult
import org.wit.player.R
import org.wit.player.main.MainApp
import org.wit.player.models.PlayerModel


class PlayerListActivity : AppCompatActivity(), PlayerListener{

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_list)
        app = application as MainApp


        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        loadPlayers()

        //enabling the action bar and giving it a title
        toolbar.title = title
        setSupportActionBar(toolbar)
    }

    //loading the menu resource
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    //implements the menu event handler, If the event is item add we start the playerActivity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> startActivityForResult<PlayerActivity>(0)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPlayerClick(player: PlayerModel) {
        startActivityForResult(intentFor<PlayerActivity>().putExtra("player_edit", player), 0)
    }


    //refreshing the edited player and displaying the update
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        loadPlayers()
        super.onActivityResult(requestCode, resultCode, data)
    }

    //method to load players to the activity
    private fun loadPlayers() {
        showPlayers(app.players.findAll())
    }

    //method to show the players after the user have added them
    fun showPlayers (players: List<PlayerModel>) {
        recyclerView.adapter = PlayerAdapter(players, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }




}



