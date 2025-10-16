import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        ProductMenu menu = new ProductMenu();

        // atur ukuran window
        menu.setSize(700, 600);

        // letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua produk
    private ArrayList<Product> listProduct;
    private Database database;
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;
    private JTextField expiredField;
    private JLabel expiredLabel;
    private JSpinner stokSpinner;
    private JLabel stokLabel;

    // constructor
    public ProductMenu() {
        //buat objek database
        database = new Database();

        // isi tabel produk
        productTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = {"Pilih Kategori Produk", "Makanan", "Minuman"};
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedIndex == -1){
                    insertData();
                }else{
                    updateData();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // pastikan ada baris yang dipilih
                if (selectedIndex == -1) {
                    JOptionPane.showMessageDialog(ProductMenu.this, "Pilih produk yang akan dihapus.", "Info", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(ProductMenu.this,
                        "Apakah Anda yakin ingin menghapus data ini?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });        // saat tombol delete ditekan

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = productTable.getSelectedRow();

                // simpan value textfield dan combo box
                String curId = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex, 4).toString();
                String curExpired = productTable.getModel().getValueAt(selectedIndex, 5).toString();
                String curStok = productTable.getModel().getValueAt(selectedIndex, 6).toString();

                // ubah isi textfield dan combo box
                idField.setText(curId);
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriComboBox.setSelectedItem(curKategori);
                expiredField.setText(curExpired);
                stokSpinner.setValue(Integer.parseInt(curStok));

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);

            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = { "No", "ID Produk", "Nama", "Harga", "Kategori", "Expired", "Stok" };

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel(null, cols);

        //isi tabel dengan hasil query
        try{
            ResultSet resultSet = database.selectQuery("SELECT * FROM product");

            int i = 0;
            while (resultSet.next()) {
                Object[] row = new Object[7];
                row[0] = ++i;
                row[1] = resultSet.getString("id");
                row[2] = resultSet.getString("nama");
                row[3] = resultSet.getDouble("harga");
                row[4] = resultSet.getString("kategori");
                row[5] = resultSet.getString("expired");
                row[6] = resultSet.getInt("stok");
                tmp.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return tmp; // return juga harus diganti
    }

    public void insertData() {
        try{
            // ambil value dari textfield dan combobox
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String expired = expiredField.getText();
            int stok = (Integer) stokSpinner.getValue();

            //cek data tidak boleh kosong
            try{
                if(id.isEmpty() || nama.isEmpty() || kategori.equals("Pilih Kategori Produk") || expired.isEmpty()){
                    throw new Exception("Data tidak boleh kosong");
                }
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //cek id tidak boleh sama
            try{
                ResultSet resultSet = database.selectQuery("SELECT * FROM product WHERE id = '" + id + "'");
                if(resultSet.next()){
                    throw new Exception("ID Produk sudah ada");
                }
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }


            //tambah data produk ke database
            String sqlQuery = "INSERT INTO product VALUES ('" + id + "', '" + nama + "', " + harga + ", '" + kategori + "', '" + expired + "', " + stok + ")";
            database.insertUpdateDeleteQuery(sqlQuery);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Insert berhasil");
            JOptionPane.showMessageDialog(null,"Data berhasil Ditambahkan");
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateData() {
        try{
            // ambil data dari form
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            String expired = expiredField.getText();
            int stok = (Integer) stokSpinner.getValue();

            //cek data tidak boleh kosong
            try{
                if(id.isEmpty() || nama.isEmpty() || kategori.equals("Pilih Kategori Produk") || expired.isEmpty()){
                    throw new Exception("Data tidak boleh kosong");
                }
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // update data di database
            String sqlQuery = "UPDATE product SET " + "nama = '" + nama + "', " + "harga = " + harga + ", " + "kategori = '" + kategori + "', " + "expired = '" + expired + "', " + "stok = " + stok + " " + "WHERE id = '" + id + "'";
            database.insertUpdateDeleteQuery(sqlQuery);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Update berhasil");
            JOptionPane.showMessageDialog(null,"Data berhasil diubah");
        }catch (NumberFormatException ex){
            JOptionPane.showMessageDialog(null, "Harga harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    public void deleteData() {
        // hapus data dari database
        String sqlQuery = "DELETE FROM product WHERE id = '" + idField.getText() + "'";
        database.insertUpdateDeleteQuery(sqlQuery);

        // update tabel
        productTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete Berhasil");
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        expiredField.setText("");
        stokSpinner.setValue(0);

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;

    }
}