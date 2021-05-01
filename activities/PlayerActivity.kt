package org.wit.player.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_player.*
import org.jetbrains.anko.*
import org.wit.player.R
import org.wit.player.helpers.readImage
import org.wit.player.helpers.readImageFromPath
import org.wit.player.helpers.showImagePicker
import org.wit.player.main.MainApp
import org.wit.player.models.Location
import org.wit.player.models.PlayerModel


class PlayerActivity : AppCompatActivity(), AnkoLogger {

    var player = PlayerModel()
    lateinit var app : MainApp
    var edit = false
    val IMAGE_REQUEST = 1 //ID I created for image
    val LOCATION_REQUEST = 2
    //var location = Location(52.245696, -7.139102, 15f)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        app = application as MainApp
        //edit = true


        //reading the player from the intent and placing its fields into view controls
        if (intent.hasExtra("player_edit")) {
            edit = true
            player = intent.extras?.getParcelable<PlayerModel>("player_edit")!!
            playerTitle.setText(player.title)
            playerDescription.setText(player.description)
            ratingBar.setRating(player.playerRating)
            playerImage.setImageBitmap(readImageFromPath(this, player.image))
            if (player.image != null) {
                chooseImage.setText(R.string.change_player_image)
            }
            btnAdd.setText(R.string.save_player)
        }

        playerLocation.setOnClickListener {
//listener for changing marker location in maps view
        val location = Location(52.245696, -7.139102, 15f)
        //if statement for if the zoom is not 0.0 we are going to use the default location that we just set in the line above
        if (player.zoom != 0f){
            location.lat =  player.lat
            location.lng = player.lng
            location.zoom = player.zoom
        }

           startActivityForResult (intentFor<MapsActivity>().putExtra("location", location), LOCATION_REQUEST)
        }




        btnAdd.setOnClickListener() {
            player.title = playerTitle.text.toString()
            player.description = playerDescription.text.toString()
            player.playerRating = ratingBar.rating
            if (player.title.isEmpty()) {
                toast(R.string.enter_player_title)
            } else {
                if (edit) {
                    app.players.update(player.copy())
                } else {
                    app.players.create(player.copy())
                }
            }
                info("add Button Pressed: ${playerTitle}")
                setResult(AppCompatActivity.RESULT_OK)
                finish() //updates the list view and finsishes the activity
            }



        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

    }

    //inflating the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_player, menu)
        if (edit && menu != null) menu.getItem(0).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    //handling the event for the cancel button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {

            R.id.item_delete -> {
                app.players.delete(player)
                finish()
            }

            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    player.image = data.getData().toString()
                    playerImage.setImageBitmap(readImage(this, resultCode, data))
                    chooseImage.setText(R.string.change_player_image)
                }
            }
            //saving the location once the maps activity has finished
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    player.lat = location.lat
                    player.lng = location.lng
                    player.zoom = location.zoom
                }
            }

        }
    }

}