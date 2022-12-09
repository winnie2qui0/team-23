import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BuildTree {
	public String url;
	public String content;
	public String title;
	
	public BuildTree(String url, String title){
		this.url = url;
		this.title = title;
	}
	
	private String fetchContent(String urlStr) throws IOException {
		String retVal = "";
    	try {
    		URL u = new URL(url);
    		URLConnection conn = u.openConnection();
    		//set HTTP header
    		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107 Chrome/40.0.2214.38 Safari/537.36");
    		InputStream in = conn.getInputStream();

    		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
    		BufferedReader bufReader = new BufferedReader(inReader);
    		String line = null;

    		while((line = bufReader.readLine()) != null){
    			retVal += line;
    		}
			
    	}catch(IOException e) {
    		
    	}
    	
		return retVal;
	}
	
	private ArrayList<String> getSubUrl(String url) throws IOException{
		content = fetchContent(url);
		
		ArrayList<String> retVal = new ArrayList<String>();
		
		Document doc = Jsoup.parse(content);

		//select particular element(tag) which you want 
		Elements lis = doc.select("div");
//		lis = lis.select(".kCrYT");

		for(Element li : lis){
			try {
				String citeUrl = li.select("a").get(0).attr("href");

				if(citeUrl.substring(0, 4).equals("http")) {
					retVal.add(citeUrl);
				}	
			} catch (IndexOutOfBoundsException e) {
//					e.printStackTrace();
			}
			if(retVal.size() == 3) {
				break;
			}
		}
		
		return retVal;
	}
	
	public WebTree buildIt() throws IOException{
		WebPage rootPage = new WebPage(this.url, this.title);
		rootPage.passContent(this.content);
		WebTree tree = new WebTree(rootPage);
		
		ArrayList<String> firstFloor = this.getSubUrl(this.url);
		
		for(String firstSubUrl : firstFloor){
			WebNode f = new WebNode(new WebPage(firstSubUrl, "NoName"));
			tree.root.addChild(f);
		}
		
		return tree;
	}
}
