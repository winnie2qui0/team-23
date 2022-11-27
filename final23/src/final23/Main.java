import java.io.IOException;
import java.util.HashMap;

public class Main {
	
	public static void main(String args[]) throws IOException {
		Search go = new Search("臺灣");
		HashMap<String, String> urls = go.query();
		Ranking rank = new Ranking();
		for(String title : urls.keySet()) {
			BuildTree urlTree = new BuildTree(urls.get(title), title);
			WebTree tree = urlTree.buildIt();
			rank.add(tree.root);
		}
		rank.output();
	}
}
