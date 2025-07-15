package pl.vrtechnology.tires.settings;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Configuration {

    private String deviceIp = "172.20.0.226";
    private int devicePort = 8080;

    private int minTireWidth = 135;
    private int maxTireWidth = 355;

    private int minTireProfile = 30;
    private int maxTireProfile = 85;

    private int minTireDiameter = 12;
    private int maxTireDiameter = 24;
}
