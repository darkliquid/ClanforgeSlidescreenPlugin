package uk.co.darkliquid.android.slidescreenplugins.clanforge;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.SimpleAdapter;

public class ServerDetailsActivity extends ListActivity {
	private static String[] FIELDS = {
		"Name", "Game", "Map", "Host", "Status", "Number of players", "Max players",
		"Number of spectators", "Max spectators", "Ping"
	};
	@Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle serverInfo = intent.getExtras();


        List<HashMap<String, String>> groupData = new ArrayList<HashMap<String, String>>();

        for(String key : FIELDS) {
        	HashMap<String, String> group = new HashMap<String, String>();
        	group.put("Label", key);
        	group.put("Value", serverInfo.getString(key));
        	groupData.add(group);
        }

        setTitle(serverInfo.getString("Name"));

        // -- create an adapter, takes care of binding hash objects in our list to actual row views
        SimpleAdapter adapter = new SimpleAdapter( this, groupData, android.R.layout.simple_list_item_2,
                                                           new String[] { "Label", "Value" },
                                                           new int[]{ android.R.id.text1, android.R.id.text2 } );
        setListAdapter( adapter );
    }
}
