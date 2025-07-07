package pl.vrtechnology.tires.advanced;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class TireParameters {
    private final int width;
    private final int profile;
    private final int diameter;
}
