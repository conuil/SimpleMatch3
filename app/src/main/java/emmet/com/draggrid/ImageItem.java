package emmet.com.draggrid;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

public class ImageItem {
    private Bitmap image;
    private ImageView theView;


    public ImageItem(Bitmap image) {
        super();
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setTheView(ImageView v) {
        theView = v;
    }
    public ImageView getTheView() {
        return theView;
    }


}