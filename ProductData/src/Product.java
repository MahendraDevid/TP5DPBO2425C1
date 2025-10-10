public class Product {
    private String id;
    private String nama;
    private double harga;
    private String kategori;
    private String expired;
    private int stok;

    public Product(String id, String nama, double harga, String kategori, String expired, int stok) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.expired = expired;
        this.stok = stok;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setExpired(String expired) { this.expired = expired; }

    public void setStok(int stok) { this.stok = stok; }

    public String getId() { return this.id; }

    public String getNama() { return this.nama; }

    public double getHarga() { return this.harga; }

    public String getKategori() { return this.kategori; }

    public  String getExpired() { return this.expired; }

    public int getStok() { return this.stok; }
}