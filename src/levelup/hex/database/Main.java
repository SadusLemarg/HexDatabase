package levelup.hex.database;

import levelup.hex.database.models.CheckConnection;
import levelup.hex.database.models.SearchData;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity {
	
	String jsonUrl = "http://hexonline.com.br/hex-database/json/card/all";
	
	TextView status;
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		//teste
		setContentView(R.layout.main);
		
		status = (TextView) findViewById(R.id.status);
		
		callConnectionCheck();
	}
	
	
	// TESTA CONEXÃO ====================================================================================================
	public void callConnectionCheck() {
		
		CheckConnection connection = new CheckConnection(getApplicationContext());
		Boolean connectionStatus = connection.CheckingConnection();
		 
        if (connectionStatus) {

        	status.setText("Baixando Json...");
        	StartConnection();
        }
        else {

        	status.setText("Sem Conexão");
        }
	}
	
	
	// BUSCAR OS DADOS ====================================================================================================
	public void StartConnection(){
		
		new SearchData(this.jsonMsg).execute(this.jsonUrl);
	}
	
	public Handler jsonMsg = new Handler(){
		
		@Override
		public void handleMessage(Message msg){
			
			String jsonAnswer = (String) msg.getData().getString("answer");
			
			if(jsonAnswer != ""){
				
				status.setText("É Nóis!");
				
				TreatObject(jsonAnswer);
			}
		}
	};

	
	// CAPTURA O OBJETO ====================================================================================================
	@SuppressWarnings("unused")
	private void TreatObject(String data) {
		
		try {
			
			JSONArray dataCards = new JSONArray(data);
			
			for(int i=0; i<dataCards.length(); i++){
				
				JSONObject dataCard = dataCards.getJSONObject(i);
				
				String jsonData = dataCards.getJSONObject(i).getString("name");
				showText(i, jsonData);				
			}
		}
		catch (Exception e) {
			
			status.setText("Problema com o Objeto :" + e);
		}
	}
	
	
	// MOSTRA O OBJETO ====================================================================================================
	private void showText(int i, String name){
		
		TextView tv = new TextView(this);
		tv.setText(i + " - " + name);
		
		LinearLayout ll = (LinearLayout) findViewById(R.id.main);
		ll.addView(tv);
	}
}
