package final23;

import java.util.Comparator;

public class NodeComparator implements Comparator<WebNode> {

	@Override
	public int compare(WebNode o1, WebNode o2) {
		// TODO Auto-generated method stub
		if (o1.nodeScore > o2.nodeScore) {
			return 1;
		} else if (o1.nodeScore < o2.nodeScore) {
			return -1;
		}
		return 0;
	}
}
