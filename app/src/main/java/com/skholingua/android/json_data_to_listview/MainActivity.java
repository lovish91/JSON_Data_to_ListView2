package com.skholingua.android.json_data_to_listview;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	ListView list;
	Button getdata;
	static int count=0;
	static int k =0;
	boolean flag=false;

	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	private static String url = "http://serviceapi.skholingua.com/open-feeds/list_multipletext_json.php";
	private static final String TAG_OS = "Android Version List";
	private static final String TAG_VER = "Version No";
	private static final String TAG_NAME = "Version Name";
	private static final String TAG_API = "API Level";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.list);
		getdata = (Button) findViewById(R.id.getdata);
		getdata.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//if (oslist.isEmpty()) {
				new JSONParse().execute();
				//getdata.setVisibility(View.INVISIBLE);
				// }else{
				//    list.removeAllViews();
				// }
				if (flag == true) {
					k += 4;
				}
			}
		});



	}

	private class JSONParse extends AsyncTask<Void, Void, Void> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();

			pDialog = new ProgressDialog(MainActivity.this);
			pDialog.setMessage("Getting Data ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();

        }

		@Override
		protected Void doInBackground(Void... args) {

			String content = MyHttpURLConnection.getData(url);
			try {
				flag=true;
				// Getting JSON Object from URL Content
				JSONObject json = new JSONObject(content);

				JSONArray jsonArray = json.getJSONArray(TAG_OS);
				for (int i = 0 ; i < 4; i++) {
					JSONObject c = jsonArray.getJSONObject(i);
					// Storing JSON item in a Variable
					String name = c.getString(TAG_NAME);
					String ver = c.getString(TAG_VER);
					String api = c.getString(TAG_API);
					// Adding value HashMap key => value
					HashMap<String, String> map = new HashMap<String, String>();
					map.put(TAG_VER, ver);
					map.put(TAG_NAME, name);
					map.put(TAG_API, api);
					//if(count<jsonArray.length())
					//{
						oslist.add(map);
					//}
					//count++;

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void args) {
			pDialog.dismiss();
			ListAdapter adapter = new SimpleAdapter(MainActivity.this, oslist,
					R.layout.list_child, new String[] { TAG_VER, TAG_NAME,
							TAG_API }, new int[] { R.id.vers, R.id.name,
							R.id.api });
			list.setAdapter(adapter);
			list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					Toast.makeText(
							MainActivity.this,
							"You Clicked at "
									+ oslist.get(+position).get(TAG_NAME),
							Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

}
