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
	
	private String fetchContent(String urlStr) throws IOException
	{
		String retVal = "";
    	try {
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			InputStream in = conn.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
			String line = null;
			
			while ((line = br.readLine()) != null){
			    retVal = retVal + line + "\n";
			}
    	}catch(IOException e){
    		
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
		
	
		
		for(Element li : lis)
		{
			try 
			{
				String citeUrl = li.select("a").get(0).attr("href");

				if(citeUrl.substring(0, 4).equals("http")) {
					retVal.add(citeUrl);
				}
				
			} catch (IndexOutOfBoundsException e) 
			{
//					e.printStackTrace();
			}
			
			if(retVal.size()>=3) {
				break;

			}
		}
		
		return retVal;
	}
	
	public WebTree buildIt() throws IOException{
		WebPage rootPage = new WebPage(this.url, this.title);		
		WebTree tree = new WebTree(rootPage);
		
		ArrayList<String> firstFloor = this.getSubUrl(this.url);
		
		for(String firstSubUrl : firstFloor){
			WebNode f = new WebNode(new WebPage(firstSubUrl, "NoName"));
			tree.root.addChild(f);
		}
		
		return tree;
	}
}
