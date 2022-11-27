package final23;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class BuildTree {
	
	public String url;
	public String content;
	public String title;
	
	public BuildTree(String url, String title){
		this.url = url;
		this.title = title;
	}
	
	private String fetchContent(String url) throws IOException
	{
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		//set HTTP header
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107");
		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line = bufReader.readLine()) != null)
		{
			retVal += line;
		}
		return retVal;
	}
	
	private ArrayList<String> getSubUrl(String url){

		content = fetchContent(url);
		
		
		ArrayList<String> retVal = new ArrayList<String>();
		
		Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("div");
		lis = lis.select(".kCrYT");
		
		for(Element li : lis)
		{
			try 
			{
				String citeUrl = li.select("a").get(0).attr("href");

				retVal.add(citeUrl);

			} catch (IndexOutOfBoundsException e) 
			{
					e.printStackTrace();
			}
		}
		
		return retVal;
	}
	
	public WebTree buildIt(){
		WebPage rootPage = new WebPage(this.url, this.title);		
		WebTree tree = new WebTree(rootPage);
		
		ArrayList<String> firstFloor = this.getSubUrl(this.url);
		
		for(String firstSubUrl : firstFloor){
			ArrayList<String> secondFloor = this.getSubUrl(firstSubUrl);
			WebNode f = new WebNode(new WebPage(firstSubUrl));
			for(String secondSubUrl : secondFloor) {
				f.addChild(new WebNode(new WebPage(secondSubUrl)));
			}
			tree.root.addChild(f);
		}
		
		return tree;
	}
}
