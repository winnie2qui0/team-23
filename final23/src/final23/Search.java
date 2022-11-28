import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Search {

	public String searchKeyword;
	public String url;
	public String content;
	
	public Search(String searchKeyword)
	{
		this.searchKeyword = searchKeyword;
<<<<<<< HEAD
		this.url = "http://www.google.com/search?q="+searchKeyword+"&oe=utf8&num=20";
//		this.url = "https://tw.search.yahoo.com/search?p="+searchKeyword+"&fr=yfp-search-sb";

=======
		this.url = "http://www.google.com/search?q="+searchKeyword+"+笑話"+"&oe=utf8&num=20";
>>>>>>> branch 'master' of https://github.com/winnie2qui0/team-23
	}
	
	private String fetchContent() throws IOException
	{
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		//set HTTP header
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107 Chrome/40.0.2214.38 Safari/537.36");
//		conn.userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:49.0) Gecko/20100101 Firefox/49.0").ignoreHttpErrors(true).followRedirects(true).timeout(100000).ignoreContentType(true).get();
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
	
	public HashMap<String, String> query() throws IOException
	{
		if(content == null)
		{
			content = fetchContent();
		}

		HashMap<String, String> retVal = new HashMap<String, String>();
		
		
		/* 
		 * some Jsoup source
		 * https://jsoup.org/apidocs/org/jsoup/nodes/package-summary.html
		 * https://www.1ju.org/jsoup/jsoup-quick-start
 		 */
		
		//using Jsoup analyze html string
		Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("div");
//		Elements lis = doc.select("a[href]");
		lis = lis.select(".kCrYT");
		System.out.println(lis);
		
		for(Element li : lis)
		{
			try 
			{
				String citeUrl = li.select("a").get(0).attr("href");
<<<<<<< HEAD
				int useLessUrl = citeUrl.indexOf("&sa=");
				System.out.println(useLessUrl);
				citeUrl = citeUrl.substring(7, useLessUrl);
				System.out.println(citeUrl);
=======
				String result = "";
				try {
				    result = java.net.URLDecoder.decode(citeUrl, StandardCharsets.UTF_8.name());
				} catch (UnsupportedEncodingException e) {
				    // not going to happen - value came from JDK's own StandardCharsets
				}
				int last = result.indexOf("&sa");
				result =  result.substring(7, last);
>>>>>>> branch 'master' of https://github.com/winnie2qui0/team-23
				String title = li.select("a").get(0).select(".vvjwJb").text();
//				System.out.println(title);
				
				if(title.equals("")) 
				{
					continue;
				}
				
				//put title and pair into HashMap
				retVal.put(title, result);

			} catch (IndexOutOfBoundsException e) 
			{
//					e.printStackTrace();
			}
		}
		return retVal;
	}
}

