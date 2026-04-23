import java.text.NumberFormat
import java.util.Locale

fun Int.formatRupiah(): String {
    val localeID = Locale("in", "ID")
    val formatter = NumberFormat.getNumberInstance(localeID)
    return "Rp " + formatter.format(this)
}

class Barang(val nama: String, val harga: Int, stokAwal: Int) {
    var stok: Int = stokAwal
        private set

    fun kurangiStok(jumlah: Int): Boolean {
        if (stok >= jumlah) {
            stok -= jumlah
            return true
        }
        return false
    }
}

class Pembeli(val nama: String, uangAwal: Int) {
    var uangTunai: Int = uangAwal
        private set
    var poinMember: Int = 0
        private set

    fun bayar(jumlah: Int): Boolean {
        if (uangTunai >= jumlah) {
            uangTunai -= jumlah
            return true
        }
        return false
    }

    fun tambahPoin(tambahan: Int) {
        poinMember += tambahan
    }
}

class Kasir(val nama: String) {
    fun prosesTransaksi(pembeli: Pembeli, barang: Barang, jumlahBeli: Int) {
        println("\n--- Memproses Transaksi ---")
        println("Kasir ${this.nama} melayani ${pembeli.nama} membeli ${jumlahBeli}x ${barang.nama} (@ ${barang.harga.formatRupiah()})")

        val totalHarga = barang.harga * jumlahBeli

        if (barang.stok < jumlahBeli) {
            println("[GAGAL] Stok ${barang.nama} tidak mencukupi. Sisa stok: ${barang.stok}")
            return
        }

        if (pembeli.uangTunai < totalHarga) {
            println("[GAGAL] Uang tunai ${pembeli.nama} kurang. Total tagihan: ${totalHarga.formatRupiah()}, Uang: ${pembeli.uangTunai.formatRupiah()}")
            return
        }

        if (barang.kurangiStok(jumlahBeli) && pembeli.bayar(totalHarga)) {
            pembeli.tambahPoin(10)
            println("[SUKSES] Transaksi berhasil diproses!")
            println("Sisa Uang  : ${pembeli.uangTunai.formatRupiah()}")
            println("Poin Member: ${pembeli.poinMember} Poin")
            println("Sisa Stok  : ${barang.stok} unit")
        }
    }
}

fun main() {
    // Nama Kasir dan Pembeli sudah diperbarui
    val kasir1 = Kasir("Vera")
    val pembeli1 = Pembeli("Andi", 50000)

    val barang1 = Barang("Buku Catatan", 15000, 2)
    val barang2 = Barang("Kalkulator", 100000, 5)

    println("=== SIMULASI SISTEM ITK-MART ===")
    println("Status Awal: Uang ${pembeli1.nama} = ${pembeli1.uangTunai.formatRupiah()} | Poin ${pembeli1.nama} = ${pembeli1.poinMember}")

    kasir1.prosesTransaksi(pembeli1, barang1, 3)
    kasir1.prosesTransaksi(pembeli1, barang2, 1)
    kasir1.prosesTransaksi(pembeli1, barang1, 1)
    kasir1.prosesTransaksi(pembeli1, barang1, 1)
}