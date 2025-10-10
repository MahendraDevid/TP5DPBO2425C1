import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua produk
    private ArrayList<Product> listProduct;
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
        // inisialisasi listProduct
        listProduct = new ArrayList<>();

        // isi listProduct
        populateList();

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

        // isi tabel dengan listProduct
        for (int i = 0; i < listProduct.size(); i++){
            Object[] row = {
                    i + 1,
                    listProduct.get(i).getId(),
                    listProduct.get(i).getNama(),
                    String.format("%.2f", listProduct.get(i).getHarga()),
                    listProduct.get(i).getKategori(),
                    listProduct.get(i).getExpired(),
                    listProduct.get(i).getStok()
            };
            tmp.addRow(row);
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

            // tambahkan data ke dalam list
            listProduct.add(new Product(id, nama, harga, kategori, expired, stok));

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

            // ubah data produk di list
            listProduct.get(selectedIndex).setId(id);
            listProduct.get(selectedIndex).setNama(nama);
            listProduct.get(selectedIndex).setHarga(harga);
            listProduct.get(selectedIndex).setKategori(kategori);
            listProduct.get(selectedIndex).setExpired(expired);
            listProduct.get(selectedIndex).setStok(stok);

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
        // hapus data dari list
        listProduct.remove(selectedIndex);

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

    // panggil prosedur ini untuk mengisi list produk
    private void populateList() {
        listProduct.add(new Product("P001", "Indomie Goreng", 2500, "Makanan", "2024-12-31", 100));
        listProduct.add(new Product("P002", "Teh Botol Sosro", 5000, "Minuman", "2024-11-30", 50));
        listProduct.add(new Product("P003", "Chitato", 7000, "Makanan", "2024-10-15", 75));
        listProduct.add(new Product("P004", "Aqua", 3000, "Minuman", "2025-01-20", 200));
        listProduct.add(new Product("P005", "Silver Queen", 15000, "Makanan", "2024-09-10", 30));
    }
}