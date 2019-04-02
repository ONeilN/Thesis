// Класс реализующий главное окно приложения.
// Так же данный класс связывает все классы приложения и отвечает за их взаимодействие.


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

public class MainWindow extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #3c3f41");
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #2b2b2b");
        Scene mainScene = new Scene(root, 800, 650);
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-text-fill: white");
        ToolBar toolBar = new ToolBar();
        toolBar.setStyle("-fx-background-color: #2b2b2b");


        FileChooserHelper fileChooserHelper = new FileChooserHelper();
        ImageKeeper imageKeeper = new ImageKeeper();
        ImageViewHelper imageViewHelper = new ImageViewHelper();
        ImageActions imageActions = new ImageActions();
//        CanvasHelper canvasHelper = new CanvasHelper(mainScene.getHeight()-100, mainScene.getHeight()-100);

        Menu fileMenu = new Menu("Файл");
        Menu actionsMenu = new Menu("Действия");
        Menu helpMenu = new Menu("Помощь");

        // Для "Файл"
        MenuItem openImageMenuItem = new MenuItem("Открыть");
        MenuItem saveImageMenuItem = new MenuItem("Сохранить");
        MenuItem exitMenuItem = new MenuItem("Выход");

        // Для "Действия"
        MenuItem rotateImageRightMenuItem = new MenuItem("Повернуть на право");
        MenuItem rotateImageLeftMenuItem = new MenuItem("Повернуть на лево");
        MenuItem verticalMirrorMenuItem = new MenuItem("Отзеркалить по вертикали");
        MenuItem horizontalMirrorMenuItem = new MenuItem("Отзеркалить по горизонтали");
        MenuItem changeImageSizeMenuItem = new MenuItem("Изменить размер");

        // Для "Помощь"
        MenuItem documentationMenuItem = new MenuItem("Документация");

        exitMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));
        saveImageMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        openImageMenuItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

        File fileOpenImage = new File("./src/icons/buttons/openimage.png");
        Image iconOpenImageButton = new Image(fileOpenImage.toURI().toString());
        Button buttonOpenImage = new Button("", new ImageView(iconOpenImageButton));
        buttonOpenImage.setStyle("    -fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 5 5 5 5;");

        File fileSaveImage = new File("./src/icons/buttons/saveimage.png");
        Image iconSaveImageButton = new Image(fileSaveImage.toURI().toString());
        Button buttonSaveImage = new Button("", new ImageView(iconSaveImageButton));
        buttonSaveImage.setStyle("    -fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 5 5 5 5;");

        File fileRotateImage = new File("./src/icons/buttons/rotate.png");
        Image iconRotateImageButton = new Image(fileRotateImage.toURI().toString());
        Button buttonRotateImage = new Button("", new ImageView(iconRotateImageButton));
        buttonRotateImage.setStyle("    -fx-background-color: \n" +
                "        #090a0c,\n" +
                "        linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                "        linear-gradient(#20262b, #191d22),\n" +
                "        radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                "    -fx-background-radius: 5,4,3,5;\n" +
                "    -fx-background-insets: 0,1,2,0;\n" +
                "    -fx-text-fill: white;\n" +
                "    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                "    -fx-font-family: \"Arial\";\n" +
                "    -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "    -fx-font-size: 12px;\n" +
                "    -fx-padding: 5 5 5 5;");

        File fileVerticalMirror = new File("./src/icons/buttons/MirrorVertical.png");
        Image iconVerticalMirrorButton = new Image(fileVerticalMirror.toURI().toString());
        Button buttonVerticalMirror = new Button("", new ImageView(iconVerticalMirrorButton));
        buttonVerticalMirror.setStyle(" -fx-background-color: \n" +
                " #090a0c,\n" +
                " linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "  -fx-font-size: 12px;\n" +
                "  -fx-padding: 5 5 5 5;");

        File fileHorizontalMirror = new File("./src/icons/buttons/MirrorHorizontal.png");
        Image iconHorizontalMirrorButton = new Image(fileHorizontalMirror.toURI().toString());
        Button buttonHorizontalMirror = new Button("", new ImageView(iconHorizontalMirrorButton));
        buttonHorizontalMirror.setStyle(" -fx-background-color: \n" +
                " #090a0c,\n" +
                " linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "  -fx-font-size: 12px;\n" +
                "  -fx-padding: 5 5 5 5;");

        File fileChangeImageSizeButton = new File("./src/icons/buttons/compress.png");
        Image iconChangeImageSizeButton = new Image(fileChangeImageSizeButton.toURI().toString());
        Button changeImageSizeButton = new Button("", new ImageView(iconChangeImageSizeButton));
        changeImageSizeButton.setStyle(" -fx-background-color: \n" +
                " #090a0c,\n" +
                " linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "  -fx-font-size: 12px;\n" +
                "  -fx-padding: 5 5 5 5;");


        File fileClearButton = new File("./src/icons/buttons/clear.png");
        Image iconClearButton = new Image(fileClearButton.toURI().toString());
        Button buttonClear = new Button("", new ImageView(iconClearButton));
        buttonClear.setStyle(" -fx-background-color: \n" +
                " #090a0c,\n" +
                " linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "  -fx-font-size: 12px;\n" +
                "  -fx-padding: 5 5 5 5;");

        File fileExitButton = new File("./src/icons/buttons/exit.png");
        Image iconExitButton = new Image(fileExitButton.toURI().toString());
        Button buttonExit = new Button("", new ImageView(iconExitButton));
        buttonExit.setLayoutY(265);
        buttonExit.setStyle(" -fx-background-color: \n" +
                " #090a0c,\n" +
                " linear-gradient(#38424b 0%, #1f2429 20%, #191d22 100%),\n" +
                " linear-gradient(#20262b, #191d22),\n" +
                " radial-gradient(center 50% 0%, radius 100%, rgba(114,131,148,0.9), rgba(255,255,255,0));\n" +
                " -fx-background-radius: 5,4,3,5;\n" +
                " -fx-background-insets: 0,1,2,0;\n" +
                " -fx-text-fill: white;\n" +
                " -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );\n" +
                " -fx-font-family: \"Arial\";\n" +
                "  -fx-text-fill: linear-gradient(white, #d0d0d0);\n" +
                "  -fx-font-size: 12px;\n" +
                "  -fx-padding: 5 5 5 5;");


        // События для кнопок панели MenuBar.
        // Кнопка "Открыть изображение".
        openImageMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File selectImage = fileChooserHelper.getFileChooser().showOpenDialog(primaryStage);
                imageKeeper.setImageFromFile(selectImage);

                Image workingImage = imageKeeper.getImage();

                try {
                    BufferedImage bufImage = ImageIO.read(selectImage);
                    imageKeeper.setBufImage(bufImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                imageViewHelper.setImageView(workingImage, (int)mainScene.getWidth()-100, (int)mainScene.getHeight()-100);
                root.setCenter(imageViewHelper.getImageView());
            }
        });

        buttonSaveImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                BufferedImage bufferedImage = imageKeeper.getBufImage();
                byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
                byte[] red = imageActions.getRedPixelsFromImage(bufferedImage, pixels);
                byte[] green = imageActions.getGreenPixelsFromImage(bufferedImage, pixels);
                byte[] blue = imageActions.getBluePixelsFromImage(bufferedImage, pixels);
                byte[] testArray = imageActions.combineRGBArrays(bufferedImage, pixels, red, green, blue);
                int test = 0;
                for (int i = 0; i < pixels.length; i++) {
                    if (pixels[i] == testArray[i]) {
                        test += 1;
                    }
                }
                if (test == pixels.length) {
                    System.out.println(true);
                } else {
                    System.out.println(false);
                }
            }
        });


        // Кнопка для октрытия документации.
        documentationMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().open(new File("./Documentation.docx"));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        mainScene.heightProperty().addListener(new ChangeListener<Number>(){

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                if (imageKeeper.getImage() != null) {
                    buttonExit.setLayoutY(mainScene.getHeight()-100);
                    imageViewHelper.setHeight((int)mainScene.getHeight()-100);
                    imageViewHelper.setWidth((int)mainScene.getWidth()-100);
                }
            }
        });



        fileMenu.getItems().addAll(openImageMenuItem, saveImageMenuItem, exitMenuItem);
        actionsMenu.getItems().addAll(rotateImageRightMenuItem, rotateImageLeftMenuItem, verticalMirrorMenuItem, horizontalMirrorMenuItem, changeImageSizeMenuItem);
        helpMenu.getItems().add(documentationMenuItem);
        menuBar.getMenus().addAll(fileMenu, actionsMenu, helpMenu);
        root.setTop(menuBar);



        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.getItems().add(buttonOpenImage);
        toolBar.getItems().add(buttonSaveImage);
        toolBar.getItems().add(buttonRotateImage);
        toolBar.getItems().add(buttonVerticalMirror);
        toolBar.getItems().add(buttonHorizontalMirror);
        toolBar.getItems().add(changeImageSizeButton);
        toolBar.getItems().add(buttonClear);
        pane.getChildren().add(buttonExit);
        toolBar.getItems().add(pane);
        root.setLeft(toolBar);


        primaryStage.setTitle("Image Editor");
        primaryStage.getIcons().add(new Image("icons/mainIcon.png"));
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(300);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }
}
