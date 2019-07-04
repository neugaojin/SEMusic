package com.se.music.widget.lrc

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Looper
import androidx.core.content.ContextCompat
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Scroller
import com.se.music.R
import com.se.music.base.Null
import java.io.File
import java.lang.ref.WeakReference
import java.util.*

/**
 *Author: gaojin
 *Time: 2018/10/18 下午11:17
 *显示歌词控件
 */

class LrcView : View {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, attrs, defStyleAttr, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    companion object {
        const val ADJUST_DURATION: Long = 100
        const val TIMELINE_KEEP_TIME = 4 * DateUtils.SECOND_IN_MILLIS
        const val DEFAULT_DURATION = 1000
    }

    private val mLrcEntryList = ArrayList<LrcEntry>()
    private val mLrcPaint = TextPaint()
    private val mTimePaint = TextPaint()
    private lateinit var mTimeFontMetrics: Paint.FontMetrics
    private var mPlayDrawable: Drawable? = null
    private var mDividerHeight: Float = 0.toFloat()
    private var mAnimationDuration: Long = 0
    private var mNormalTextColor: Int = 0
    private var mCurrentTextColor: Int = 0
    private var mTimelineTextColor: Int = 0
    private var mTimelineColor: Int = 0
    private var mTimeTextColor: Int = 0
    private var mDrawableWidth: Float = 0f
    private var mTimeTextWidth: Float = 0f
    private var mDefaultLabel: String = Null
    private var mLrcPadding: Float = 0.toFloat()
    private var mOnPlayClickListener: OnPlayClickListener? = null
    private var mAnimator: ValueAnimator? = null
    private var mGestureDetector: GestureDetector? = null
    private var mScroller: Scroller? = null
    private var mOffset: Float = 0.toFloat()
    private var mCurrentLine: Int = 0
    private var mFlag: Any? = null
    private var isShowTimeline: Boolean = false
    private var isTouching: Boolean = false
    private var isFling: Boolean = false
    private var mTextGravity: Int = 0 // 歌词显示位置，靠左/居中/靠右

    /**
     * 播放按钮点击监听器，点击后应该跳转到指定播放位置
     */
    interface OnPlayClickListener {
        /**
         * 播放按钮被点击，应该跳转到指定播放位置
         *
         * @return 是否成功消费该事件，如果成功消费，则会更新UI
         */
        fun onPlayClick(time: Long): Boolean
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.LrcView)
        val lrcTextSize = ta.getDimension(R.styleable.LrcView_lrcTextSize, resources.getDimension(R.dimen.lrc_text_size))
        mDividerHeight = ta.getDimension(R.styleable.LrcView_lrcDividerHeight, resources.getDimension(R.dimen.lrc_divider_height))
        mAnimationDuration = ta.getInt(R.styleable.LrcView_lrcAnimationDuration, DEFAULT_DURATION).toLong()
        mNormalTextColor = ta.getColor(R.styleable.LrcView_lrcNormalTextColor, ContextCompat.getColor(context, R.color.lrc_normal_text_color))
        mCurrentTextColor = ta.getColor(R.styleable.LrcView_lrcCurrentTextColor, ContextCompat.getColor(context, R.color.lrc_current_text_color))
        mTimelineTextColor = ta.getColor(R.styleable.LrcView_lrcTimelineTextColor, ContextCompat.getColor(context, R.color.lrc_timeline_text_color))
        mLrcPadding = ta.getDimension(R.styleable.LrcView_lrcPadding, 0f)
        mTimelineColor = ta.getColor(R.styleable.LrcView_lrcTimelineColor, resources.getColor(R.color.lrc_timeline_color))
        mTimeTextColor = ta.getColor(R.styleable.LrcView_lrcTimeTextColor, ContextCompat.getColor(context, R.color.lrc_time_text_color))
        mTextGravity = ta.getInteger(R.styleable.LrcView_lrcTextGravity, LrcEntry.GRAVITY_CENTER)

        // 暂时 xml中必须设置
        mDefaultLabel = ta.getString(R.styleable.LrcView_lrcLabel) ?: Null
        mPlayDrawable = ta.getDrawable(R.styleable.LrcView_lrcPlayDrawable)

        val timelineHeight = ta.getDimension(R.styleable.LrcView_lrcTimelineHeight, resources.getDimension(R.dimen.lrc_timeline_height))
        val timeTextSize = ta.getDimension(R.styleable.LrcView_lrcTimeTextSize, resources.getDimension(R.dimen.lrc_time_text_size))

        ta.recycle()

        mDrawableWidth = resources.getDimension(R.dimen.lrc_drawable_width)
        mTimeTextWidth = resources.getDimension(R.dimen.lrc_time_width)

        mLrcPaint.isAntiAlias = true
        mLrcPaint.textSize = lrcTextSize
        mLrcPaint.textAlign = Paint.Align.LEFT
        mTimePaint.isAntiAlias = true
        mTimePaint.textSize = timeTextSize
        mTimePaint.textAlign = Paint.Align.CENTER

        mTimePaint.strokeWidth = timelineHeight
        mTimePaint.strokeCap = Paint.Cap.ROUND
        mTimeFontMetrics = mTimePaint.fontMetrics

        mGestureDetector = GestureDetector(context, mSimpleOnGestureListener)
        mGestureDetector!!.setIsLongpressEnabled(false)
        mScroller = Scroller(context)
    }

    fun setNormalColor(normalColor: Int) {
        mNormalTextColor = normalColor
        postInvalidate()
    }

    fun setCurrentColor(currentColor: Int) {
        mCurrentTextColor = currentColor
        postInvalidate()
    }

    fun setTimelineTextColor(timelineTextColor: Int) {
        mTimelineTextColor = timelineTextColor
        postInvalidate()
    }

    fun setTimelineColor(timelineColor: Int) {
        mTimelineColor = timelineColor
        postInvalidate()
    }

    fun setTimeTextColor(timeTextColor: Int) {
        mTimeTextColor = timeTextColor
        postInvalidate()
    }

    /**
     * 设置播放按钮点击监听器
     *
     * @param onPlayClickListener 如果为非 null ，则激活歌词拖动功能，否则将将禁用歌词拖动功能
     */
    fun setOnPlayClickListener(onPlayClickListener: OnPlayClickListener) {
        mOnPlayClickListener = onPlayClickListener
    }

    /**
     * 设置歌词为空时屏幕中央显示的文字，如“暂无歌词”
     */
    fun setLabel(label: String) {
        runOnUi(Runnable {
            mDefaultLabel = label
            invalidate()
        })
    }

    /**
     * 加载歌词文件
     *
     * @param lrcFile 歌词文件
     */
    fun loadLrc(lrcFile: File) {
        runOnUi(Runnable {
            reset()
            setFlag(lrcFile)
            FileAsyncTask(this, lrcFile).execute(lrcFile)
        })
    }

    /**
     * 加载歌词文本
     *
     * @param lrcText 歌词文本
     */
    fun loadLrc(lrcText: String) {
        runOnUi(Runnable {
            reset()
            setFlag(lrcText)
            StringAsyncTask(this, lrcText).execute(lrcText)
        })
    }

    /**
     * 加载在线歌词
     *
     * @param lrcUrl 歌词文件的网络地址
     */
    fun loadLrcByUrl(lrcUrl: String) {
    }

    /**
     * 歌词是否有效
     *
     * @return true，如果歌词有效，否则false
     */
    fun hasLrc(): Boolean {
        return !mLrcEntryList.isEmpty()
    }

    /**
     * 刷新歌词
     *
     * @param time 当前播放时间
     */
    fun updateTime(time: Long) {
        runOnUi(Runnable {
            if (!hasLrc()) {
                return@Runnable
            }

            val line = findShowLine(time)
            if (line != mCurrentLine) {
                mCurrentLine = line
                if (!isShowTimeline) {
                    scrollTo(line)
                } else {
                    invalidate()
                }
            }
        })
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            initEntryList()
            val l = (mTimeTextWidth - mDrawableWidth) / 2
            val t = height / 2 - mDrawableWidth / 2
            val r = l + mDrawableWidth
            val b = t + mDrawableWidth
            mPlayDrawable!!.setBounds(l.toInt(), t.toInt(), r.toInt(), b.toInt())
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerY = height / 2

        // 无歌词文件
        if (!hasLrc()) {
            mLrcPaint.color = mCurrentTextColor

            @SuppressLint("DrawAllocation")
            val staticLayout = StaticLayout(mDefaultLabel, mLrcPaint, getLrcWidth().toInt(), Layout.Alignment.ALIGN_CENTER, 1f, 0f, false)

            drawText(canvas, staticLayout, centerY.toFloat())
            return
        }

        val centerLine = getCenterLine()

        if (isShowTimeline) {
            mPlayDrawable!!.draw(canvas)

            mTimePaint.color = mTimelineColor
            canvas.drawLine(mTimeTextWidth, centerY.toFloat(), width - mTimeTextWidth, centerY.toFloat(), mTimePaint)

            mTimePaint.color = mTimeTextColor
            val timeText = formatTime(mLrcEntryList[centerLine].time)
            val timeX = (width - mTimeTextWidth / 2)
            val timeY = centerY - (mTimeFontMetrics.descent + mTimeFontMetrics.ascent) / 2
            canvas.drawText(timeText, timeX, timeY, mTimePaint)
        }

        canvas.translate(0f, mOffset)

        var y = 0f
        for (i in mLrcEntryList.indices) {
            if (i > 0) {
                y += (mLrcEntryList.get(i - 1).getHeight() + mLrcEntryList.get(i).getHeight()) / 2 + mDividerHeight
            }
            if (i == mCurrentLine) {
                mLrcPaint.color = mCurrentTextColor
            } else if (isShowTimeline && i == centerLine) {
                mLrcPaint.color = mTimelineTextColor
            } else {
                mLrcPaint.color = mNormalTextColor
            }
            drawText(canvas, mLrcEntryList[i].staticLayout, y)
        }
    }

    /**
     * 画一行歌词
     *
     * @param y 歌词中心 Y 坐标
     */
    private fun drawText(canvas: Canvas, staticLayout: StaticLayout, y: Float) {
        canvas.save()
        canvas.translate(mLrcPadding, y - staticLayout.height / 2)
        staticLayout.draw(canvas)
        canvas.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP || event.action == MotionEvent.ACTION_CANCEL) {
            isTouching = false
            if (hasLrc() && !isFling) {
                adjustCenter()
                postDelayed(hideTimelineRunnable, TIMELINE_KEEP_TIME)
            }
        }
        return mGestureDetector!!.onTouchEvent(event)
    }

    private val mSimpleOnGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            if (hasLrc() && mOnPlayClickListener != null) {
                mScroller!!.forceFinished(true)
                removeCallbacks(hideTimelineRunnable)
                isTouching = true
                isShowTimeline = true
                invalidate()
                return true
            }
            return super.onDown(e)
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            if (hasLrc()) {
                mOffset += -distanceY
                mOffset = Math.min(mOffset, getOffset(0))
                mOffset = Math.max(mOffset, getOffset(mLrcEntryList.size - 1))
                invalidate()
                return true
            }
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (hasLrc()) {
                mScroller!!.fling(0, mOffset.toInt(), 0, velocityY.toInt(), 0, 0, getOffset(mLrcEntryList.size - 1).toInt(), getOffset(0).toInt())
                isFling = true
                return true
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            if (hasLrc() && isShowTimeline && mPlayDrawable!!.bounds.contains(e.x.toInt(), e.y.toInt())) {
                val centerLine = getCenterLine()
                val centerLineTime = mLrcEntryList.get(centerLine).time
                // onPlayClick 消费了才更新 UI
                if (mOnPlayClickListener != null && mOnPlayClickListener!!.onPlayClick(centerLineTime)) {
                    isShowTimeline = false
                    removeCallbacks(hideTimelineRunnable)
                    mCurrentLine = centerLine
                    invalidate()
                    return true
                }
            }
            return super.onSingleTapConfirmed(e)
        }
    }

    private val hideTimelineRunnable = Runnable {
        if (hasLrc() && isShowTimeline) {
            isShowTimeline = false
            scrollTo(mCurrentLine)
        }
    }

    override fun computeScroll() {
        if (mScroller!!.computeScrollOffset()) {
            mOffset = mScroller!!.currY.toFloat()
            invalidate()
        }

        if (isFling && mScroller!!.isFinished) {
            isFling = false
            if (hasLrc() && !isTouching) {
                adjustCenter()
                postDelayed(hideTimelineRunnable, TIMELINE_KEEP_TIME)
            }
        }
    }

    override fun onDetachedFromWindow() {
        removeCallbacks(hideTimelineRunnable)
        super.onDetachedFromWindow()
    }

    private fun onLrcLoaded(entryList: List<LrcEntry>?) {
        if (entryList != null && !entryList.isEmpty()) {
            mLrcEntryList.addAll(entryList)
        }

        initEntryList()
        invalidate()
    }

    private fun initEntryList() {
        if (!hasLrc() || width == 0) {
            return
        }

        mLrcEntryList.sort()

        for (lrcEntry in mLrcEntryList) {
            lrcEntry.init(mLrcPaint, getLrcWidth().toInt(), mTextGravity)
        }

        mOffset = (height / 2).toFloat()
    }

    private fun reset() {
        endAnimation()
        mScroller!!.forceFinished(true)
        isShowTimeline = false
        isTouching = false
        isFling = false
        removeCallbacks(hideTimelineRunnable)
        mLrcEntryList.clear()
        mOffset = 0f
        mCurrentLine = 0
        invalidate()
    }

    /**
     * 滚动到某一行
     */
    private fun scrollTo(line: Int) {
        scrollTo(line, mAnimationDuration)
    }

    /**
     * 将中心行微调至正中心
     */
    private fun adjustCenter() {
        scrollTo(getCenterLine(), ADJUST_DURATION)
    }

    private fun scrollTo(line: Int, duration: Long) {
        val offset = getOffset(line)
        endAnimation()

        mAnimator = ValueAnimator.ofFloat(mOffset, offset)
        mAnimator!!.duration = duration
        mAnimator!!.interpolator = LinearInterpolator()
        mAnimator!!.addUpdateListener { animation ->
            mOffset = animation.animatedValue as Float
            invalidate()
        }
        mAnimator!!.start()
    }

    private fun endAnimation() {
        if (mAnimator != null && mAnimator!!.isRunning) {
            mAnimator!!.end()
        }
    }

    /**
     * 二分法查找当前时间应该显示的行数（最后一个 <= time 的行数）
     */
    private fun findShowLine(time: Long): Int {
        var left = 0
        var right = mLrcEntryList.size
        while (left <= right) {
            val middle = (left + right) / 2
            val middleTime = mLrcEntryList.get(middle).time

            if (time < middleTime) {
                right = middle - 1
            } else {
                if (middle + 1 >= mLrcEntryList.size || time < mLrcEntryList.get(middle + 1).time) {
                    return middle
                }

                left = middle + 1
            }
        }
        return 0
    }

    private fun getCenterLine(): Int {
        var centerLine = 0
        var minDistance = java.lang.Float.MAX_VALUE
        for (i in mLrcEntryList.indices) {
            if (Math.abs(mOffset - getOffset(i)) < minDistance) {
                minDistance = Math.abs(mOffset - getOffset(i))
                centerLine = i
            }
        }
        return centerLine
    }

    private fun getOffset(line: Int): Float {
        if (mLrcEntryList[line].offset == java.lang.Float.MIN_VALUE) {
            var offset = (height / 2).toFloat()
            for (i in 1..line) {
                offset -= (mLrcEntryList[i - 1].getHeight() + mLrcEntryList[i].getHeight()) / 2 + mDividerHeight
            }
            mLrcEntryList[line].offset = offset
        }

        return mLrcEntryList[line].offset
    }

    private fun getLrcWidth(): Float {
        return width - mLrcPadding * 2
    }

    private fun runOnUi(r: Runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            r.run()
        } else {
            post(r)
        }
    }

    private fun getFlag(): Any? {
        return mFlag
    }

    private fun setFlag(flag: Any?) {
        this.mFlag = flag
    }

    class FileAsyncTask(lrcView: LrcView, private val lrcFile: File) : AsyncTask<File, Int, List<LrcEntry>?>() {

        private val weakReference = WeakReference<LrcView>(lrcView)

        override fun doInBackground(vararg params: File?): List<LrcEntry>? {
            return parseLrc(params[0])
        }

        override fun onPostExecute(result: List<LrcEntry>?) {
            val view = weakReference.get()
            if (view != null) {
                if (view.getFlag() === lrcFile) {
                    view.onLrcLoaded(result)
                    view.setFlag(null)
                }
            }
        }
    }

    class StringAsyncTask(lrcView: LrcView, private val lrcString: String) : AsyncTask<String, Int, List<LrcEntry>?>() {

        private val weakReference = WeakReference<LrcView>(lrcView)

        override fun doInBackground(vararg params: String): List<LrcEntry>? {
            return parseLrc(params[0])
        }

        override fun onPostExecute(result: List<LrcEntry>?) {
            val view = weakReference.get()
            if (view != null) {
                if (view.getFlag() === lrcString) {
                    view.onLrcLoaded(result)
                    view.setFlag(null)
                }
            }
        }
    }
}