package thereisnospon.acclient.utils;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.lang.ref.Reference;
import java.util.Iterator;

/**
 * Created by xzhao on 25.11.16.
 */

public final class AcClientActivityCompat extends ActivityCompat {
	private AcClientActivityCompat() {}
	private static final boolean IS_JELLY_BEAN_MIN = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;


	public static void finishAffinityCompat(@NonNull  Activity activity, @NonNull AcClientActivityStack activityStack) {
		if (IS_JELLY_BEAN_MIN) {
			android.support.v4.app.ActivityCompat.finishAffinity(activity);
			return;
		}
		Iterator<Reference<Activity>> iterator = activityStack.reverseIterator();
		Reference<Activity> reference;
		while (iterator.hasNext()) {
			reference = iterator.next();
			Activity current = reference.get();
			if (current == null) {
				continue;
			}

			current.finish();
		}
	}
}
