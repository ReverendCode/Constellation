WiFiServer server(80); //webserver to accept commands.
const char WiFiAPPSK[] = "sparkfun";
const int MAX_ATTEMPTS = 10;

 struct NetConfig {
  bool present;
  String ssid;
  String pass;
  int ip;
};

struct Request {
  int pin;
  int value;
}

void setup() {
  // put your setup code here, to run once:
  NetConfig network = getNetwork();
  if (!network.present) {
    runAP();
  } else {
    setupWifi(network);
    setupPins();
  }
  server.begin();
}

void loop() {
  // put your main code here, to run repeatedly:
  WiFiClient client = server.available();
  if (!client) {
    //There is no connection, do nothing.
    return;
  }
  //There is a request to be read. process it.
  String rawRequest = client.readStringUntil('\r');
  String response = "";
  if (rawRequest.indexOf("status") != -1) {
    //report status
  } else {
    //process request
    Request request = getRequest(rawRequest);
    digitalWrite(request.pin, request.value);
    
  }
  
}
Request getRequest(String raw) {
  //TODO: this might need some refactor love.
  raw = raw.substring(raw.indexOf("/") + 1);
  return {raw.substring(0, raw.indexOf("/")).toInt(),
          raw.substring(raw.indexOf("/") + 1).toInt()};
}


void setupWifi(NetConfig network) {
      int status = WL_IDLE_STATUS;
      int attempts = 0;
      while (status != WL_CONNECTED && attempts < MAX_ATTEMPTS) {
        attempts++;
        Serial.print("Attempting to connect);
        status = WiFi.begin(network.ssid, network.pass);
        delay(10000); //boo, but what can you do?
      }
      if (status != WL_CONNECTED) {
        runAp(); //we failed to connect, fallback to AP mode for debugging
        //TODO: should this also generate an error log to display in AP mode?
      } else {
        //You have connected, if you need any further setup, do it here.
      }
}

void runAP() {
  WiFi.mode(WIFI_AP);

  // Do a little work to get a unique-ish name. Append the
  // last two bytes of the MAC (HEX'd) to "Thing-":
  uint8_t mac[WL_MAC_ADDR_LENGTH];
  WiFi.softAPmacAddress(mac);
  String macID = String(mac[WL_MAC_ADDR_LENGTH - 2], HEX) +
                 String(mac[WL_MAC_ADDR_LENGTH - 1], HEX);
  macID.toUpperCase();
  String AP_NameString = "Constellation " + macID;

  char AP_NameChar[AP_NameString.length() + 1];
  memset(AP_NameChar, 0, AP_NameString.length() + 1);

  for (int i=0; i<AP_NameString.length(); i++)
    AP_NameChar[i] = AP_NameString.charAt(i);
  WiFi.softAP(AP_NameChar, WiFiAPPSK);
}

NetConfig getNetwork() {
  return {false, "","",-1};
}

