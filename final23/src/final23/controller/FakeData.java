package controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import backEnd.*;

public class FakeData {
	
	private String result = "This is result : 12312312312312312";
	

	
	public String getResult() {
		return result + "\n total time : 2s";
	}
	
	public String getResult(String string) throws IOException, InterruptedException, ExecutionException, TimeoutException {
		
		BackEndMain main = new BackEndMain();
		main.main(string);
		
		
		return result;
		
	}


}
