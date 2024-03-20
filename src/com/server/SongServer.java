package com.server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SongServer {

    private static final int PORT = 8888;
    private static final String SECRET_KEY = "YourSecretKey";
    private static final String SALT = "YourSalt";
    private static final int IV_LENGTH = 16;
    private static final List<Socket> clientSockets = new ArrayList<>();
    private static ServerSocket serverSocket;
    private static volatile boolean isRunning = true; // Biến để kiểm soát trạng thái máy chủ
    private static Thread serverThread;

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String command;

        try {
            while (true) {
                command = reader.readLine();

                if (command.equalsIgnoreCase("start")) {
                    startServer();
                } else if (command.equalsIgnoreCase("close") && isRunning) {
                    closeServer();
                }
            }
        } catch (IOException e) {

        }

    }

    private static void startServer() {
        isRunning = true; // Đặt trạng thái isRunning thành true để chạy máy chủ
        serverThread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(PORT);
                System.out.println("Máy chủ đã bắt đầu và lắng nghe ở cổng: " + PORT);

                while (isRunning) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client đã kết nối: " + clientSocket.getInetAddress().getHostAddress());
                    synchronized (clientSockets) {
                        clientSockets.add(clientSocket); // Thêm clientSocket vào danh sách
                    }

                    Thread clientThread = new Thread(new ClientHandler(clientSocket));
                    clientThread.start();
                }

            } catch (IOException e) {

            }
        });

        serverThread.start();
    }

    private static void closeServer() {
        isRunning = false; // Đặt trạng thái isRunning thành false để đóng máy chủ
        // Đóng tất cả các clientSockets
        synchronized (clientSockets) {
            for (Socket clientSocket : clientSockets) {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                }
            }
            clientSockets.clear(); // Xóa tất cả các clientSocket khỏi danh sách
        }
        serverThread.interrupt();
        try {
            serverSocket.close();
        } catch (IOException ex) {

        }

        System.out.println("Đã đóng máy chủ");
    }

    static class ClientHandler implements Runnable {

        private final Socket clientSocket;
        private boolean isConnected;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            this.isConnected = true;
        }

        @Override
        public void run() {
            try (
                    BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8")); PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"), true)) {
                while (isConnected && SongServer.isRunning) {
                    String encryptedRequest = reader.readLine();
                    String encryptedType = reader.readLine(); // Đọc loại tìm kiếm từ client

                    if (encryptedRequest == null || encryptedType == null) {
                        isConnected = false;
                        break;
                    }
                    String request = decrypt(encryptedRequest);
                    String type = decrypt(encryptedType);

                    JSONObject responseJson;
                    if (type.equalsIgnoreCase("Bài hát")) {
                        responseJson = findSongByName(request);
                    } else if (type.equalsIgnoreCase("Ca sỹ")) {
                        responseJson = findSingerByName(request);
                    } else {
                        responseJson = null;
                    }

                    if (responseJson != null) {
                        String response = responseJson.toString();
                        String encryptedResponse = encrypt(response);
                        writer.println(encryptedResponse);
                    } else {
                        writer.println("Song not found");
                    }
                }
                System.out.print("Client đã ngắt kết nối!");
                reader.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private JSONObject findSingerByName(String singerName) {
            JSONObject singer = new JSONObject();
            System.out.println(singerName);
            try {
                String url = "https://vi.wikipedia.org/wiki/" + singerName.replace(" ", "_");
                System.out.println(url);
                Document document = Jsoup.connect(url).get();
                Element birthNameElement = document.select("th:contains(Sinh)").first();
                Element dateOfBirthElement = birthNameElement.nextElementSibling();
                String dateOfBirth = dateOfBirthElement.text();
                Element placeOfBirthElement = dateOfBirthElement.select("a").last();
                String placeOfBirth = placeOfBirthElement.text();
                Element birthNameRow = birthNameElement.parent();
                String birthName = birthNameElement.text();
                singer.put("name", birthName);
                singer.put("birthDate", dateOfBirth);
                singer.put("birthPlace", placeOfBirth);
                JSONObject json = findSongByName(singerName);
                singer.put("link", json.get("link"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            return singer;
        }

        private JSONObject findSongByName(String songName) {
            JSONObject song = new JSONObject();

            System.out.println(songName);

            String url = "https://www.nhaccuatui.com/tim-kiem?q=" + songName.replace(" ", "+");
            System.out.println(url);
            try {
                Document doc = Jsoup.connect(url).get();
                Element firstSongItem = doc.selectFirst("li.sn_search_single_song");

                String title = firstSongItem.select("h3.title_song a").text();
                String artist = firstSongItem.select("h4.singer_song a").text();
                Element songLink = firstSongItem.selectFirst("h3.title_song a");
                String href = songLink.attr("href");
                Document songDoc = Jsoup.connect(href).get();
                Element lyricDiv = songDoc.selectFirst("div#lyrics");
                Element lyricP = lyricDiv.selectFirst("p#divLyric");
                String lyrics = lyricP.html().replace("<br>", "\n");

                song.put("title", title);
                song.put("artist", artist);
                song.put("link", href);
                song.put("lyrics", lyrics);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return song;
        }

        private String encrypt(String data) {
            try {
                SecretKey secretKey = generateSecretKey(SECRET_KEY, SALT);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                byte[] iv = generateIV();
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
                byte[] encryptedData = cipher.doFinal(data.getBytes("UTF-8"));
                byte[] combinedData = new byte[IV_LENGTH + encryptedData.length];
                System.arraycopy(iv, 0, combinedData, 0, IV_LENGTH);
                System.arraycopy(encryptedData, 0, combinedData, IV_LENGTH, encryptedData.length);
                return Base64.encodeBase64String(combinedData);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private String decrypt(String encryptedData) {
            try {
                SecretKey secretKey = generateSecretKey(SECRET_KEY, SALT);
                byte[] combinedData = Base64.decodeBase64(encryptedData);
                byte[] iv = new byte[IV_LENGTH];
                byte[] encryptedBytes = new byte[combinedData.length - IV_LENGTH];
                System.arraycopy(combinedData, 0, iv, 0, IV_LENGTH);
                System.arraycopy(combinedData, IV_LENGTH, encryptedBytes, 0, encryptedBytes.length);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
                byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
                return new String(decryptedBytes, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private SecretKey generateSecretKey(String secretKey, String salt) throws Exception {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            PBEKeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            return new SecretKeySpec(tmp.getEncoded(), "AES");
        }

        private byte[] generateIV() {
            byte[] iv = new byte[IV_LENGTH];
            for (int i = 0; i < IV_LENGTH; i++) {
                iv[i] = (byte) (Math.random() * 256);
            }
            return iv;
        }
    }
}
