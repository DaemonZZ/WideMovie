package com.app.movie.sharelibrary.datasource.api

import com.app.movie.sharelibrary.R


enum class SortField(val value: String) {
    Time("_id"),
    LastUpdated("modified.time"),
    CreatedYear("year")
}

enum class TypeList(val value: String, val title: Int) {
    New("phim-moi", R.string.phim_moi),
    Series("phim-bo", R.string.phim_bo),
    Movie("phim-le", R.string.phim_le),
    TvShow("tv-shows", R.string.tv_shows),
    Anime("hoat-hinh", R.string.hoat_hinh),
    VietSub("phim-vietsub", R.string.phim_vietsub),
    VietDub("phim-thuyet-minh", R.string.phim_thuyet_minh),
    VietFakeSub("phim-long-tieng", R.string.phim_long_tieng),
    OnGoing("phim-bo-dang-chieu", R.string.phim_bo_dang_chieu),
    Completed("phim-bo-hoan-thanh", R.string.phim_bo_hoan_thanh),
    ComingSoon("phim-sap-chieu", R.string.phim_sap_chieu),
    SubTeam("subteam", R.string.phim_subteam),
}

enum class Category(val value: String, val title: Int) {
    All("", R.string.phim_all),
    Action("hanh-dong", R.string.action),
    Romance("tinh-cam", R.string.romance),
    Comedy("hai-huoc", R.string.comedy),
    CoTrang("co-trang", R.string.co_trang),
    TamLy("tam-ly", R.string.tam_ly),
    HinhSu("hinh-su", R.string.hinh_su),
    ChienTranh("chien-tranh", R.string.chien_tranh),
    TheThao("the-thao", R.string.the_thao),
    VoThuat("vo-thuat", R.string.vo_thuat),
    VienTuong("vien-tuong", R.string.vien_tuong),
    PhieuLuu("phieu-luu", R.string.phieu_luu),
    KhoaHoc("khoa-hoc", R.string.khoa_hoc),
    KinhDi("kinh-di", R.string.kinh_di),
    AmNhac("am-nhac", R.string.am_nhac),
    ThanThoai("than-thoai", R.string.than_thoai),
    TaiLieu("tai-lieu", R.string.tai_lieu),
    GiaDinh("gia-dinh", R.string.gia_dinh),
    ChinhKich("chinh-kich", R.string.chinh_kich),
    BiAn("bi-an", R.string.bi_an),
    HocDuong("hoc-duong", R.string.hoc_duong),
    KinhDien("kinh-dien", R.string.kinh_dien),
    Phim18("phim-18", R.string.phim_18),
}

enum class Countries(val value: String, val title: Int) {
    All("", R.string.phim_all),
    VietNam("viet-nam", R.string.viet_nam),
    AuMy("au-my", R.string.au_my),
    TrungQuoc("trung-quoc", R.string.trung_quoc),
    NhatBan("nhat-ban", R.string.nhat_ban),
    Anh("anh", R.string.anh),
}

enum class Year(val value: String) {
    All(""),
    Y2015("2015"),
    Y2016("2016"),
    Y2017("2017"),
    Y2018("2018"),
    Y2019("2019"),
    Y2020("2020"),
    Y2021("2021"),
    Y2022("2022"),
    Y2023("2023"),
    Y2024("2024"),
}
