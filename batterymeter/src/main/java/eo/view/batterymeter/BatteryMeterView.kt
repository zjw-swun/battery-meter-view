package eo.view.batterymeter

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class BatteryMeterView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), BatteryMeter {

    private val batteryMeterDrawable: BatteryMeterDrawable

    override var theme: BatteryMeter.Theme
        get() = batteryMeterDrawable.theme
        set(value) {
            if (value != theme) {
                batteryMeterDrawable.theme = value
                invalidate()
            }
        }

    override var chargeLevel: Int?
        get() = batteryMeterDrawable.chargeLevel
        set(value) {
            if (value != chargeLevel) {
                batteryMeterDrawable.chargeLevel = value
                invalidate()
            }
        }

    override var criticalChargeLevel: Int?
        get() = batteryMeterDrawable.criticalChargeLevel
        set(value) {
            if (value != criticalChargeLevel) {
                batteryMeterDrawable.criticalChargeLevel = value
                invalidate()
            }
        }

    override var isCharging: Boolean
        get() = batteryMeterDrawable.isCharging
        set(value) {
            if (value != isCharging) {
                batteryMeterDrawable.isCharging = value
                invalidate()
            }
        }

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.BatteryMeterView,
            defStyleAttr,
            0
        )

        val themes = BatteryMeter.Theme.values()
        val themeIndex = typedArray.getInt(
            R.styleable.BatteryMeterView_batteryTheme,
            BatteryMeter.Theme.SHARP.ordinal
        ).coerceIn(0, themes.lastIndex)

        batteryMeterDrawable = BatteryMeterDrawable(context, themes[themeIndex])

        if (typedArray.hasValue(R.styleable.BatteryMeterView_batteryChargeLevel)) {
            chargeLevel = typedArray.getInt(R.styleable.BatteryMeterView_batteryChargeLevel, 0)
        }

        if (typedArray.hasValue(R.styleable.BatteryMeterView_batteryCriticalChargeLevel)) {
            criticalChargeLevel = typedArray.getInt(
                R.styleable.BatteryMeterView_batteryCriticalChargeLevel, 0)
        }

        isCharging = typedArray.getBoolean(R.styleable.BatteryMeterView_batteryIsCharging, isCharging)

        typedArray.recycle()
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        batteryMeterDrawable.draw(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        batteryMeterDrawable.setBounds(left, top, right, bottom);
    }
}