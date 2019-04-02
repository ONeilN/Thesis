import javafx.stage.FileChooser;

import java.io.File;

public class FileChooserHelper {

    private FileChooser fileChooser;

    FileChooserHelper() {
        fileChooser = new FileChooser();
        configuringFileChooser(fileChooser);
    }

    void setFileChooser(FileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    FileChooser getFileChooser() {
        return fileChooser;
    }

    static void configuringFileChooser(FileChooser fileChooser) {
        //Set title for FileChooser
        fileChooser.setTitle("Select Picture");

        //Set Initial Directory
        fileChooser.setInitialDirectory(new File("./Pictures"));

        //Add Extension Filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"));
    }
}
