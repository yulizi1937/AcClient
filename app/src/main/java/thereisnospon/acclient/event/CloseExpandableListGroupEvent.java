package thereisnospon.acclient.event;


public final class CloseExpandableListGroupEvent {
	private final int mGroupIndex;


	public CloseExpandableListGroupEvent(int groupIndex) {
		mGroupIndex = groupIndex;
	}


	public int getGroupIndex() {
		return mGroupIndex;
	}
}
