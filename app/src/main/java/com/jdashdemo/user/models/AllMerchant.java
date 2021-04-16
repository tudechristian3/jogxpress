package com.jdashdemo.user.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AllMerchant {
    @SerializedName("id_merchant")
    @Expose
    private String idMerchant;
    @SerializedName("nama_merchant")
    @Expose
    private String namaMerchant;
    @SerializedName("alamat_merchant")
    @Expose
    private String alamatMerchant;
    @SerializedName("latitude_merchant")
    @Expose
    private String latitudeMerchant;
    @SerializedName("id_fitur")
    @Expose
    private String idFitur;
    @SerializedName("longitude_merchant")
    @Expose
    private String longitudeMerchant;
    @SerializedName("jam_buka")
    @Expose
    private String jamBuka;
    @SerializedName("jam_tutup")
    @Expose
    private String jamTutup;
    @SerializedName("deskripsi_merchant")
    @Expose
    private String deskripsiMerchant;
    @SerializedName("category_merchant")
    @Expose
    private String categoryMerchant;
    @SerializedName("foto_merchant")
    @Expose
    private String fotoMerchant;
    @SerializedName("telepon_merchant")
    @Expose
    private String teleponMerchant;
    @SerializedName("status_merchant")
    @Expose
    private String statusMerchant;
    @SerializedName("open_merchant")
    @Expose
    private String openMerchant;
    @SerializedName("saldo")
    @Expose
    private String saldo;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("status_promo")
    @Expose
    private String statusPromo;
    @SerializedName("jarak_minimum")
    @Expose
    private String jarakMinimum;
    @SerializedName("wallet_minimum")
    @Expose
    private String walletMinimum;
    @SerializedName("nama_kategori")
    @Expose
    private String namaKategori;

    public String getIdMerchant() {
        return idMerchant;
    }

    public void setIdMerchant(String idMerchant) {
        this.idMerchant = idMerchant;
    }

    public String getNamaMerchant() {
        return namaMerchant;
    }

    public void setNamaMerchant(String namaMerchant) {
        this.namaMerchant = namaMerchant;
    }

    public String getAlamatMerchant() {
        return alamatMerchant;
    }

    public void setAlamatMerchant(String alamatMerchant) {
        this.alamatMerchant = alamatMerchant;
    }

    public String getLatitudeMerchant() {
        return latitudeMerchant;
    }

    public void setLatitudeMerchant(String latitudeMerchant) {
        this.latitudeMerchant = latitudeMerchant;
    }

    public String getIdFitur() {
        return idFitur;
    }

    public void setIdFitur(String idFitur) {
        this.idFitur = idFitur;
    }

    public String getLongitudeMerchant() {
        return longitudeMerchant;
    }

    public void setLongitudeMerchant(String longitudeMerchant) {
        this.longitudeMerchant = longitudeMerchant;
    }

    public String getJamBuka() {
        return jamBuka;
    }

    public void setJamBuka(String jamBuka) {
        this.jamBuka = jamBuka;
    }

    public String getJamTutup() {
        return jamTutup;
    }

    public void setJamTutup(String jamTutup) {
        this.jamTutup = jamTutup;
    }

    public String getDeskripsiMerchant() {
        return deskripsiMerchant;
    }

    public void setDeskripsiMerchant(String deskripsiMerchant) {
        this.deskripsiMerchant = deskripsiMerchant;
    }

    public String getCategoryMerchant() {
        return categoryMerchant;
    }

    public void setCategoryMerchant(String categoryMerchant) {
        this.categoryMerchant = categoryMerchant;
    }

    public String getFotoMerchant() {
        return fotoMerchant;
    }

    public void setFotoMerchant(String fotoMerchant) {
        this.fotoMerchant = fotoMerchant;
    }

    public String getTeleponMerchant() {
        return teleponMerchant;
    }

    public void setTeleponMerchant(String teleponMerchant) {
        this.teleponMerchant = teleponMerchant;
    }

    public String getStatusMerchant() {
        return statusMerchant;
    }

    public void setStatusMerchant(String statusMerchant) {
        this.statusMerchant = statusMerchant;
    }

    public String getOpenMerchant() {
        return openMerchant;
    }

    public void setOpenMerchant(String openMerchant) {
        this.openMerchant = openMerchant;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStatusPromo() {
        return statusPromo;
    }

    public void setStatusPromo(String statusPromo) {
        this.statusPromo = statusPromo;
    }

    public String getJarakMinimum() {
        return jarakMinimum;
    }

    public void setJarakMinimum(String jarakMinimum) {
        this.jarakMinimum = jarakMinimum;
    }

    public String getWalletMinimum() {
        return walletMinimum;
    }

    public void setWalletMinimum(String walletMinimum) {
        this.walletMinimum = walletMinimum;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }
}
