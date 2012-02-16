package za.co.bank_builder.news;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import za.co.bank_builder.adapter.TweetAdapter;
import za.co.bank_builder.data.Tweet;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class Main extends Activity {

	public static final String LOG_TAG = "Gautrain";

	Handler handler = new Handler();
	ProgressDialog pd;

	/** Called when the activity is first created. 
	 * @return */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.show();
       
        AsyncTask<Void, Void, Tweet[]> loadtweets = new AsyncTask<Void, Void, Tweet[]>() {

			/* want to load all our tweets ...
			 */
			@Override
			protected Tweet[] doInBackground(Void... params) {
				
				String jsonData;
				try {
					//the search returns a jsonObject and not an array like the statuses do.....
					jsonData = getData("http://search.twitter.com/search.json?q=%40gautrain"); 
					JSONObject bigblob = new JSONObject(jsonData);
					final String jd = bigblob.getString("results");
					
					JSONArray entries = new JSONArray(jd);
					
					Tweet[] data = new Tweet[entries.length()];
						for (int i=0; i < entries.length(); i++) {
							JSONObject post = entries.getJSONObject(i);
							data[i] = new Tweet();
							data[i].setTweet(post.getString("text"));
							data[i].setUser(post.getString("from_user"));
							data[i].setAvatar(post.getString("profile_image_url"));
							//if (isCancelled()) return null;
							
						}
					return data;
					
				} catch (final Exception e) {
					handler.post(new Runnable() {
						@Override
						public void run() {
							Toast.makeText(Main.this, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
						}
					});
					return null;
				}
				
			}
			
			@Override
			protected void onPostExecute(Tweet[] tweets) {
				// TODO Auto-generated method stub
				ListView lv = (ListView) findViewById(R.id.main_list_view);
		        TweetAdapter adapter = new TweetAdapter(Main.this, tweets);
		        lv.setAdapter(adapter);
				pd.dismiss();
			}
			     	
        };
        
        loadtweets.execute(new Void[0]);
        
        setContentView(R.layout.main);
               
        
    }

	public void onRefresh(View v) {
		if (Debug.BUTTON_TEST) Log.d(Main.LOG_TAG, "onTest has been called...");

		Toast.makeText(Main.this, R.string.btn_test, Toast.LENGTH_LONG).show();
		Intent i = new Intent(Intent.ACTION_DIAL);
		i.setData(Uri.parse("tel:0828205064"));
		startActivity(Intent.createChooser(i, "Select Dialer"));

		if (Debug.BUTTON_TEST) Log.d(Main.LOG_TAG, "Dialer called..");

	}

	


	public void onExit(View v) {
		if (Debug.BUTTON_TEST) Log.d(Main.LOG_TAG, "onExit has been called...");
		
		finish();
		

	}

	/**
	 * Get data from the Internet
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String getData(String url) throws IOException {	
		URL u = new URL(url);
		HttpURLConnection c = (HttpURLConnection) u.openConnection();
		c.connect();
		if (c.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStream is = c.getInputStream();
			int len;
		    byte [] buffer = new byte[1024];
            
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) > 0) {
            	os.write(buffer, 0, len);
            	}
			is.close();
			return os.toString();
            }
		return null;
	}
} //end Main
