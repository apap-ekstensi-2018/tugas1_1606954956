package com.example.service;

import com.example.dao.MahasiswaMapper;
import com.example.model.FakultasModel;
import com.example.model.MahasiswaModel;
import com.example.model.ProgramStudyModel;
import com.example.model.UniversitasModel;

import lombok.extern.slf4j.Slf4j;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MahasiswaServiceDatabase implements MahasiswaService{
    @Autowired
    private MahasiswaMapper mahasiswaMapper;

    @Override
    public MahasiswaModel selectMahasiswa(String npm){
        log.info("Select mahasiswa with npm {}", npm);
        return mahasiswaMapper.selectMahasiswa(npm);
    }

	@Override
	public ProgramStudyModel selectProdi(int id) {
		return mahasiswaMapper.selectProdi(id);
	}

	@Override
	public FakultasModel selectFakultas(int id) {
		// TODO Auto-generated method stub
		return mahasiswaMapper.selectFakultas(id);
	}

	@Override
	public UniversitasModel selectUniversitas(int id) {
		// TODO Auto-generated method stub
		return mahasiswaMapper.selectUniversitas(id);
	}

	@Override
	public String selectMahasiswaNpm(String npm) {
		// TODO Auto-generated method stub
		return mahasiswaMapper.selectMahasiswaNpm(npm);
	}

	@Override
	public void addMahasiswa(MahasiswaModel mahasiswa) {
		// TODO Auto-generated method stub
		mahasiswaMapper.addMahasiswa (mahasiswa);
	}

	@Override
	public void ubahMahasiswa( String npm_lama,String npm_baru, String nama,
            String tempat_lahir,String tanggal_lahir,
            int jenis_kelamin,String agama,
            String golongan_darah,
            String status,
            String tahun_masuk,
            String jalur_masuk,
            int id_prodi) 
	{
		log.info("mahasiswa " + npm_lama + "ubah");
        mahasiswaMapper.ubahMahasiswa(  npm_lama, npm_baru, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, status, tahun_masuk, jalur_masuk, id_prodi);
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer selectKelulusan(String tahun_masuk, int prodi) {
		// TODO Auto-generated method stub
		return mahasiswaMapper.selectKelulusan(tahun_masuk, prodi);
	}

	@Override
	public Integer selectTahunKelulusan(String tahun_masuk, int prodi) {
		// TODO Auto-generated method stub
		return mahasiswaMapper.selectTahunKelulusan(tahun_masuk, prodi);
	}

	@Override
	public List<MahasiswaModel> selectAllMahasiswa() {
		log.info ("select all mahasiswa");
        return mahasiswaMapper.selectAllMahasiswa();
	}

	
	@Override
	public List<FakultasModel> selectAllFakultas() {
		log.info ("select all fakultas");
        return mahasiswaMapper.selectAllFakultas();
	}

	@Override
	public List <ProgramStudyModel> selectAllProdi() {
		// TODO Auto-generated method stub
		return mahasiswaMapper.selectAllProdi();
	}
	
	
	@Override
	public List<UniversitasModel> selectAllUniversitas() {
		log.info("select all universitas");
		return mahasiswaMapper.selectAllUniversitas();
	}

	@Override
	public List<FakultasModel> selectAllFakultasbyUniv(int id_univ) {
		log.info("show by univ");
		return mahasiswaMapper.selectAllFakultasbyUniv(id_univ);
	}

	@Override
	public List<ProgramStudyModel> selectProdibyFak(int id_fakultas) {
		log.info("show prodi");
		return mahasiswaMapper.selectProdibyFak(id_fakultas);
	}

	@Override
	public List<MahasiswaModel> selectMahasiswaByProdi(int id_prodi) {
		log.info(" showmahasiswa ");
		return mahasiswaMapper.selectMahasiswaByProdi(id_prodi);
	}

	@Override
	public int selectUniv(int id) {
		log.info("show by univ");
		return mahasiswaMapper.selectUniv(id);
	}

	@Override
	public int selectFak(int id) {
		log.info("show prodi");
		return mahasiswaMapper.selectFak(id);
	}



	
}