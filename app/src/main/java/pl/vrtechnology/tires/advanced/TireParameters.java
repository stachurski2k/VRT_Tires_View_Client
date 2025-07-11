package pl.vrtechnology.tires.advanced;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
class TireParameters {
    private int width;
    private int profile;
    private int diameter;
}
