bool isAP = false;

void setup() {
  // put your setup code here, to run once:
  /*
  val config = getConfig();
  if (!config) {
    runAP();
    isAP = true;
  } else {
    setupWifi(config.wifi);
    setupPins(config.pins);
  }
  */
}

void loop() {
  // put your main code here, to run repeatedly:

}

void runAP()
{
  WiFi.mode(WIFI_AP);

  // Do a little work to get a unique-ish name. Append the
  // last two bytes of the MAC (HEX'd) to "Thing-":
  uint8_t mac[WL_MAC_ADDR_LENGTH];
  WiFi.softAPmacAddress(mac);
  String macID = String(mac[WL_MAC_ADDR_LENGTH - 2], HEX) +
                 String(mac[WL_MAC_ADDR_LENGTH - 1], HEX);
  macID.toUpperCase();
  String AP_NameString = "ESP8266 Thing " + macID;

  char AP_NameChar[AP_NameString.length() + 1];
  memset(AP_NameChar, 0, AP_NameString.length() + 1);

  for (int i=0; i<AP_NameString.length(); i++)
    AP_NameChar[i] = AP_NameString.charAt(i);
  WiFi.softAP(AP_NameChar, WiFiAPPSK);
}


PointConfig getConfig() {
  /*
  access the SPIFFS filesystem, look for config file.
  */
  return null;
}

class PointConfig {
  public:
    PointConfig();
  private:
    String ssid;
    String pass;
    Pin[10] pins;
}

struct Pin {
  int pinId;
  String Name;
  bool takesInput;
}

