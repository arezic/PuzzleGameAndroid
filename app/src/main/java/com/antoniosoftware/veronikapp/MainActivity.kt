package com.antoniosoftware.veronikapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        displayStatistics()

        button_play.setOnClickListener {
            val intent = Intent(this,PlayActivity::class.java)
            startActivity(intent)
        }

        button_credits.setOnClickListener {
            val intent = Intent(this,CreditsActivity::class.java)
            startActivity(intent)
        }
    }


    private fun displayStatistics(){
        val helper = MyDbHelper(applicationContext)
        val db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM WINS",null)

        var numberOfWins = "0"
        var numberOfGamesPlayed = "0"
        var numberOfWinsToday = "0"
        var numberOfGamesPlayedToday = "0"

        if (rs.moveToNext())
            numberOfWins = rs.getString(1)
        textView_total_wins.text = textView_total_wins.text.toString() + " " + numberOfWins

        rs = db.rawQuery("SELECT * FROM GAMES_PLAYED",null)
        if (rs.moveToNext())
            numberOfGamesPlayed = rs.getString(1)
        textView_total_games_played.text = textView_total_games_played.text.toString() + " " + numberOfGamesPlayed

        textView_wins_percentage.text = textView_wins_percentage.text.toString() + " " + ((numberOfWins.toFloat()/numberOfGamesPlayed.toFloat())*100).toInt() + "%"

        helper.resetTodayValues(helper.writableDatabase)

        rs = db.rawQuery("SELECT * FROM WINS_TODAY",null)
        if (rs.moveToNext())
            numberOfWinsToday = rs.getString(1)
        textView_wins_today.text = textView_wins_today.text.toString() + " " + numberOfWinsToday

        rs = db.rawQuery("SELECT * FROM GAMES_PLAYED_TODAY",null)
        if (rs.moveToNext())
            numberOfGamesPlayedToday = rs.getString(1)
        textView_games_played_today.text = textView_games_played_today.text.toString() + " " + numberOfGamesPlayedToday

        rs.close()
    }
}