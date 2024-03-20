package com.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;

public class frmClient extends javax.swing.JFrame {

    private static final int PORT = 8888;
    private final DefaultListModel<String> listModel;
    private static final String SECRET_KEY = "YourSecretKey";
    private static final String SALT = "YourSalt";
    private static final int IV_LENGTH = 16;
    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;

    public frmClient() {
        initComponents();
        startClient();
        listModel = new DefaultListModel<>();
        listLink.setModel(listModel);
    }

    private void startClient() {
        try {
            clientSocket = new Socket("localhost", PORT);
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
            writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"), true);
        } catch (IOException e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textField1 = new java.awt.TextField();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        areaLyrics = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        listLink = new javax.swing.JList<>();
        txtSong = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboBox = new javax.swing.JComboBox<>();

        textField1.setText("textField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));
        getContentPane().add(jLabel1);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        areaLyrics.setColumns(20);
        areaLyrics.setRows(5);
        jScrollPane1.setViewportView(areaLyrics);

        listLink.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listLinkMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(listLink);

        btnFind.setText("Tìm");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        jLabel3.setText("Liên kết đến bài hát:");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("ỨNG DỤNG TÌM THÔNG TIN BÀI HÁT");

        jLabel4.setText("Tên bài hát:");

        comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bài hát", "Ca sỹ" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(txtSong)
                            .addGap(18, 18, 18)
                            .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnFind)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(55, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSong, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 28, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        String searchText = txtSong.getText(); // Lấy từ khóa tìm kiếm từ JTextField
        String searchType = comboBox.getSelectedItem().toString(); // Lấy loại tìm kiếm từ ComboBox

        Thread requestThread = new Thread(() -> {
            try {
                String encryptedSearchText = encrypt(searchText);
                String encryptedSearchType = encrypt(searchType);

                writer.println(encryptedSearchText);
                writer.println(encryptedSearchType);

                String encryptedLine;
                txtSong.setText("");
                areaLyrics.setText("");
                listModel.clear();
                while ((encryptedLine = reader.readLine()) != null) {
                    if (encryptedLine.equals("Song not found")) {
                        JOptionPane.showMessageDialog(null, "Không tìm thấy bài hát hoặc ca sỹ!");
                    } else {
                        if (comboBox.getSelectedItem().toString() == "Bài hát") {
                            String line = decrypt(encryptedLine);
                            JSONObject responseJson = new JSONObject(line);
                            String artist = responseJson.getString("artist");
                            String link = responseJson.getString("link");
                            String lyrics = responseJson.getString("lyrics");
                            areaLyrics.append("Tên ca sỹ hoặc bài hát:\n" + artist + "\n\n");
                            areaLyrics.append("Lời bài hát: \n\n" + lyrics + "\n");
                            listModel.clear();
                            listModel.addElement(link);
                        } else {
                            String line = decrypt(encryptedLine);
                            JSONObject responseJson = new JSONObject(line);
                            String inputString = responseJson.getString("birthDate");
                            String link = responseJson.getString("link");
                            String fullName = inputString.replaceAll("\\d+.*", "").trim();
                            areaLyrics.append("Họ và tên: " + fullName);
                            String dobPattern = "(\\d+\\s+tháng\\s+\\d+)";
                            Pattern dobRegex = Pattern.compile(dobPattern);
                            Matcher dobMatcher = dobRegex.matcher(inputString);
                            String dateOfBirth = dobMatcher.find() ? dobMatcher.group(1) : "";
                            areaLyrics.append("\n\nNgày sinh: " + dateOfBirth);
                            String yearPattern = "(\\d{4})";
                            Pattern yearRegex = Pattern.compile(yearPattern);
                            Matcher yearMatcher = yearRegex.matcher(inputString);
                            String yearOfBirth = yearMatcher.find() ? yearMatcher.group(1) : "";
                            areaLyrics.append("\n\nNăm sinh: " + yearOfBirth);
                            String agePattern = "\\((\\d+\\s+tuổi)\\)";
                            Pattern ageRegex = Pattern.compile(agePattern);
                            Matcher ageMatcher = ageRegex.matcher(inputString);
                            String age = ageMatcher.find() ? ageMatcher.group(1) : "";
                            areaLyrics.append("\n\nTuổi: " + age);
                            String hometown = inputString.replaceAll(".*\\)\\s+", "");
                            areaLyrics.append("\n\nQuê quán: " + hometown);
                            listModel.clear();
                            listModel.addElement(link);

                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        requestThread.start();
    }//GEN-LAST:event_btnFindActionPerformed
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
    private void listLinkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listLinkMouseClicked
        // Lấy chỉ mục của mục được chọn trong JList
        int selectedIndex = listLink.getSelectedIndex();
        // Kiểm tra xem có mục nào được chọn không
        if (selectedIndex != -1) {
            // Lấy liên kết tương ứng từ listModel
            String link = listModel.getElementAt(selectedIndex);
            try {
                // Mở liên kết trong trình duyệt
                java.awt.Desktop.getDesktop().browse(new URI(link));
            } catch (IOException | URISyntaxException e) {
            }
        }
    }//GEN-LAST:event_listLinkMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        // Ngắt kết nối đến máy chủ
        try {
            // Thực hiện các hoạt động cần thiết để ngắt kết nối
            // Ví dụ: Đóng socket, gửi thông báo đóng kết nối, vv.
            clientSocket.close(); // Đóng socket kết nối đến máy chủ
            reader.close();
            writer.close();
            System.out.println("Disconnected from server");
        } catch (IOException e) {
        }
    }//GEN-LAST:event_formWindowClosing

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmClient().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea areaLyrics;
    private javax.swing.JButton btnFind;
    private javax.swing.JComboBox<String> comboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> listLink;
    private java.awt.TextField textField1;
    private javax.swing.JTextField txtSong;
    // End of variables declaration//GEN-END:variables
}
