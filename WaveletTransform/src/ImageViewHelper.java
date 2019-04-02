import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class ImageViewHelper {

    private ImageView imageView;
    private int width;
    private int height;

    ImageViewHelper() {

        imageView = null;
        this.width = 0;
        this.height = 0;
    }

    ImageViewHelper(Image img, int width, int height){

        imageView = new ImageView(img);
        this.width = width;
        this.height = height;
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    // SETTERS
    void setWidth(int width) {

        this.width = width;
        imageView.setFitWidth(width);
    }

    void setHeight(int height) {

        this.height = height;
        imageView.setFitHeight(height);
    }

    void setImageView(Image img, int width, int height) {

        imageView = new ImageView(img);
        this.width = width;
        this.height = height;
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
    }

    //GETTERS
    int getWidth() {

        return width;
    }

    int getHeight() {

        return height;
    }

    ImageView getImageView() {

        return imageView;
    }
}
