package philosophicas.org.avgasyv

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class IndicatorLight(context: Context?, attrs: AttributeSet?) : View(context, attrs) {




    enum class IndicatorStatus(val value : Int)  {
        OK(0),
        ALERT(1),
        WARNING(2)
    }

    private var paintOk = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.FILL
    }

    private var paintWarning = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private var paintAlert = Paint().apply {
        color = Color.YELLOW
        style = Paint.Style.FILL
    }

    private var paintOff = Paint().apply {
        color = Color.TRANSPARENT
        style = Paint.Style.FILL
    }

    private var paintBorder = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        strokeWidth = 8F
    }

    private var myStatus = IndicatorStatus.OK

    private var isOn = true

    private var timer : TimerTask? = null

    var status : IndicatorStatus
        get() = myStatus
        set(value) {
            myStatus = value

            when(value) {
                IndicatorStatus.WARNING -> {
                    timer = Timer().scheduleAtFixedRate(1000L,1000L) {
                        isOn = isOn.not()
                        invalidate()
                    }
                }
            }
        }


    val centerX : Float
        get() = ((right - left) / 2).toFloat()

    val centerY : Float
        get() = ((bottom - top) / 2).toFloat()


    val radius : Float
        get() = (width/2).toFloat() - 8


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setPadding(8,8,8,8)

        //Draw a box
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        var paint = Paint()

        //Draw content:
        when(status) {
            IndicatorStatus.OK -> paint = paintOk
            IndicatorStatus.ALERT -> paint = paintAlert
            IndicatorStatus.WARNING -> {
                if(isOn) {
                    paint = paintWarning
                } else {
                    paint = paintOff
                }
            }
        }




        canvas?.drawCircle(centerX,
            centerY,
            radius,
            paint)


        //Draw border
        canvas?.drawCircle(centerX,
            centerY,
            radius,
            paintBorder)
    }


}