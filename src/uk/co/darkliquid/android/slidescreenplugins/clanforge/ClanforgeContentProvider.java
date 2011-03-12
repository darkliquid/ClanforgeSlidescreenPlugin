package uk.co.darkliquid.android.slidescreenplugins.clanforge;

import android.content.*;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import com.larvalabs.slidescreen.PluginUtils;

import java.util.Calendar;
import java.util.ArrayList;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import static com.larvalabs.slidescreen.PluginConstants.*;

/**
 *
 * @author darkliquid
 *
 */
public class ClanforgeContentProvider extends ContentProvider {

    private static final String TAG = ClanforgeContentProvider.class.getName();

    public static final Uri CONTENT_URI = Uri.parse("content://uk.co.darkliquid.android.slidescreenplugins.clanforge");

    public boolean onCreate() {
        Log.d(getClass().getName(), "* CREATED.");
        return true;
    }

    public Cursor query(Uri uri, String[] fields, String s, String[] strings1, String s1) {
        if (fields == null || fields.length == 0) {
            fields = FIELDS_ARRAY;
        }
        Log.d(TAG, "* QUERY Called.");
        for (String string : fields) {
            Log.d(TAG, "  ARG: " + string);
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (!preferences.contains(ClanforgePluginPreferences.SETTING_ACCOUNT_SERVICE_ID)) {
            Log.w(TAG, "No account service id set, telling SlideScreen we need to show settings entry.");
            return makeSettingsNeededCursor();
        }

        ArrayList<ClanforgeServer> clanforgeServers = null;

        try {
        	/** Handling XML */
        	SAXParserFactory spf = SAXParserFactory.newInstance();
        	SAXParser sp = spf.newSAXParser();
        	XMLReader xr = sp.getXMLReader();

        	/** Send URL to parse XML Tags */
        	URL sourceUrl = new URL(
        		"http://clanforge.multiplay.co.uk/public/servers.pl?event=Online;opt=ServersXmlList;accountserviceid=" +
        		preferences.getString(ClanforgePluginPreferences.SETTING_ACCOUNT_SERVICE_ID, "") +
        		";fake=servers.xml"
        	);

        	ClanforgeXMLHandler clanforgeXMLHandler = new ClanforgeXMLHandler();
        	xr.setContentHandler(clanforgeXMLHandler);
        	xr.parse(new InputSource(sourceUrl.openStream()));
        	clanforgeServers = clanforgeXMLHandler.getServerList();
        } catch (Exception e) {
        	Log.d(TAG, "Couldn't get information from Clanforge");
        	return makeClanforgeFailCursor();
        }

        if(clanforgeServers == null || clanforgeServers.size() == 0) {
        	return makeClanforgeFailCursor();
        }

        MatrixCursor cursor = new MatrixCursor(fields);
        for (int i = 0; i < clanforgeServers.size(); i++) {
        	ClanforgeServer server = clanforgeServers.get(i);
            MatrixCursor.RowBuilder builder = cursor.newRow();
            long time = Calendar.getInstance().getTime().getTime();
            for (String field : fields) {
                if (FIELD_ID.equals(field)) {
                    builder.add("" + server.getID());
                } else if (FIELD_TITLE.equals(field)) {
                    builder.add(server.name);
                } else if (FIELD_LABEL.equals(field)) {
                    builder.add(server.game + " - " + server.map);
                } else if (FIELD_TEXT.equals(field)) {
                    builder.add("Players: " + server.numplayers + "/" + server.maxplayers + " Status: " + (server.status ? "UP" : "DOWN"));
                } else if (FIELD_DATE.equals(field)) {
                    builder.add(time - i);
                } else if (FIELD_PRIORITY.equals(field)) {
                    builder.add(time - i);
                } else if (FIELD_INTENT.equals(field)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://clanforge.multiplay.co.uk"));
                    builder.add(PluginUtils.encodeIntents(intent));
                } else {
                    builder.add("");
                }
            }
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    private Cursor makeSettingsNeededCursor() {
        MatrixCursor cursor = new MatrixCursor(new String[]{FIELD_SETTINGS_NEEDED_MESSAGE});
        MatrixCursor.RowBuilder builder = cursor.newRow();
        builder.add("You must provide your account service id.");
        return cursor;
    }

    private Cursor makeClanforgeFailCursor() {
        MatrixCursor cursor = new MatrixCursor(new String[]{FIELD_SETTINGS_NEEDED_MESSAGE});
        MatrixCursor.RowBuilder builder = cursor.newRow();
        builder.add("Couldn't get information from Clanforge. Are the settings right?");
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return "vnd.android.cursor.item";
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }

    public void sendUpdatedNotification() {
        getContext().getContentResolver().notifyChange(CONTENT_URI, null);
    }
}
