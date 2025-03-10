
-- Tabel Kategori
CREATE TABLE IF NOT EXISTS kategori (
  id_kategori CHAR(3) NOT NULL PRIMARY KEY,
  kategori VARCHAR(50) NOT NULL UNIQUE
);

-- Tabel Pemasok
CREATE TABLE IF NOT EXISTS pemasok (
    id_pemasok INT AUTO_INCREMENT PRIMARY KEY, 
    nama_pemasok VARCHAR(100) NOT NULL,
    alamat VARCHAR(255) DEFAULT NULL,
    no_hp VARCHAR(15) DEFAULT NULL,
    INDEX idx_nama_pemasok (nama_pemasok)
);

-- Tabel Pelanggan
CREATE TABLE IF NOT EXISTS pelanggan (
  id_pelanggan INT AUTO_INCREMENT PRIMARY KEY,
  nama_pelanggan VARCHAR(100) NOT NULL,
  alamat VARCHAR(255) DEFAULT NULL,
  no_hp VARCHAR(15) DEFAULT NULL,
  INDEX idx_nama_pelanggan (nama_pelanggan)
);

-- Tabel Barang
CREATE TABLE IF NOT EXISTS barang (
    id_barang INT AUTO_INCREMENT PRIMARY KEY,
    id_kategori CHAR(3) NOT NULL,
    nama_barang VARCHAR(100) NOT NULL,
    deskripsi TEXT,
    harga DECIMAL(15,2) NOT NULL,
    stok INT NOT NULL DEFAULT 0,
    FOREIGN KEY (id_kategori) REFERENCES kategori (id_kategori),
    INDEX idx_nama_barang (nama_barang),
    INDEX idx_harga (harga)
);

-- Tabel Pembelian
CREATE TABLE IF NOT EXISTS pembelian (
    id_pembelian INT AUTO_INCREMENT PRIMARY KEY,
    id_pemasok INT NOT NULL,
    tanggal DATE NOT NULL,
    total_harga DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (id_pemasok) REFERENCES pemasok(id_pemasok),
    INDEX idx_tanggal (tanggal)
);

-- Tabel Detail Pembelian
CREATE TABLE IF NOT EXISTS detail_pembelian (
    id_dpembelian INT AUTO_INCREMENT PRIMARY KEY,
    id_pembelian INT NOT NULL,
    id_barang INT NOT NULL,
    harga DECIMAL(15,2) NOT NULL,
    jumlah INT NOT NULL,
    subtotal DECIMAL(15,2) NOT NULL,
    FOREIGN KEY (id_pembelian) REFERENCES pembelian(id_pembelian),
    FOREIGN KEY (id_barang) REFERENCES barang(id_barang),
    INDEX idx_id_pembelian (id_pembelian),
    INDEX idx_id_barang (id_barang)
);

-- Tabel Penjualan
CREATE TABLE IF NOT EXISTS penjualan (
  id_penjualan INT AUTO_INCREMENT PRIMARY KEY,
  id_pelanggan INT NOT NULL,
  tanggal DATE NOT NULL,
  total_harga DECIMAL(15,2) NOT NULL,
  FOREIGN KEY (id_pelanggan) REFERENCES pelanggan(id_pelanggan),
  INDEX idx_tanggal (tanggal)
);

-- Tabel Detail Penjualan
CREATE TABLE IF NOT EXISTS detail_penjualan (
  id_dpenjualan INT AUTO_INCREMENT PRIMARY KEY,
  id_penjualan INT NOT NULL,
  id_barang INT NOT NULL,
  harga DECIMAL(15,2) NOT NULL,
  jumlah INT NOT NULL,
  subtotal DECIMAL(15,2) NOT NULL,
  FOREIGN KEY (id_penjualan) REFERENCES penjualan(id_penjualan),
  FOREIGN KEY (id_barang) REFERENCES barang(id_barang),
  INDEX idx_id_penjualan (id_penjualan),
  INDEX idx_id_barang (id_barang)
);



INSERT INTO pemasok (nama_pemasok, alamat, no_hp) VALUES
('PT Denka Pratama', 'jl.merdeka', '2147483647'),
('PT DOSS', 'jl.perdana', '2147483647'),
('PT Sinar Jaya Abadi', 'jl.pancasila', '2147483647'),
('PT Gudang Kamera', 'jl.media', '2147483647');

INSERT INTO pelanggan (nama_pelanggan, alamat, no_hp) VALUES
('Riki', 'jl.satu', '2147483647'),
('Surya', 'jl.dua', '2147483647'),
('Bagas', 'jl.tiga', '2147483647'),
('Gita', 'jl.empat', '2147483647');

INSERT INTO kategori (id_kategori, kategori) VALUES
('BT', 'Baterai'),
('FL', 'Flash'),
('FT', 'Filter'),
('GB', 'Gimbal'),
('KM', 'Kamera'),
('LS', 'Lensa'),
('TP', 'Tripod');

INSERT INTO barang (nama_barang, deskripsi, id_kategori, harga, stok) VALUES
('Canon EOS 800D', 'Kamera DSLR dengan konektivitas WiFi', 'KM', 8000000.00, 15),
('Sony A7 Mark IV', 'Kamera mirrorless full frame dengan autofocus cepat', 'KM', 40000000.00, 8),
('Lumix S5 Mark II', 'Kamera mirrorless ini ideal untuk videografi', 'KM', 22000000.00, 5),
('Godox TT600', 'Flash speedlite dengan harga yang terjangkau', 'FL', 250000.00, 20),
('DJI RS4 Combo', 'Gimbal untuk stabilitas saat pengambilan video', 'GB', 7000000.00, 10),
('Filter ND 8-200', 'Filter untuk mengatur pencahayaan saat mengambil gambar', 'FT', 300000.00, 25),
('Filter CPL', 'Filter polarizer untuk mengurangi pantulan', 'FT', 150000.00, 30),
('DJI RS3 Mini', 'Gimbal mini untuk semua kebutuhan videografi', 'GB', 5000000.00, 12),
('Baterai NPF 50', 'Baterai cadangan untuk kamera DSLR', 'BT', 200000.00, 40),
('Baterai LP-EL 8', 'Baterai cadangan untuk kamera mirrorless', 'BT', 250000.00, 35),
('Godox SK400', 'Lampu studio dengan distribusi cahaya yang merata', 'FL', 4500000.00, 8),
('Tamron 70-300 F2.8 USM', 'Lensa telephoto dengan aperture cepat', 'LS', 15000000.00, 6),
('Sony 24-70 F2.8 GM', 'Lensa profesional untuk kamera Sony', 'LS', 28000000.00, 5),
('Yongnuo 50mm 1.8 Mark II', 'Lensa prime dengan fokus cepat untuk portret', 'LS', 800000.00, 20),
('Beiqe Q-999', 'Drone dengan kamera HD', 'TP', 5500000.00, 9),
('Takara L290', 'Drone dengan fitur pengambilan gambar canggih', 'TP', 6000000.00, 7);



ALTER TABLE pembelian
ADD CONSTRAINT fk_id_pemasok  -- Nama constraint, bisa diubah sesuai kebutuhan Anda
FOREIGN KEY (id_pemasok)
REFERENCES pemasok(id_pemasok);

ALTER TABLE barang
ADD CONSTRAINT fk_id_kategori
FOREIGN KEY (id_kategori)
REFERENCES kategori(id_kategori);