package uk.co.darkliquid.android.slidescreenplugins.clanforge;

//import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
//import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public class ClanforgePluginPreferences extends PreferenceActivity {
	public static final String SETTING_ACCOUNT_SERVICE_ID = "clanforge_account_service_id";

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
//		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		final ClanforgeAccountServiceIdPreferenceDialog accountServiceIdDialog = new ClanforgeAccountServiceIdPreferenceDialog(
				this, null, "Account Service ID", "Clanforge account service id",
				SETTING_ACCOUNT_SERVICE_ID);
        root.addPreference(accountServiceIdDialog);

		setPreferenceScreen(root);
	}
}
