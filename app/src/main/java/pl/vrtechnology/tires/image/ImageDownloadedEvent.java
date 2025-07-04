package pl.vrtechnology.tires.image;

import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageDownloadedEvent {

    private final Bitmap bitmap;
}
