package uk.co.darkliquid.android.slidescreenplugins.clanforge;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;

public class ClanforgeXMLHandler extends DefaultHandler {
	private ArrayList<ClanforgeServer> serverList = null;
	private ClanforgeServer currentServer = null;
	private String currentValue = null;
	private boolean currentElement = false;

	public ArrayList<ClanforgeServer> getServerList() {
		return serverList;
	}

	@Override
	public void startDocument() {
		serverList = new ArrayList<ClanforgeServer>();
	}

    @Override
    public void startElement(String namespaceURI, String localName, String qName,
            Attributes atts) throws SAXException {

    	currentElement = true;

        if (localName.equals("server")) {
        	currentServer = new ClanforgeServer();
        	String status = atts.getValue("status");
        	currentServer.status = status.equals("UP");
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
        	currentServer.host = currentValue;
        	currentValue = null;
        } else if (localName.equals("name")) {
        	currentServer.name = currentValue;
        	currentValue = null;
        } else if (localName.equals("gametype")) {
        	currentServer.game = currentValue;
        	currentValue = null;
        } else if (localName.equals("map")) {
        	currentServer.map = currentValue;
        	currentValue = null;
        } else if (localName.equals("numplayers")) {
        	currentServer.numplayers = currentValue;
        	currentValue = null;
        } else if (localName.equals("maxplayers")) {
        	currentServer.maxplayers = currentValue;
        	currentValue = null;
        }

    }
}
