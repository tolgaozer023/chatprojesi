/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author tlg
 */
public class VeriTabaniKatmani {

    private Connection conn;
    String dburl = "jdbc:derby://localhost:1527/deneme";
    String user = "tolga";
    String pass = "12345";

    public Connection baglan() {

        try {

            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
            System.out.println("baglanti basarili");
            conn = DriverManager.getConnection(dburl, user, pass);

        } catch (Exception e) {

            System.out.println("baglantida sorun var");

        }

        return conn;

    }

    public String MesajGecmisi() {

        String mesajlar = "";

        if (conn == null) {
            baglan();
        }

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MESAJ FROM TOLGA.MESAJLAR");

            while (rs.next()) {
                mesajlar += rs.getString(1) + "\n";
            }

        } catch (Exception e) {

        }

        return mesajlar;

    }

    public String[] ServerKeyOkuma() {

        String keys[] = new String[2];
        String publicKey = null, privateKey = null;

        if (conn == null) {
            baglan();
        }

        try {

            Statement stmt = conn.createStatement();

            ResultSet okuma = stmt.executeQuery("SELECT * FROM TOLGA.KEYS WHERE ID=1");

            while (okuma.next()) {

                publicKey = okuma.getString("PUBLICKEY");
                privateKey = okuma.getString("PRIVATEKEY");

            }

            if (publicKey == null) {
                RSA_Encryption rsa = new RSA_Encryption();
                String[] keysdizisi = rsa.getkeys();
                setKeys(1, keysdizisi[0], keysdizisi[1]);
                keys[0] = keysdizisi[0];
                keys[1] = keysdizisi[1];
            } else {
                keys[0] = publicKey;
                keys[1] = privateKey;
            }

        } catch (Exception e) {

        }

        return keys;

    }

    public String[] ClientKeyOkuma() {

        String keys[] = new String[2];
        String publicKey = null, privateKey = null;

        if (conn == null) {
            baglan();
        }

        try {

            Statement stmt = conn.createStatement();

            ResultSet okuma = stmt.executeQuery("SELECT * FROM TOLGA.KEYS WHERE ID=2");

            while (okuma.next()) {
                publicKey = okuma.getString("PUBLICKEY");
                privateKey = okuma.getString("PRIVATEKEY");
            }

            if (publicKey == null) {
                RSA_Encryption rsa = new RSA_Encryption();
                String[] keysdizisi = rsa.getkeys();
                setKeys(2, keysdizisi[0], keysdizisi[1]);
                keys[0] = keysdizisi[0];
                keys[1] = keysdizisi[1];
            } else {
                keys[0] = publicKey;
                keys[1] = privateKey;
            }

        } catch (Exception e) {

        }

        return keys;

    }

    public void insertMessage(String message) {

        if (conn == null) {
            baglan();
        }

        try {

            //   Connection baglanti = baglan();
            String sorgu = "INSERT INTO TOLGA.MESAJLAR(MESAJ) VALUES(?)";

            PreparedStatement ps = conn.prepareStatement(sorgu);

            ps.setString(1, message);

            ps.executeUpdate();
            ps.close();

        } catch (Exception e) {

        }

    }

    public void setKeys(int ID, String publickey, String privatekey) {

        if (conn == null) {
            baglan();
        }

        try {

            //   Connection baglanti = baglan();
            String sorgu = "INSERT INTO TOLGA.MESAJLAR(MESAJ) VALUES(?)";

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO TOLGA.KEYS VALUES(?, ?, ?)");
            stmt.setInt(1, ID);
            stmt.setString(2, publickey);
            stmt.setString(3, privatekey);

            stmt.executeUpdate();

            stmt.close();

        } catch (Exception e) {

        }

    }

    public static void main(String args[]) {
        //  VeriTabaniKatmani vtk = new VeriTabaniKatmani();
        //   vtk.MesajGecmisi();

    }

}
