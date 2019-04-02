import javafx.scene.image.Image;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

class ImageKeeper {

    private Image image;
    private int width;
    private int height;
    private int[] imagePixelsIntegerArray;
    private byte[] imagePixelsByteArray;
    private byte[] imageRedArray;
    private byte[] imageGreenArray;
    private byte[] imageBlueArray;
    private double imageAspectRatio; //Отношение высоты изображения к его ширине.
    private double[] imageYArray;
    private double[] imageCbArray;
    private double[] imageCrArray;
    private BufferedImage bufImage;


    ImageKeeper() {
        image = null;
        width = 0;
        height = 0;
        imageAspectRatio = 0.0;
    }

    ImageKeeper(Image image) {
        this.image = image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        imageAspectRatio = height/width;
    }

    ImageKeeper(File file) {
        image = new Image(file.toURI().toString());
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        imageAspectRatio = height/width;
    }

    // SETTERS

    void setImageFromFile(File file) {
        if (file != null) {
            try {
                image = new Image(file.toURI().toString());
            } catch (NullPointerException ex) {
                System.out.println("Файл не выбран");
            }
            width = (int) image.getWidth();
            height = (int) image.getHeight();
            imageAspectRatio = height/width;
        }
    }

    void setImage(Image image) {
        this.image = image;
        width = (int) image.getWidth();
        height = (int) image.getHeight();
        imageAspectRatio = height/width;
    }

    void setImagePixelsIntegerArray(int[] array) {
        imagePixelsIntegerArray = array;
    }

    void setImagePixelsByteArray(byte[] array) { this.imagePixelsByteArray = array; }

    void setImageRedArray(byte[] array) {
        this.imageRedArray = array;
    }

    void setImageGreenArray(byte[] array) {
        this.imageGreenArray = array;
    }

    void setImageBlueArray(byte[] array) {
        this.imageBlueArray = array;
    }

    void setBufImage(BufferedImage bufImage) {
        this.bufImage = bufImage;
    }

    // GETTERS

    Image getImage() {
        return image;
    }

    double getWidth() {
        return width;
    }

    double getHeight() {
        return height;
    }

    int[] getImagePixelsIntegerArray() {
        return imagePixelsIntegerArray;
    }

    byte[] getImagePixelsByteArray() { return imagePixelsByteArray; }

    byte[] getImageRedArray() {
        return imageRedArray;
    }

    byte[] getImageGreenArray() {
        return imageGreenArray;
    }

    byte[] getImageBlueArray() {
        return imageBlueArray;
    }

    double getImageAspectRatio() {
        return imageAspectRatio;
    }

    double[] getImageYArray() {
        return imageYArray;
    }

    double[] getImageCbArray() {
        return imageCbArray;
    }

    double[] getImageCrArray() {
        return imageCrArray;
    }

    BufferedImage getBufImage() {
        return bufImage;
    }

    // METODS
    void clear() {
        image = null;
        width = 0;
        height = 0;
        imageAspectRatio = 0.0;
    }

    // Метод для получения массива пикселей типа int[] из массива пикселей типа byte[]
    void convertImageToIntArray() {

        final byte[] pixels = ((DataBufferByte) bufImage.getRaster().getDataBuffer()).getData();
        final int width = bufImage.getWidth();
        final int height = bufImage.getHeight();
        final boolean hasAlphaChannel = bufImage.getAlphaRaster() != null;
        int len = height*width;
        int[] result = new int[len];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                int argb = 0;
                argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
                argb += ((int) pixels[pixel + 1] & 0xff); // blue
                argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
                result[row * width + col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                byte argb = 0;
                argb += -16777216; // 255 alpha
                argb += ((int) pixels[pixel] & 0xff); // blue
                argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
                argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
                result[row * width + col] = argb;
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }
        this.imagePixelsIntegerArray = result;
    }

    // Метод для получения объекта класса Image JavaFX из массива пикселей типа int[].
    Image convertIntArrayToImage() {

        WritableImage img = new WritableImage(width, height);
        PixelWriter pw = img.getPixelWriter();
        pw.setPixels(0, 0, width, height, PixelFormat.getIntArgbInstance(), imagePixelsIntegerArray, 0, width);

        return img;
    }

    /* Метод для перекодирования RGB массива пикселей,
       в YCbCr массив пикселей. Это необходимо для получения яркости пикселей */
    void convertRGBToYCbCr() {

        final double kr = 0.229; // Весовой множитель для красного цвета
        final double kg = 0.587; // Весовой множитель для зеленого цвета
        final double kb = 0.114; // Весовой множитель для синего цвета
        final int width = bufImage.getWidth();
        final int height = bufImage.getHeight();
        final boolean hasAlphaChannel = bufImage.getAlphaRaster() != null;
        int size = height*width;
        imageYArray = new double[size];
        imageCbArray = new double[size];
        imageCrArray = new double[size];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, i = 0; pixel < imagePixelsIntegerArray.length; pixel += pixelLength) {
                this.imageYArray[i] = kr * imagePixelsIntegerArray[pixel + 1] + kg * imagePixelsIntegerArray[pixel + 2] + kb * imagePixelsIntegerArray[pixel + 3];
                this.imageCbArray[i] = (0.5 * (imagePixelsIntegerArray[pixel + 3] - imageYArray[i])) / (1 - kb);
                this.imageCrArray[i] = (0.5 * (imagePixelsIntegerArray[pixel + 1] - imageYArray[i])) / (1 - kr);
                i++;
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, i = 0; pixel < imagePixelsIntegerArray.length; pixel += pixelLength) {
                this.imageYArray[i] = kr * imagePixelsIntegerArray[pixel + 0] + kg * imagePixelsIntegerArray[pixel + 1] + kb * imagePixelsIntegerArray[pixel + 2];
                this.imageCbArray[i] = (0.5 * (imagePixelsIntegerArray[pixel + 2] - imageYArray[i])) / (1 - kb);
                this.imageCrArray[i] = (0.5 * (imagePixelsIntegerArray[pixel + 0] - imageYArray[i])) / (1 - kr);
                i++;
            }
        }
    }

    /* Метод для разделения byte массива RGB, на 3 byte массива: R - красный,
       G - зеленый, B - синий */
    void splitRGB() {

        final byte[] pixels = ((DataBufferByte) bufImage.getRaster().getDataBuffer()).getData();
        final int width = bufImage.getWidth();
        final int height = bufImage.getHeight();
        final boolean hasAlphaChannel = bufImage.getAlphaRaster() != null;
        int size = height*width;
        byte[] redArray = new byte[size];
        byte[] greenArray = new byte[size];
        byte[] blueArray = new byte[size];
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, row = 0, col = 0; pixel < imagePixelsIntegerArray.length; pixel += pixelLength) {
                redArray[row * width + col] = pixels[pixel + 1];
                greenArray[row * width + col] = pixels[pixel + 2];
                blueArray[row * width + col] = pixels[pixel + 3];
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
                redArray[row * width + col] = pixels[pixel];
                greenArray[row * width + col] = pixels[pixel + 1];
                blueArray[row * width + col] = pixels[pixel + 2];
                col++;
                if (col == width) {
                    col = 0;
                    row++;
                }
            }
        }

        this.imageRedArray = redArray;
        this.imageGreenArray = greenArray;
        this.imageBlueArray = blueArray;
    }

    /* Метод для объединения 3х byte массивов: R - красный, G - зеленый, B - синий,
    в один общий byte массив RGB */
    void combineRGBArrays() {

        final int width = bufImage.getWidth();
        final int height = bufImage.getHeight();
        final boolean hasAlphaChannel = bufImage.getAlphaRaster() != null;
        if (hasAlphaChannel) {
            final int pixelLength = 4;
            for (int pixel = 0, i = 0; pixel < imagePixelsByteArray.length; pixel += pixelLength) {
                imagePixelsByteArray[pixel + 1] = imageRedArray[i];
                imagePixelsByteArray[pixel + 2] = imageGreenArray[i];
                imagePixelsByteArray[pixel + 3] = imageBlueArray[i];
                i++;
            }
        } else {
            final int pixelLength = 3;
            for (int pixel = 0, i = 0; pixel < imagePixelsByteArray.length; pixel += pixelLength) {
                imagePixelsByteArray[pixel] = imageRedArray[i];
                imagePixelsByteArray[pixel + 1] = imageGreenArray[i];
                imagePixelsByteArray[pixel + 2] = imageBlueArray[i];
                i++;
            }
        }
    }
}
