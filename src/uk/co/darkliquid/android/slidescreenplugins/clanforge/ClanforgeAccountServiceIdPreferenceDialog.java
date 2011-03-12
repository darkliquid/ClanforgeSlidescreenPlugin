package uk.co.darkliquid.android.slidescreenplugins.clanforge;

import android.preference.DialogPreference;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.LayoutInflater;
import android.app.AlertDialog;


public class ClanforgeAccountServiceIdPreferenceDialog extends DialogPreference {
	private static final String TAG = ClanforgeContentProvider.class.getName();

	    private String title;
	    private String accountServiceIdPrefsKey;
	    private EditText account_service_id;

	    public ClanforgeAccountServiceIdPreferenceDialog(Context context, AttributeSet attrs, String title, String summary, String accountServiceIdPrefsKey) {
	        super(context, attrs);
	        this.title = title;
	        this.accountServiceIdPrefsKey = accountServiceIdPrefsKey;

//	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

	        setTitle(title);
	        setSummary(summary);
	    }

	    @Override
	    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
	        builder.setTitle(title);

	        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
	        String accountServiceIdPrefStr = prefs.getString(accountServiceIdPrefsKey, null);

	        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View settingsViews = inflater.inflate(R.layout.accountserviceidpreferences, null);
	        this.account_service_id = (EditText) settingsViews.findViewById(R.id.account_service_id);
	        this.account_service_id.setText(accountServiceIdPrefStr);
	        builder.setView(settingsViews);
	        super.onPrepareDialogBuilder(builder);
	    }

	    @Override
	    protected void onDialogClosed(boolean positiveResult) {
	        if (positiveResult) {
	            String accountServiceIdPrefStr = this.account_service_id.getText().toString();
	            Log.d(TAG, "Setting account service id to " + account_service_id);
	            Toast toast = Toast.makeText(getContext(), "Saving account service id.", 1000);
	            toast.show();
	            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
	            prefs.edit().putString(accountServiceIdPrefsKey, accountServiceIdPrefStr).commit();
	        }
	        super.onDialogClosed(positiveResult);
	    }
}
