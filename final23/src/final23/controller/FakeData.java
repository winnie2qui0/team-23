package controller;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.json.simple.JSONArray;

import backEnd.*;

public class FakeData {
	
	private JSONArray result = new JSONArray();
	

	
	public JSONArray getResult() {
		return result;
	}
	
	public void setResult(String string) throws IOException, InterruptedException, ExecutionException, TimeoutException {
		
		BackEndMain main = new BackEndMain();
		result = main.main(string);
		
	}

}
