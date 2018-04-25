#include <ESP8266WiFi.h>
#include <ArduinoJson.h>

//////////////////////
// WiFi Definitions //
//////////////////////
const char WiFiAPPSK[] = "sparkfun";
const char ssid[] = "Aernet";
const char pass[] = "1234567890";
const int ip = 101;
/////////////////////
// Pin Definitions //
/////////////////////
const int LED_PIN = 2; // In this case, 2 is the pin for the D1 Mini's blue wifi led
const int ANALOG_PIN = A0; // The only analog pin on the Thing
const int DIGITAL_PIN = 12; // Digital pin to be read

WiFiServer server(80);

void setup()
{
  /*
  init hardware. (Maybe with pin map?)
  connect to <NETWORK> with <IP>
  possibly register active pins at <SERVER>
  */
  initHardware();
  setupWiFi();
  server.begin();

}

void loop() {
  WiFiClient client = server.available();
  if (!client) {
    return;
  }
  StaticJsonBuffer<200> jsonBuffer;
  String request = client.readStringUntil('\r'); // request = /set/0/1
  client.flush();
  request = request.substring(request.indexOf("/") + 1);
  int pin = request.substring(0, request.indexOf("/") ).toInt();
  int value = request.substring(request.indexOf("/") + 1).toInt();
//  client.print("Pin: " + pin + " Value: " + value);
  digitalWrite(pin,value);
  JsonObject& root = jsonBuffer.createObject();
  root["value"] = value;
  root["pinId"] = pin;
  root.printTo(Serial);
  client.println("HTTP/1.0 200 OK");
  client.println("Content-Type: application/json");
  client.println("Connection: close");
  client.println();
  root.prettyPrintTo(client);
  client.stop();
}

// TODO(DWenzel): change this to connect to the (already extant) network.
// to be hard wired for prototype.
void setupWiFi()
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

void initHardware()
{
  Serial.begin(115200);
  pinMode(DIGITAL_PIN, INPUT_PULLUP);
  pinMode(LED_PIN, OUTPUT);
  digitalWrite(LED_PIN, LOW);
  // Don't need to set ANALOG_PIN as input, 
  // that's all it can be.
}

