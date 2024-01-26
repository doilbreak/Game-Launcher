package com.example.isglauncher;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ISGLauncherController extends ISGLauncherApp {

    @FXML private ImageView Haberbe;

    private List<ImageLink> imageLinks = new ArrayList<>();
    private int currentSlide = 0;

    public ISGLauncherController() {
        // ImageLinks'leri doldur
        imageLinks.add(new ImageLink("image1.jpg", "https://www.example.com/link1"));
        imageLinks.add(new ImageLink("image2.jpg", "https://www.example.com/link2"));
        imageLinks.add(new ImageLink("image3.jpg", "https://www.example.com/link3"));
        imageLinks.add(new ImageLink("image4.jpg", "https://www.example.com/link4"));
    }
    @FXML
    private Button kayit;
    @FXML
    private Button forum;
    @FXML
    private Button destek;
    @FXML
    private Button sss;
    @FXML
    private Button insta;
    @FXML
    private Button discord;
    @FXML
    private ImageView baslat;




    @FXML
    private void initialize() {
        checkForUpdates();
        // İlk resmi görüntüle
        showSlide(currentSlide);

        // Zamanlayıcıyı oluştur ve her 5 saniyede bir slaytı değiştir
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> nextSlide())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    private void nextSlide() {
        currentSlide = (currentSlide + 1) % imageLinks.size();
        showSlide(currentSlide);
    }

    private void showSlide(int slideIndex) {
        ImageLink imageLink = imageLinks.get(slideIndex);
        Image image = new Image(getClass().getResourceAsStream(imageLink.getImagePath()));
        Haberbe.setImage(image);

        Haberbe.setOnMouseClicked(event -> openLink(imageLink.getLink()));
    }






    @FXML
    private void handleOyunBaslatButton(ActionEvent event) {
        try {
            // Oyunun exe dosyasının yolunu belirtin
            String oyunExeYolu = "C:\\ISG\\SVGGG.exe"; // Oyunun exe dosyasının yolu

            // Oyunu başlat
            ProcessBuilder processBuilder = new ProcessBuilder(oyunExeYolu);
            processBuilder.start();
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
            // Hata işleme kodunu ekle
            System.err.println("Oyun başlatılırken hata oluştu: " + e.getMessage());
        }
    }

    private boolean checkForUpdates() {
        try {
            // Güncelleme kontrolünün yapılacağı URL
            URL updateURL = new URL("https://filemanager.ai/new/#/c/185.27.134.11/if0_35180872/eyJ0IjoiZnRwIiwiYyI6eyJwIjoiV2NweHNiTzBCd0hvViIsImkiOiIvIn19");

            // Çevrimiçi sürümü oku
            BufferedReader reader = new BufferedReader(new InputStreamReader(updateURL.openStream()));
            String onlineVersionStr = reader.readLine().trim();
            reader.close();

            // Yerel sürümü al
            String localVersionStr = getVersionFromFile(); // Sürümü dosyadan okuyun veya başka bir yerden alın

            // Sürüm karşılaştırması yap
            if (!onlineVersionStr.equals(localVersionStr)) {
                // Güncelleme mevcut, güncellemeyi başlat
                downloadUpdate();
                return true;
            } else {
                // Güncelleme yok, işlem tamamlandı
                return false;
            }
        } catch (IOException ex) {
            // Hata durumunda kullanıcıya hata mesajı gösterilebilir
            showUpdateError("Güncelleme kontrolü sırasında bir hata oluştu: " + ex.getMessage());
            return false;
        }
    }

    private String getVersionFromFile() {
        String filePath = "versiyon.txt";

        try {
            // FileReader ve BufferedReader kullanarak dosyayı okuma işlemi
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            StringBuilder versionBuilder = new StringBuilder();

            // Dosya satır satır okuma
            while ((line = bufferedReader.readLine()) != null) {
                versionBuilder.append(line);
            }

            bufferedReader.close();
            fileReader.close();

            return versionBuilder.toString().trim(); // Okunan metni temizleyin ve döndürün
        } catch (IOException ex) {
            // Dosya okuma sırasında bir hata oluşursa burada ele alabilirsiniz
            showUpdateError("versiyon.txt dosyası okunurken bir hata oluştu: " + ex.getMessage());
            ex.printStackTrace();  // Hatanın ayrıntılarını yazdır
            System.exit(1);  // Programı kapat
            return "0.0.1"; // Hata durumunda varsayılan bir sürüm döndürebilirsiniz
        }
    }


    private void downloadUpdate() {
        try {
            // Güncelleme dosyasının URL'si
            URL updateURL = new URL("https://filemanager.ai/new/#/c/185.27.134.11/if0_35180872/eyJ0IjoiZnRwIiwiYyI6eyJwIjoiV2NweHNiTzBCd0hvViIsImkiOiIvIn19");

            // Güncelleme dosyasını indirme işlemi
            ReadableByteChannel channel = Channels.newChannel(updateURL.openStream());
            FileOutputStream outputStream = new FileOutputStream("SVGGG.zip"); // Güncelleme dosyasının kaydedileceği dosya adı

            // Kanalı dosyaya kopyala
            outputStream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);

            // Dosyayı kapat
            outputStream.close();

            // Güncelleme dosyasını başarıyla indirdik, şimdi uygulamada uygulayabiliriz
            applyUpdate();
        } catch (IOException ex) {
            // İndirme sırasında bir hata olursa burada ele alabilirsiniz
            showUpdateError("Güncelleme indirilirken bir hata oluştu: " + ex.getMessage());
        }
    }

    private void applyUpdate() {
        try {
            // Güncelleme dosyasının adı ve yolu
            String updateFilePath = "update.zip"; // Güncelleme dosyasının adı ve yolu
            String targetDirectory = "oyun_klasoru"; // Güncellemelerin uygulanacağı hedef dizin

            // ZIP dosyasını açma
            ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(updateFilePath));
            ZipEntry entry;

            // ZIP dosyasındaki her girişi okuma ve hedef dizine çıkarma
            while ((entry = zipInputStream.getNextEntry()) != null) {
                String entryName = entry.getName();
                String entryPath = Paths.get(targetDirectory, entryName).toString();

                if (entry.isDirectory()) {
                    // Dizin oluşturma
                    Files.createDirectories(Paths.get(entryPath));
                } else {
                    // Dosya kopyalama
                    Files.copy(zipInputStream, Paths.get(entryPath), StandardCopyOption.REPLACE_EXISTING);
                }

                zipInputStream.closeEntry();
            }

            zipInputStream.close();
        } catch (IOException ex) {
            // Güncelleme sırasında bir hata oluşursa burada ele alabilirsiniz
            showUpdateError("Güncelleme uygulanırken bir hata oluştu: " + ex.getMessage());
        }
    }
    private void showUpdateError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Sunucuya Bağlanmıyor yetkili ile iletişime geç");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }

    @FXML
    private void handleUyeOlButton() {
        String link = "https://www.threads.net/@internationalsoccergame";
        openLink(link);
    }
    @FXML
    private void handleForumButton() {
        String link = "https://www.threads.net/@internationalsoccergame";
        openLink(link);
    }
    @FXML
    private void handleDestekButton() {
        String link = "https://www.threads.net/@internationalsoccergame";
        openLink(link);
    }

    @FXML
    private void handleSssButton() {
        String link = "https://www.threads.net/@internationalsoccergame";
        openLink(link);
    }
    @FXML
    private void handleInstaButton(){
        String link = "https://www.threads.net/@internationalsoccergame";
        openLink(link);
    }
    @FXML
    private void handleDiscordButton(){
        String link = "https://www.threads.net/@internationalsoccergame";
        openLink(link);
    }
    @FXML
    private void handleTiktokButton(){
        String link = "https://www.threads.net/@internationalsoccergame";
        openLink(link);
    }
    @FXML
    private void handleYoutubeButton(){
        String link = "https://www.threads.net/@internationalsoccergame";
        openLink(link);
    }
    @FXML
    private void handleThreadsButton(){
        String link = "https://www.threads.net/@internationalsoccergame";
        openLink(link);
    }
    @FXML
    private void handleXButton(){
        String link = "https://www.threads.net/@internationalsoccergame";
        openLink(link);
    }


    private void openLink(String link) {
        try {
            java.awt.Desktop.getDesktop().browse(new java.net.URI(link));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Link açılırken hata oluştu: " + e.getMessage());
        }
    }
}
