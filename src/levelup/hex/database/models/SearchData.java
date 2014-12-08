package levelup.hex.database.models;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class SearchData extends AsyncTask<String, Void, String> {

	private Handler handler;
	
	public SearchData(Handler handler) {
		
		this.handler = handler;
    }

	@Override
	protected String doInBackground(String... params) {
		
		HttpPost   httpPost   = new HttpPost(params[0]);
		HttpClient httpClient = new DefaultHttpClient();
		String     answer = "";
		
		try {
			
			HttpResponse httpAnswer = httpClient.execute(httpPost);
			answer = EntityUtils.toString(httpAnswer.getEntity());	
		}
		catch(Exception e){
			
			Log.i("Json", "Erro: " + e);
		}
		
		return answer;
	}
	
	@Override
	protected void onPostExecute(String response) {
		
		Bundle bundle = new Bundle();
		bundle.putString("answer", response);
		
		Message msg = new Message();
		msg.setData(bundle);
		
		handler.sendMessage(msg);
		
	}
}