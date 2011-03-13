package uk.co.darkliquid.android.slidescreenplugins.clanforge;

import android.os.Bundle;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class ClanforgeXMLHandler extends DefaultHandler {
	private ArrayList<Bundle> serverList = null;
	private Bundle currentServer = null;
	private String currentValue = null;
	private boolean currentElement = false;

	public ArrayList<Bundle> getServerList() {
		return serverList;
	}

	@Override
	public void startDocument() {
		serverList = new ArrayList<Bundle>();
	}

    @Override
    public void startElement(String namespaceURI, String localName, String qName,
            Attributes atts) throws SAXException {

    	currentElement = true;

        if (localName.equals("server")) {
        	currentServer = new Bundle();
        	String status = atts.getValue("status");
        	currentServer.putString("Status", status);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) {
    	if (currentElement) {
			currentValue = new String(ch, start, length);
			currentElement = false;
		}
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
    throws SAXException {

    	currentElement = false;

        if (localName.equals("server")) {
        	serverList.add(currentServer);
        	currentServer = null;
        } else if (localName.equals("hostname")) {
        	currentServer.putString("Host", currentValue);
        	currentValue = null;
        } else if (localName.equals("name")) {
        	currentServer.putString("Name", currentValue);
        	currentValue = null;
        } else if (localName.equals("gametype")) {
        	currentServer.putString("Game", currentValue);
        	currentValue = null;
        } else if (localName.equals("map")) {
        	currentServer.putString("Map", currentValue);
        	currentValue = null;
        } else if (localName.equals("numplayers")) {
        	currentServer.putString("Number of players", currentValue);
        	currentValue = null;
        } else if (localName.equals("maxplayers")) {
        	currentServer.putString("Max players", currentValue);
        	currentValue = null;
        } else if (localName.equals("numspectators")) {
        	currentServer.putString("Number of spectators", currentValue);
        	currentValue = null;
        } else if (localName.equals("maxspectators")) {
        	currentServer.putString("Max spectators", currentValue);
        	currentValue = null;
        } else if (localName.equals("ping")) {
        	currentServer.putString("Ping", currentValue);
        	currentValue = null;
        }

    }
}
