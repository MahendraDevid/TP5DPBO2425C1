# Janji
Janji Saya Mahendra Devid Putra Anwar dengan NIM 2407792 mengerjakan Tugas Praktikum 2 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

# Desain Program
1. **Class Product**
- **Atribut**
  - ID : Untuk id produk
  - Nama : Nama produk
  - Harga : harga produk
  - Kategori : kategori Produk
  - Expired : Tanggal expired produk
  - Stok : Stok Produk
    
- **Method**
  -Setter dan getter Untuk Product
  
2. **Class ProductMenu (GUI)**
- **Component**
  - Textfield : untuk memasukkan data seperti ID, nama, harga, dan expired
  - Label : Untuk Memberi label disebuah component lain
  - Panel : Panel utama untuk menampung semua component2 yang ada
  - Table : Untuk menampilkan data dalam sebuah table
  - ComboBox : Untuk memilih kategori produk
  - Spinner : Untuk Memasukkan stok produk
  - Button : Untuk tombol seperti add, update, delete dan cancel
 
3. **Class Database**
- **Atribut**
  - Connection : Untuk menyambungkan java dengan sql
  - Statement : Untuk menerima perintah sql dan mengirimkannya ke sql
 
- **Method**
  - selectQuery : menjalankan perintah select untuk menampilkan data
  - insertUpdateDelete : menjalankan perintah Insert, update dan delete (DML)
  - getStatement : mengambil objek statement

# Alur Program
1. **Menampilkan data**
- Data yang sudah ada dalam database akan ditampilkan
- Lalu Data yang sudah di add dan di update akan ditampilkan dalam table tersebut
  
2. **Insert data**
- User akan memasukkan data sesuai yang ada di form dan harus mengisi semua isi form tersebut atau tidak ada alert jika data belum lengkap di isi
- User harus memilih id yang belum ada dalam table tersebut,id harus unik. jika tidak maka akan ada alert id sudah ada.
- Lalu menekan tombol add
- Lalu Akan ada notifikasi data ditambahkan
- Setelah itu data akan masuk ke dalam table dan database
  
3. **Update Data**
- User akan memilih yang akan diupdate datanya
- User harus melengkapi data yang ada dalam form, jika data dalam form tidak semua di isi maka ada alert jika data belum lengkap di isi
- Lalu Menekan tombol update
- User memilih data yang ingin dihapus
- setelah itu akan ada notifikasi data di ubah
- Setelah itu data akan terubah ditable dan database
  
4. **Delete Data**
- User akan memilih data yang ingin dihapus
- Lalu Menekan Tombol Delete
- Lalu akan ada notifikasi konfirmasi data dihapus ya atau tidak
- Lalu akan ada notifikasi data sudah di hapus
- Setelah itu data yang dihapus akan hilang dalam table dan database
  
5. **Clear Data**
- Saat user menekan tombol cancel
- Maka data yang diform akan ke reset

# Dokumentasi
<img src="Dokumentasi/Tp5/Screen Recording 2025-10-17 003818.gif" width="800">


