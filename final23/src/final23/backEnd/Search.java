package backEnd;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Search {
	public String searchKeyword;
	public String url;
	public String content;
	
	public Search(String searchKeyword) {
		this.searchKeyword = searchKeyword;
		this.url = "http://www.google.com/search?q=" + searchKeyword + "&oe=utf8&num=20";
	}
	
	private String fetchContent() throws IOException {
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		//set HTTP header
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107 Chrome/40.0.2214.38 Safari/537.36");
		InputStream in = conn.getInputStream();

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line = bufReader.readLine()) != null) {
			retVal += line;
		}
		return retVal;
	}
	
	public HashMap<String, String> query(int limitation) throws IOException {
		if(content == null) {
			content = fetchContent();
		}

		HashMap<String, String> retVal = new HashMap<String, String>();
		
		//using Jsoup analyze html string
		Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("div");
//		Elements lis = doc.select("a[href]");
		lis = lis.select(".kCrYT");
		
		for(Element li : lis) {
			try {
				String citeUrl = li.select("a").get(0).attr("href");
				int useLessUrl = citeUrl.indexOf("&sa=");
//				System.out.println(citeUrl);
//				System.out.println(useLessUrl);
				citeUrl = citeUrl.substring(7, useLessUrl);
				String title = li.select("a").get(0).select(".vvjwJb").text();
				
				if(title.equals("")) {
					continue;
				}
				
				boolean ban = false;
				File file = new File("BlackList.txt");
				Scanner scanner = new Scanner(file);
				while(scanner.hasNextLine()){
					String  negUrl = scanner.next();
					if(citeUrl.indexOf(negUrl) != -1) {
		    			ban = true;
		    			break;
		    		}
				}
				
				//put title and pair into HashMap
				if(retVal.size() <= limitation && !ban) {
					retVal.put(title, citeUrl);
				}

			} catch (IndexOutOfBoundsException e) {
//					e.printStackTrace();
			}
		}
		return retVal;
	}
	
	public ArrayList<String> semanticsAnalysis() throws IOException {
		if(content == null) {
			content = fetchContent();
		}
		
		ArrayList<String> saList = new ArrayList<String>();
		
		//using Jsoup analyze html string
		Document doc = Jsoup.parse(content);
		
		//select particular element(tag) which you want 
		Elements lis = doc.select("div");
		lis = lis.select(".gGQDvd");
		lis = lis.select(".BNeawe");
		
		for(Element li : lis) {
			saList.add(li.text());
		}
		
		return saList;
	}
}

