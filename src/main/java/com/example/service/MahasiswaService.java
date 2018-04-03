package com.example.service;

import com.example.model.FakultasModel;
import com.example.model.MahasiswaModel;
import com.example.model.ProgramStudyModel;

import com.example.model.UniversitasModel;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface MahasiswaService {
//    List<MahasiswaModel> selectAllMahasiswa();

   MahasiswaModel selectMahasiswa (String npm);
    
   ProgramStudyModel selectProdi(int id);
   
   FakultasModel selectFakultas (int id);
   
   UniversitasModel selectUniversitas (int id);
   
   
   
   List<MahasiswaModel> selectAllMahasiswa();
   
   List<FakultasModel> selectAllFakultas();
   
   List<ProgramStudyModel> selectAllProdi();
   
   List<UniversitasModel> selectAllUniversitas();
   
   
   List<FakultasModel> selectAllFakultasbyUniv(int id_univ);
   
   List<ProgramStudyModel> selectProdibyFak (int id_fakultas);
   
   List<MahasiswaModel> selectMahasiswaByProdi(int id_prodi);
   
   
   String selectMahasiswaNpm (String npm);
   
   void addMahasiswa (MahasiswaModel mahasiswa);
   
   void ubahMahasiswa( String npm_lama, String npm_baru, String nama,String tempat_lahir,String tanggal_lahir,int jenis_kelamin,String agama,String golongan_darah,String status,String tahun_masuk,String jalur_masuk,int id_prodi);
   
   Integer selectKelulusan (String tahun_masuk, int prodi);
   
   Integer selectTahunKelulusan (String tahun_masuk, int prodi);
   
   int selectUniv (int id);
   int selectFak(int id);
   
   
	
}
