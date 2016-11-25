package thereisnospon.acclient.utils;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

public final class AcClientAnimationUtils extends AnimationUtils {
	private AcClientAnimationUtils() {
		super();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public void animateReveal(@NonNull ViewGroup parent, @NonNull View centerV, boolean reverse) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
			animateReveal(parent, getViewCenter(centerV), reverse);
		}
	}


	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private static void animateReveal(@NonNull View view, @NonNull PointF center, boolean reverse) {
		int startRadius = 0;
		int endRadius = Math.max(view.getWidth(), view.getHeight());

		int centerX = (int) center.x;
		int centerY = (int) center.y;
		float start = !reverse ?
		              startRadius :
		              endRadius * 2;
		float end = !reverse ?
		            endRadius * 2 :
		            startRadius;
		Animator reveal = ViewAnimationUtils.createCircularReveal(view, centerX, centerY, start, end);
		reveal.setInterpolator(BakedBezierInterpolator.INSTACNCE);
		reveal.setDuration(1000);
		reveal.start();
	}

	public static
	@NonNull
	PointF getViewCenter(@NonNull View v) {
		int widthHalf = v.getWidth() / 2;
		int heightHalf = v.getHeight() / 2;
		float x = ViewCompat.getX(v);
		float y = ViewCompat.getY(v);
		return new PointF(x + widthHalf, y + heightHalf);
	}

	public static
	@NonNull
	ActivityOptionsCompat createRevealOptions(@NonNull View view, @NonNull PointF center, boolean reverse) {
		int startRadius = 0;
		int endRadius = Math.max(view.getWidth(), view.getHeight());

		int centerX = (int) center.x;
		int centerY = (int) center.y;
		int start = !reverse ?
		            startRadius :
		            endRadius * 2;
		int end = !reverse ?
		          endRadius * 2 :
		          startRadius;
		return ActivityOptionsCompat.makeClipRevealAnimation(view, centerX, centerY, start, end);
	}
}
