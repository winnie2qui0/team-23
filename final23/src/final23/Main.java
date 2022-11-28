import java.io.IOException;
import java.util.HashMap;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class Main {
	
	public static void main(String args[]) throws IOException {
<<<<<<< HEAD
		Search go = new Search("Justin");
		System.out.println("hello");
=======
		Search go = new Search("臺灣");
>>>>>>> branch 'master' of https://github.com/winnie2qui0/team-23
		HashMap<String, String> urls = go.query();
		System.out.println(urls);
		Ranking rank = new Ranking();
		for(String title : urls.keySet()) {
<<<<<<< HEAD
			System.out.println(title);
			String encodedURL = urls.get(title);
			System.out.println("  encoded url ");
			System.out.println(encodedURL);
			System.out.println("  decoded url ");
			String decodedURL = "";
			try {
			      // 進行 URL 百分比解碼
			      String url = URLDecoder.decode(encodedURL, "UTF-8");

			      // 輸出結果
			      System.out.println(url);
			      decodedURL = url;

			    } catch (UnsupportedEncodingException e) {
			      // 例外處理 ...
			    	System.out.println("url decode problem");
			    }
			BuildTree urlTree = new BuildTree(decodedURL, title);
=======
//			System.out.print(title);
//			System.out.println(urls.get(title));
			BuildTree urlTree = new BuildTree(urls.get(title), title);
>>>>>>> branch 'master' of https://github.com/winnie2qui0/team-23
			WebTree tree = urlTree.buildIt();
			tree.setPostOrderScore();
			rank.add(tree.root);
		}
		//以下為test
//		BuildTree urlTree = new BuildTree("https://www.ptt.cc/bbs/joke/M.1669395372.A.65F.html", "pttJoke1");
//		WebTree tree = urlTree.buildIt();
//		tree.setPostOrderScore();
//		rank.add(tree.root);
//		System.out.println(tree.root.children);
//		urlTree = new BuildTree("https://www.ptt.cc/bbs/joke/M.1669037987.A.8BD.html", "pttJoke2");
//		tree = urlTree.buildIt();
//		tree.setPostOrderScore();
//		rank.add(tree.root);
<<<<<<< HEAD
//		rank.output();
=======
		rank.output();
>>>>>>> branch 'master' of https://github.com/winnie2qui0/team-23
	}
}
