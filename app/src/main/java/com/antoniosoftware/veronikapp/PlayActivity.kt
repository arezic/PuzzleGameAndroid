package com.antoniosoftware.veronikapp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

class PlayActivity : AppCompatActivity() {

    private var myDialog: Dialog? = null
    private var myDialogLose: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val rows = listOf(Row(listOf(imageView1_row1,imageView2_row1,imageView3_row1,imageView4_row1),imageButton_row1),
            Row(listOf(imageView1_row2,imageView2_row2,imageView3_row2,imageView4_row2),imageButton_row2),
            Row(listOf(imageView1_row3,imageView2_row3,imageView3_row3,imageView4_row3),imageButton_row3),
            Row(listOf(imageView1_row4,imageView2_row4,imageView3_row4,imageView4_row4),imageButton_row4),
            Row(listOf(imageView1_row5,imageView2_row5,imageView3_row5,imageView4_row5),imageButton_row5),
            Row(listOf(imageView1_row6,imageView2_row6,imageView3_row6,imageView4_row6),imageButton_row6))

        var currentRow = rows[0]

        val randomCombination = listOf((1..6).random(),(1..6).random(),(1..6).random(),(1..6).random())

        imageButton1.setOnClickListener {
            if (currentRow.imageButtonClicked && rows.indexOf(currentRow) != 5) {
                currentRow = rows[rows.indexOf(currentRow)+1]
            }
            clickHandleBottomButton(rows,rows.indexOf(currentRow),randomCombination,1,R.drawable.cat_hearteyes_image_framed)
        }

        imageButton2.setOnClickListener {
            if (currentRow.imageButtonClicked && rows.indexOf(currentRow) != 5) {
                currentRow = rows[rows.indexOf(currentRow)+1]
            }
            clickHandleBottomButton(rows,rows.indexOf(currentRow),randomCombination,2,R.drawable.owl_image_framed)
        }

        imageButton3.setOnClickListener {
            if (currentRow.imageButtonClicked && rows.indexOf(currentRow) != 5) {
                currentRow = rows[rows.indexOf(currentRow) + 1]
            }
            clickHandleBottomButton(rows, rows.indexOf(currentRow), randomCombination, 3, R.drawable.camel_image_framed
            )
        }

        imageButton4.setOnClickListener {
            if (currentRow.imageButtonClicked && rows.indexOf(currentRow) != 5) {
                currentRow = rows[rows.indexOf(currentRow)+1]
            }
            clickHandleBottomButton(rows,rows.indexOf(currentRow),randomCombination,4,R.drawable.butterfly_image_framed)
        }

        imageButton5.setOnClickListener {
            if (currentRow.imageButtonClicked && rows.indexOf(currentRow) != 5) {
                currentRow = rows[rows.indexOf(currentRow)+1]
            }
            clickHandleBottomButton(rows,rows.indexOf(currentRow),randomCombination,5,R.drawable.turtle_image_framed)
        }

        imageButton6.setOnClickListener {
            if (currentRow.imageButtonClicked && rows.indexOf(currentRow) != 5) {
                currentRow = rows[rows.indexOf(currentRow)+1]
            }
            clickHandleBottomButton(rows,rows.indexOf(currentRow),randomCombination,6,R.drawable.horse_image_framed)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
        //super.onBackPressed()
    }

    private fun compareGuessedCombinationWithCorrectCombination(guessedCombinationParam: List<Int>, correctCombinationParam: List<Int>): Int{
        val guessedCombination = guessedCombinationParam.toMutableList()
        val correctCombination = correctCombinationParam.toMutableList()
        var red = 0
        var yellow = 0
        for (x in 0 until 4){
            if (guessedCombination[x] == correctCombination[x]){
                red++
                guessedCombination[x] = 0
                correctCombination[x] = 0
            }
        }
        for (x in 0 until 4){
            if (correctCombination.contains(guessedCombination[x]) && guessedCombination[x] != 0) {
                yellow++
               for (i in 0 until 4) {
                   if (correctCombination[i] == guessedCombination[x]){
                       correctCombination[i] = 0
                       break
                   }
               }
                guessedCombination[x] = 0
            }
        }
        return red*10 + yellow
    }

    private fun clickHandleBottomButton(rows: List<Row>, currentRowIndex: Int = 0, randomCombination: List<Int>, imageNumber: Int, imageHex: Int) {
        val currentRow = rows[currentRowIndex]
        val currentImageViewObject = currentRow.getCurrentImageViewObject()
        currentImageViewObject?.let {
            currentImageViewObject.isEmpty = false
            currentImageViewObject.imageNumber = imageNumber
            currentImageViewObject.imageView.setImageResource(imageHex)
            currentImageViewObject.imageView.setOnClickListener {
                currentImageViewObject.imageView.setImageResource(R.drawable.background_image)
                currentImageViewObject.isEmpty = true
                currentImageViewObject.imageView.setOnClickListener(null)
                currentRow.imageButton.setImageResource(R.drawable.white_image)
                currentRow.imageButton.setOnClickListener(null)
            }
            if (currentRow.areAllImageViewObjectsFilled()){
                currentRow.imageButton.setImageResource(R.drawable.questionmark_image)
                currentRow.imageButton.setOnClickListener {
                    currentRow.imageButtonClicked = true
                    currentRow.imageButton.setOnClickListener(null)
                    if (currentRow == rows[0])
                        updateDatabaseLose()
                    val comparisonResult : Int = compareGuessedCombinationWithCorrectCombination(currentRow.getCombination(),randomCombination)
                    val imageButtonImageNumberPair = Dictionary.pairs.find { (_, value) -> value == comparisonResult }
                    imageButtonImageNumberPair?.let {
                        currentRow.imageButton.setImageResource(imageButtonImageNumberPair.imageHex)
                    }
                    for (imageViewObject in currentRow.imageViewObjects)
                        imageViewObject.imageView.setOnClickListener(null)
                    if (comparisonResult == 40){
                        updateDatabaseWin()
                        window.setFlags(
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
                        myDialog = Dialog(this)
                        showPopup(randomCombination)
                    }
                    else if (rows.indexOf(currentRow) == 5){
                        myDialogLose = Dialog(this)
                        showPopupLose(randomCombination)
                    }
                }
            }
        }
    }

    private fun showPopup(correctCombinationParam: List<Int>){
        myDialog!!.setContentView(R.layout.layout_popup_win)
        val buttonNewGame: Button = myDialog!!.findViewById(R.id.button_new_game)
        buttonNewGame.setOnClickListener {
            val intent = Intent(this,PlayActivity::class.java)
            startActivity(intent)
        }
        val imageHexList = numberListToResultImageHexList(correctCombinationParam)
        var imageViewVariable: ImageView = myDialog!!.findViewById(R.id.imageView1_win)
        imageViewVariable.setImageResource(imageHexList[0])
        imageViewVariable = myDialog!!.findViewById(R.id.imageView2_win)
        imageViewVariable.setImageResource(imageHexList[1])
        imageViewVariable = myDialog!!.findViewById(R.id.imageView3_win)
        imageViewVariable.setImageResource(imageHexList[2])
        imageViewVariable = myDialog!!.findViewById(R.id.imageView4_win)
        imageViewVariable.setImageResource(imageHexList[3])
        myDialog!!.show()
    }

    private fun showPopupLose(correctCombinationParam: List<Int>){
        myDialogLose!!.setContentView(R.layout.layout_popup_lose)
        val buttonNewGame: Button = myDialogLose!!.findViewById(R.id.button_new_game)
        buttonNewGame.setOnClickListener {
            val intent = Intent(this,PlayActivity::class.java)
            startActivity(intent)
        }
        val imageHexList = numberListToResultImageHexList(correctCombinationParam)
        var imageViewVariable: ImageView = myDialogLose!!.findViewById(R.id.imageView1_lose)
        imageViewVariable.setImageResource(imageHexList[0])
        imageViewVariable = myDialogLose!!.findViewById(R.id.imageView2_lose)
        imageViewVariable.setImageResource(imageHexList[1])
        imageViewVariable = myDialogLose!!.findViewById(R.id.imageView3_lose)
        imageViewVariable.setImageResource(imageHexList[2])
        imageViewVariable = myDialogLose!!.findViewById(R.id.imageView4_lose)
        imageViewVariable.setImageResource(imageHexList[3])
        myDialogLose!!.show()
    }

    private fun updateDatabaseWin(){
        val helper = MyDbHelper(applicationContext)
        helper.updateWins(helper.writableDatabase)
        helper.updateWinsToday(helper.writableDatabase)
    }

    private fun updateDatabaseLose(){
        val helper = MyDbHelper(applicationContext)
        helper.updateGamesPlayed(helper.writableDatabase)
        helper.updateGamesPlayedToday(helper.writableDatabase)
    }

    private fun numberListToResultImageHexList(correctCombinationParam: List<Int>): List<Int>{
        val imageHex1 = (Dictionary.pupupWindowPairs.find { (_, value) -> value == correctCombinationParam[0] })
        val imageHex2 = (Dictionary.pupupWindowPairs.find { (_, value) -> value == correctCombinationParam[1] })
        val imageHex3 = (Dictionary.pupupWindowPairs.find { (_, value) -> value == correctCombinationParam[2] })
        val imageHex4 = (Dictionary.pupupWindowPairs.find { (_, value) -> value == correctCombinationParam[3] })
        return listOf(imageHex1!!.imageHex,imageHex2!!.imageHex,imageHex3!!.imageHex,imageHex4!!.imageHex)
    }
}