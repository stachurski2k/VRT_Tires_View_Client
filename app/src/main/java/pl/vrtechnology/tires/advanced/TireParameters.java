package pl.vrtechnology.tires.advanced;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
class TireParameters implements Cloneable {
    private int width;
    private int profile;
    private int diameter;

    @NonNull
    @Override
    public TireParameters clone() {
        try {
            return (TireParameters) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported", e);
        }
    }
}
