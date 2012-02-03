package com.example.android.BluetoothChat;

import android.content.res.Resources;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Paint.Style;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.StateSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

	
public class AnimSeekBarDrawable extends Drawable implements Runnable {
	static final int[] STATE_FOCUSED = {android.R.attr.state_focused};
	static final int[] STATE_PRESSED = {android.R.attr.state_pressed};
		
	private static final long DELAY = 30;
	private static final String TAG = "AnimSeekBarDrawable";
	private String mText;
	private float mTextWidth;
	private Drawable mProgress;
	private Paint mPaint;
	private Paint mOutlinePaint;
	private float mTextXScale;
	private int mDelta;
	private ScrollAnimation mAnimation;
	private int _maxValue;

	public AnimSeekBarDrawable(Resources res, boolean labelOnRight, int maxValue) 
	{
		mProgress = res.getDrawable(android.R.drawable.progress_horizontal);
		mText = "";
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setTypeface(Typeface.DEFAULT_BOLD);
		mPaint.setTextSize(16);
		mOutlinePaint = new Paint(mPaint);
		mOutlinePaint.setStyle(Style.STROKE);
		mOutlinePaint.setStrokeWidth(4);
		mOutlinePaint.setMaskFilter(new BlurMaskFilter(1, Blur.NORMAL));
		mTextXScale = labelOnRight? 1 : 0;
		mAnimation = new ScrollAnimation();
		_maxValue = maxValue;
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		mProgress.setBounds(bounds);
	}
	
	@Override
	protected boolean onStateChange(int[] state) {
		boolean active = StateSet.stateSetMatches(STATE_FOCUSED, state) | StateSet.stateSetMatches(STATE_PRESSED, state);
		mOutlinePaint.setColor(active? 0xffffffff : 0xffbbbbbb);
		mPaint.setColor(active? 0xff000000 : 0xff606060);
		invalidateSelf();
		return false;
	}
	
	@Override
	public boolean isStateful() {
		return true;
	}
	
	@Override
	protected boolean onLevelChange(int level) {
		//mText = (level / 100) + " %";
		mText = Integer.toString((level*_maxValue)/10000);
		mTextWidth = mOutlinePaint.measureText(mText);

		if (level < 4000 && mDelta <= 0) {
			mDelta = 1;
			// move to the right
			startScrolling(1);
		} else
		if (level > 6000 && mDelta >= 0) {
			mDelta = -1;
			// move to the left
			startScrolling(0);
		}
		
		return mProgress.setLevel(level);
	}
	
	private void startScrolling(int to) {
		mAnimation.startScrolling(mTextXScale, to);
		scheduleSelf(this, SystemClock.uptimeMillis() + DELAY);
	}

	@Override
	public void draw(Canvas canvas) {
		mProgress.draw(canvas);

		if (mAnimation.hasStarted() && !mAnimation.hasEnded()) {
			// pending animation
			mAnimation.getTransformation(AnimationUtils.currentAnimationTimeMillis(), null);
			mTextXScale = mAnimation.getCurrent();
		}
		
		Rect bounds = getBounds();
		float x = 6 + mTextXScale * (bounds.width() - mTextWidth - 6 - 6);
		float y = (bounds.height() + mPaint.getTextSize()) / 2;
		canvas.drawText(mText, x, y, mOutlinePaint);
		canvas.drawText(mText, x, y, mPaint);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	@Override
	public void setAlpha(int alpha) {
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
	}

	public void run() {
		mAnimation.getTransformation(AnimationUtils.currentAnimationTimeMillis(), null);
		// close interpolation of mTextX
		mTextXScale = mAnimation.getCurrent();
		if (!mAnimation.hasEnded()) {
			scheduleSelf(this, SystemClock.uptimeMillis() + DELAY);
		}
		invalidateSelf();
	}
	
	static class ScrollAnimation extends Animation {
		private static final long DURATION = 750;
		private float mFrom;
		private float mTo;
		private float mCurrent;
		
		public ScrollAnimation() {
			setDuration(DURATION);
			setInterpolator(new DecelerateInterpolator());
		}
		
		public void startScrolling(float from, float to) {
			mFrom = from;
			mTo = to;
			startNow();
		}
		
		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			mCurrent = mFrom + (mTo - mFrom) * interpolatedTime;
		}
		
		public float getCurrent() {
			return mCurrent;
		}
	}
}
