package com.antoniosoftware.veronikapp

import android.widget.ImageButton
import android.widget.ImageView

class ImageViewObject(imageViewParam: ImageView) {
    var isEmpty : Boolean = true
    var imageView : ImageView = imageViewParam
    var imageNumber : Int = 0
}

class Row(imageViewList: List<ImageView>,imageButtonParam: ImageButton) {
    var imageViewObjects : List<ImageViewObject> = listOf(ImageViewObject(imageViewList[0]),ImageViewObject(imageViewList[1]),ImageViewObject(imageViewList[2]),ImageViewObject(imageViewList[3]))
    var imageButton : ImageButton = imageButtonParam
    var imageButtonClicked: Boolean = false

    fun getCurrentImageViewObject(): ImageViewObject? {
        for (imageviewobject in imageViewObjects) {
            if (imageviewobject.isEmpty){
                return imageviewobject
            }
        }
        return null
    }

    fun areAllImageViewObjectsFilled() : Boolean {
        for (imageViewObject in imageViewObjects){
            if (imageViewObject.isEmpty){
                return false
            }
        }
        return true
    }

    fun getCombination(): List<Int>{
        return listOf(imageViewObjects[0].imageNumber,imageViewObjects[1].imageNumber,imageViewObjects[2].imageNumber,imageViewObjects[3].imageNumber)
    }
}

class ImageButtonImageNumberPair(val imageHex: Int, private val number: Int) {
    operator fun component1(): Any {
        return imageHex
    }

    operator fun component2(): Any {
        return number
    }
}

object Dictionary {
    val pairs = listOf(
        ImageButtonImageNumberPair(R.drawable.red0_0yellow_image,0),
        ImageButtonImageNumberPair(R.drawable.red0_1yellow_image,1),
        ImageButtonImageNumberPair(R.drawable.red0_2yellow_image,2),
        ImageButtonImageNumberPair(R.drawable.red0_3yellow_image,3),
        ImageButtonImageNumberPair(R.drawable.red0_4yellow_image,4),
        ImageButtonImageNumberPair(R.drawable.red1_0yellow_image,10),
        ImageButtonImageNumberPair(R.drawable.red1_1yellow_image,11),
        ImageButtonImageNumberPair(R.drawable.red1_2yellow_image,12),
        ImageButtonImageNumberPair(R.drawable.red1_3yellow_image,13),
        ImageButtonImageNumberPair(R.drawable.red2_0yellow_image,20),
        ImageButtonImageNumberPair(R.drawable.red2_1yellow_image,21),
        ImageButtonImageNumberPair(R.drawable.red2_2yellow_image,22),
        ImageButtonImageNumberPair(R.drawable.red3_0yellow_image,30),
        ImageButtonImageNumberPair(R.drawable.red3_1yellow_image,31),
        ImageButtonImageNumberPair(R.drawable.red4_0yellow_image,40))

    val pupupWindowPairs = listOf(
        ImageButtonImageNumberPair(R.drawable.cat_image,1),
        ImageButtonImageNumberPair(R.drawable.owl_image,2),
        ImageButtonImageNumberPair(R.drawable.camel_image,3),
        ImageButtonImageNumberPair(R.drawable.butterfly_image,4),
        ImageButtonImageNumberPair(R.drawable.turtle_image,5),
        ImageButtonImageNumberPair(R.drawable.horse_image,6))

}

