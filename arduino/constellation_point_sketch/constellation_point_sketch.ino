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

void runAP() {
  /*
    create and run stand alone access point.
  */
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

