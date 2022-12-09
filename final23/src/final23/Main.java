import java.util.HashMap;
import java.net.URLDecoder;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main {
	public static void main(String args[]) throws IOException {
		long startTime = System.nanoTime();
		Search go = new Search("政大");

		HashMap<String, String> urls = go.query();
		urls.entrySet().forEach(entry -> {
			System.out.println(entry.getKey() + "\n" + entry.getValue());
		});
		Ranking rank = new Ranking();
		System.out.println("\nFUCK\n");
		for (String title : urls.keySet()) {
			System.out.println(title);
			String encodedURL = urls.get(title);
			System.out.println("  encoded url ");
			System.out.println(encodedURL);
//			System.out.println("  decoded url ");
//			String decodedURL = "";
//			try {
//				// 進行 URL 百分比解碼
//				String url = URLDecoder.decode(encodedURL, "UTF-8");
//				// 輸出結果
//				System.out.println(url);
//				decodedURL = url;
//			} catch (UnsupportedEncodingException e) {
//				// 例外處理 ...
//				System.out.println("url decode problem");
//			}
			BuildTree urlTree = new BuildTree(encodedURL, title);//0sec
			WebTree tree = urlTree.buildIt();//19sec
			tree.setPostOrderScore();//34sec
			rank.add(tree.root);//36sec
		}
//		以下為test
//		BuildTree urlTree = new BuildTree("https://www.ptt.cc/bbs/joke/M.1669395372.A.65F.html", "pttJoke1");
//		WebTree tree = urlTree.buildIt();
//		tree.setPostOrderScore();
//		rank.add(tree.root);
//		System.out.println(tree.root.children);
//		urlTree = new BuildTree("https://www.ptt.cc/bbs/joke/M.1669037987.A.8BD.html", "pttJoke2");
//		tree = urlTree.buildIt();
//		tree.setPostOrderScore();
//		rank.add(tree.root);

		rank.output();
		long endTime = System.nanoTime();
		System.out.println("執行時間： " + (endTime - startTime) / 1000000000 + " S");
	}
}
