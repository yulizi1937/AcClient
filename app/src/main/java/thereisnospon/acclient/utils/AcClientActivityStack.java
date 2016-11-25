package thereisnospon.acclient.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public final class AcClientActivityStack implements Application.ActivityLifecycleCallbacks {
	private final Stack<Reference<Activity>> mStack = new Stack<>();

	@Override
	public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
		mStack.add(new WeakReference<>(activity));
	}

	@Override
	public void onActivityStarted(Activity activity) {

	}

	@Override
	public void onActivityResumed(Activity activity) {
	}

	@Override
	public void onActivityPaused(Activity activity) {

	}

	@Override
	public void onActivityStopped(Activity activity) {

	}

	@Override
	public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

	}

	@Override
	public void onActivityDestroyed(Activity activity) {
		removeToStack(activity);
	}

	private void removeToStack(Activity activity) {
		Iterator<Reference<Activity>> iterator = mStack.iterator();

		while (iterator.hasNext()) {
			Activity current = iterator.next()
			                           .get();
			if (current == null) {
				iterator.remove();
				continue;
			}

			if (activity != null && current.equals(activity)) {
				iterator.remove();
			}
		}
	}


	Iterator<Reference<Activity>> reverseIterator() {
		//noinspection unchecked
		List<Reference<Activity>> stackCopy = (List<Reference<Activity>>) mStack.clone();
		Collections.reverse(stackCopy);
		return stackCopy.iterator();
	}
}
