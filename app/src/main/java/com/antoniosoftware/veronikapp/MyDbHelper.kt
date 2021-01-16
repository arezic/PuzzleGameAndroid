package com.antoniosoftware.veronikapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, "STATISTICS_DB",null, 3) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE WINS(WINS_ID INTEGER PRIMARY KEY AUTOINCREMENT, NUMBER_OF_WINS INTEGER)")
        db?.execSQL("CREATE TABLE GAMES_PLAYED(GAMES_PLAYED_ID INTEGER PRIMARY KEY AUTOINCREMENT, NUMBER_OF_GAMES_PLAYED INTEGER)")
        db?.execSQL("CREATE TABLE GAMES_PLAYED_TODAY(GPT_ID INTEGER PRIMARY KEY AUTOINCREMENT, NOGPT INTEGER, DATE_TODAY DATE)")
        db?.execSQL("CREATE TABLE WINS_TODAY(WT_ID INTEGER PRIMARY KEY AUTOINCREMENT, NOWT INTEGER, DATE_TODAY DATE)")

        db?.execSQL("INSERT INTO WINS(NUMBER_OF_WINS) VALUES(0)")
        db?.execSQL("INSERT INTO GAMES_PLAYED(NUMBER_OF_GAMES_PLAYED) VALUES(0)")
        db?.execSQL("INSERT INTO WINS_TODAY(NOWT,DATE_TODAY) VALUES(0,(SELECT date('now')))")
        db?.execSQL("INSERT INTO GAMES_PLAYED_TODAY(NOGPT, DATE_TODAY) VALUES(0,(SELECT date('now')))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun updateWins(db: SQLiteDatabase?){
        db?.execSQL("UPDATE WINS SET NUMBER_OF_WINS = NUMBER_OF_WINS + 1")
    }

    fun updateGamesPlayed(db: SQLiteDatabase?){
        db?.execSQL("UPDATE GAMES_PLAYED SET NUMBER_OF_GAMES_PLAYED = NUMBER_OF_GAMES_PLAYED + 1")
    }

    fun updateWinsToday(db: SQLiteDatabase?){
        db?.execSQL("UPDATE WINS_TODAY SET NOWT = 0 WHERE DATE_TODAY != (SELECT date('now'))")
        db?.execSQL("UPDATE WINS_TODAY SET DATE_TODAY = (SELECT date('now')) WHERE DATE_TODAY != (SELECT date('now'))")
        db?.execSQL("UPDATE WINS_TODAY SET NOWT = NOWT + 1")
    }

    fun updateGamesPlayedToday(db: SQLiteDatabase?){
        db?.execSQL("UPDATE GAMES_PLAYED_TODAY SET NOGPT = 0 WHERE DATE_TODAY != (SELECT date('now'))")
        db?.execSQL("UPDATE GAMES_PLAYED_TODAY SET DATE_TODAY = (SELECT date('now')) WHERE DATE_TODAY != (SELECT date('now'))")
        db?.execSQL("UPDATE GAMES_PLAYED_TODAY SET NOGPT = NOGPT + 1")
    }

    fun resetTodayValues(db: SQLiteDatabase?){
        db?.execSQL("UPDATE WINS_TODAY SET NOWT = 0 WHERE DATE_TODAY != (SELECT date('now'))")
        db?.execSQL("UPDATE WINS_TODAY SET DATE_TODAY = (SELECT date('now')) WHERE DATE_TODAY != (SELECT date('now'))")
        db?.execSQL("UPDATE GAMES_PLAYED_TODAY SET NOGPT = 0 WHERE DATE_TODAY != (SELECT date('now'))")
        db?.execSQL("UPDATE GAMES_PLAYED_TODAY SET DATE_TODAY = (SELECT date('now')) WHERE DATE_TODAY != (SELECT date('now'))")
    }
}