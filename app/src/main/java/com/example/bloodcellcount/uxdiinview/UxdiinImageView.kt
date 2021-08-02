package com.example.bloodcellcount.uxdiinview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet

class UxdiinImageView(context: Context, attrs: AttributeSet?=null) : androidx.appcompat.widget.AppCompatImageView(context, attrs) {

    private var imagePath : String = ""
    private lateinit var bmp : Bitmap
    private lateinit var cnvs : Canvas
    private  var witdhDiff: Float = 0F
    private  var heightDiff: Float = 0F

    public fun setImage(path: String){
        this.imagePath = path
        val bmp = Bitmap.createBitmap(BitmapFactory.decodeFile(this.imagePath))
//        witdhDiff = this.width / bmp.width.toFloat()
//        Log.d("width",this.width.toString()+" "+this.height)
//        heightDiff = bmp.height*witdhDiff
        this.bmp = bmp.copy(Bitmap.Config.ARGB_8888,true)
        cnvs = Canvas(this.bmp)
    }



    public fun drawRect(x1 : Float, y1 : Float, x2 : Float, y2 : Float, scale : Int){
        val paint = Paint()
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5F
        paint.color = Color.RED
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true;

//        cnvs.drawRect(x1 *witdhDiff ,y1 *witdhDiff ,x2 *heightDiff ,y2 *heightDiff, paint);
        cnvs.drawRect(x1  ,y1  ,x2  ,y2 , paint);

    }

    private fun setImageScaledToImageView(){
        val imageRatio = bmp.width/bmp.height
        val imageViewRatio = this.width/this.height
        if(imageRatio<imageViewRatio){
            bmp = Bitmap.createScaledBitmap(bmp,this.width, bmp.width/imageRatio,true)
        }else{
            bmp = Bitmap.createScaledBitmap(bmp,bmp.height*imageRatio,this.height, true)
        }


    }

    public fun commitDrawing(){

        this.setImageBitmap(bmp);
    }
}