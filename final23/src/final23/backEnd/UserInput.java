package backEnd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class UserInput {
	String input;
	String test = "";
	
	public UserInput(String input) throws IOException, InterruptedException{
		if(input.indexOf(" ") != -1) {
			input = input.replace(" ", "+");
		}
		this.input = input;
//		String test = trans(input);
	}
	
	
	private String fetchContent(String url) throws IOException, InterruptedException {
		String retVal = "";

		URL u = new URL(url);
		URLConnection conn = u.openConnection();
		//set HTTP header
		conn.setRequestProperty("User-agent", "Chrome/107.0.5304.107 Chrome/40.0.2214.38 Safari/537.36");
		InputStream in = conn.getInputStream();
		Thread.sleep(3000);

		InputStreamReader inReader = new InputStreamReader(in, "utf-8");
		BufferedReader bufReader = new BufferedReader(inReader);
		String line = null;

		while((line = bufReader.readLine()) != null) {
			retVal += line;
		}
		return retVal;
	}
	
	public String trans(String input) throws IOException, InterruptedException {
		String url = "https://translate.google.com.tw/?hl=zh-TW&sl=auto&tl=zh-TW&text=" + input + "&op=translate";
		
		String content = this.fetchContent(url);
		
		//using Jsoup analyze html string
		Document doc = Jsoup.parse(content);
		Elements lis = doc.select("div");
		Elements lisss = lis.select(".NwV5xd pahJge");
		
		System.out.println(lisss);
		
		String finalInput = lis.text();
		
		return finalInput;
	}
	
	
}
