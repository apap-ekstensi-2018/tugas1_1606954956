package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.model.FakultasModel;
import com.example.model.MahasiswaModel;
import com.example.model.ProgramStudyModel;
import com.example.model.UniversitasModel;

@Mapper
public interface MahasiswaMapper
{
	@Select("select m.* from mahasiswa m")
    List<MahasiswaModel> selectAllMahasiswa();
	
	@Select("SELECT * FROM mahasiswa WHERE npm = #{npm}")
    MahasiswaModel selectMahasiswa (@Param("npm") String npm);
	
	@Select("SELECT p. * FROM program_studi p")
    List<ProgramStudyModel> selectAllProdi();
	
	@Select("SELECT * FROM program_studi WHERE id = #{id}")
	ProgramStudyModel selectProdi (@Param("id") int id);
	
	@Select("SELECT * FROM fakultas")
    List<FakultasModel> selectAllFakultas();
	
	@Select("SELECT * FROM universitas")
    List<UniversitasModel> selectAllUniversitas();
	
	@Select("SELECT * FROM fakultas WHERE id = #{id}")
    FakultasModel selectFakultas (@Param("id") int id);
    
/*	@Select("SELECT * FROM universitas")
	List<ProgramStudyModel> selectAllUniversity();*/
	
	@Select("SELECT * FROM universitas WHERE id = #{id}")
    UniversitasModel selectUniversitas (@Param("id") int id);
	
	@Select("SELECT * FROM universitas WHERE id = #{id}")
    int selectUniv (@Param("id") int id);
    
    @Select("SELECT * FROM fakultas WHERE id = #{id}")
    int selectFak(@Param("id") int id);
	
	@Select("SELECT substring(npm, 10,3) FROM mahasiswa WHERE npm LIKE '${npm}%' ORDER BY substring(npm, 10, 3) DESC LIMIT 1")
    String selectMahasiswaNpm (@Param("npm")String npm);
    
    @Insert("INSERT INTO mahasiswa (npm, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, tahun_masuk, jalur_masuk, status, id_prodi) "
    		+ "VALUES (#{npm}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{agama}, #{golongan_darah}, #{tahun_masuk}, #{jalur_masuk}, #{status},#{id_prodi})")
    void addMahasiswa (MahasiswaModel mahasiswa);
    
    @Update("UPDATE mahasiswa SET npm = #{npm_baru}, nama = #{nama},  tempat_lahir = #{tempat_lahir},  tanggal_lahir = #{tanggal_lahir},"
    		+ "jenis_kelamin = #{jenis_kelamin},status = #{status}, tahun_masuk = #{tahun_masuk}, jalur_masuk = #{jalur_masuk}, id_prodi = #{id_prodi} where npm = #{npm_lama}")
    void ubahMahasiswa(@Param("npm_lama") String npm_lama,
    		@Param("npm_baru") String npm_baru,
            @Param("nama") String nama,
            @Param("tempat_lahir") String tempat_lahir,
            @Param("tanggal_lahir") String tanggal_lahir,
            @Param("jenis_kelamin") int jenis_kelamin,
            @Param("agama") String agama,
            @Param("golongan_darah") String golongan_darah,
            @Param("status") String status,
            @Param("tahun_masuk") String tahun_masuk,
            @Param("jalur_masuk") String jalur_masuk,
            @Param("id_prodi") int id_prodi);
    
    @Select("select count(*) from mahasiswa where tahun_masuk =#{tahun_masuk} and id_prodi= #{prodi} and status = 'Lulus'")
    Integer selectKelulusan (@Param ("tahun_masuk") String tahun_masuk, @Param("prodi")int prodi);
    
    @Select("select count(*) from mahasiswa where tahun_masuk=#{tahun_masuk} and id_prodi= #{prodi}")
    Integer selectTahunKelulusan (@Param ("tahun_masuk") String tahun_masuk, @Param("prodi")int prodi);
    
    @Select("SELECT * FROM fakultas where id_univ=#{id_univ}")
    List<FakultasModel> selectAllFakultasbyUniv(@Param ("id_univ")int id_univ);
    
    @Select("SELECT * FROM program_studi where id_fakultas=#{id_fakultas}")
    List<ProgramStudyModel> selectProdibyFak(@Param ("id_fakultas")int id_fakultas);
    
    @Select("SELECT * FROM mahasiswa where id_prodi = #{id_prodi}")
    List<MahasiswaModel> selectMahasiswaByProdi (@Param ("id_prodi") int id_prodi);
}

