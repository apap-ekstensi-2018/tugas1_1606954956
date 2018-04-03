package com.example.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.model.FakultasModel;
import com.example.model.MahasiswaModel;
import com.example.model.ProgramStudyModel;
import com.example.model.UniversitasModel;
import com.example.service.MahasiswaService;

@Controller
public class MahasiswaController
{
    @Autowired
    MahasiswaService mahasiswaDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }

    
    @RequestMapping("/mahasiswa")
    public String mahasiswa(Model model, @RequestParam(value = "npm", required = false) String npm){
    	MahasiswaModel mahasiswa = mahasiswaDAO.selectMahasiswa(npm);
        ProgramStudyModel prodi = mahasiswaDAO.selectProdi(mahasiswa.getId_prodi());
        FakultasModel fakultas = mahasiswaDAO.selectFakultas(prodi.getId_fakultas());
        UniversitasModel universitas = mahasiswaDAO.selectUniversitas(fakultas.getId_univ());
        if (mahasiswa != null){
            model.addAttribute("mahasiswa", mahasiswa);
            model.addAttribute("prodi", prodi);
            model.addAttribute("fakultas", fakultas);
            model.addAttribute("universitas", universitas);
            return "view";
        }else{
            model.addAttribute("npm", npm);
            return "not-found";
        }
    }
    
    @RequestMapping("/mahasiswa/tambah")
    public String add (Model model)
    {
        return "form-add";
    }

    @RequestMapping( value = "/mahasiswa/tambah", method = RequestMethod.POST)
    public String addSubmit (
    				Model model, @RequestParam(value = "nama", required = false) String nama,
					 @RequestParam(value = "tempat_lahir", required = false) String tempat_lahir,
					 @RequestParam(value = "tanggal_lahir", required = false) String tanggal_lahir,
					 @RequestParam(value = "jenis_kelamin", required = false) int jenis_kelamin,
					 @RequestParam(value = "agama", required = false) String agama,
					 @RequestParam(value = "golongan_darah", required = false) String golongan_darah,
					 @RequestParam(value = "tahun_masuk", required = false) String tahun_masuk,
					 @RequestParam(value = "jalur_masuk", required = false) String jalur_masuk,
					 @RequestParam(value = "id_prodi", required = false) int id_prodi) 
    {
    	ProgramStudyModel prodi = mahasiswaDAO.selectProdi(id_prodi);
    	FakultasModel fakultas = mahasiswaDAO.selectFakultas(prodi.getId_fakultas());
    	UniversitasModel universitas = mahasiswaDAO.selectUniversitas(fakultas.getId_univ());
    	
    	String duaPer = "" + tahun_masuk.charAt(2) + tahun_masuk.charAt(3);
    	String digitSel = universitas.getKode_univ() + prodi.getKode_prodi();
    	String jm = "";
    	if (jalur_masuk.equals("Undangan Reguler/SNMPTN")) {
    		jm = "54";
    	} else if (jalur_masuk.equals("Ujian Tulis Mandiri")){
    		jm = "62";
    	} else if (jalur_masuk.equals("Undangan Paralel/PPKB")) {
    		jm = "55";
    	} else if (jalur_masuk.equals("Ujian Tulis Bersama/SBMPTN")) {
    		jm = "57";
    	} else {
    		jm = "53";
    	}
    	
    	String npmHsl = duaPer + digitSel + jm;
    	String isNpm = mahasiswaDAO.selectMahasiswaNpm(npmHsl);
    	if(isNpm != null) {
    		isNpm = "" + (Integer.parseInt(isNpm) + 1);
    		if (isNpm.length() == 1) {
    			npmHsl = npmHsl + "00" + isNpm;
    		}else if (isNpm.length() == 2) {
    			npmHsl = npmHsl + "0" + isNpm;
    		}else {
    			npmHsl = npmHsl + isNpm;
    		}
    	}else {
    		npmHsl = npmHsl + "001";
    	}
    	//System.out.println(npmHsl);
    	MahasiswaModel mahasiswa = new MahasiswaModel (npmHsl, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, agama, golongan_darah, tahun_masuk, jalur_masuk, "Aktif", id_prodi);
    	mahasiswaDAO.addMahasiswa(mahasiswa);
       	model.addAttribute("mahasiswa", mahasiswa);
       	model.addAttribute("message", "Mahasiswa dengan NPM " + npmHsl + " berhasil ditambahkan");
       	return "success-add";
    }
    
    @RequestMapping("/mahasiswa/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        MahasiswaModel mahasiswa = mahasiswaDAO.selectMahasiswa (npm);

        if (mahasiswa != null) {
            model.addAttribute ("mahasiswa", mahasiswa);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }
    
   
    @RequestMapping("/mahasiswa/ubah/{npm}")
    public String ubahMahasiswa(@PathVariable(value = "npm") String npm, Model model) {
    	MahasiswaModel mahasiswa = mahasiswaDAO.selectMahasiswa(npm);
    	if (mahasiswa != null) {
    		model.addAttribute("mahasiswa", mahasiswa);
    		return "form-update";
    	}
    	else {
    		model.addAttribute("mahasiswa", mahasiswa);
    		return "not-found";
    	}
    }
    
    @RequestMapping(value = "/mahasiswa/ubah/{npm}", method = RequestMethod.POST)
    public String ubahSubmit (@ModelAttribute ("mahasiswa") MahasiswaModel mahasiswa, ModelMap model)
    {
    	if(mahasiswa.getNpm() == null || mahasiswa.getNpm() == "") {
    		return "error";
    	}
    	

    	MahasiswaModel mahasiswaValid = mahasiswaDAO.selectMahasiswa(mahasiswa.getNpm());

    	if (mahasiswa.getTahun_masuk().equals(mahasiswaValid.getTahun_masuk())&& mahasiswa.getJalur_masuk().equals(mahasiswaValid.getJalur_masuk())&& mahasiswa.getId_prodi()==mahasiswaValid.getId_prodi()) {

        	if (mahasiswaValid != null) {
        		mahasiswaDAO.ubahMahasiswa(mahasiswa.getNpm(),mahasiswa.getNpm(),
        				mahasiswa.getNama(), mahasiswa.getTempat_lahir(), mahasiswa.getTanggal_lahir(), mahasiswa.getJenis_kelamin(),
        				mahasiswa.getAgama(), mahasiswa.getGolongan_darah(), "aktif", mahasiswa.getTahun_masuk(),
        				mahasiswa.getJalur_masuk(), mahasiswa.getId_prodi());
        				model.addAttribute("message", "Mahasiswa dengan NPM " + mahasiswa.getNpm() + " berhasil diubah");
        	}
        	
            return "success-update";
    	}
    	else {
    		ProgramStudyModel prodi = mahasiswaDAO.selectProdi(mahasiswa.getId_prodi());
        	FakultasModel fakultas = mahasiswaDAO.selectFakultas(prodi.getId_fakultas());
        	UniversitasModel universitas = mahasiswaDAO.selectUniversitas(fakultas.getId_univ());
        	
        	String duaPer = "" + mahasiswa.getTahun_masuk().charAt(2) + mahasiswa.getTahun_masuk().charAt(3);
        	String digitSel = universitas.getKode_univ() + prodi.getKode_prodi();
        	String jm = "";
        	if (mahasiswa.getJalur_masuk().equals("Undangan Reguler/SNMPTN")) {
        		jm = "54";
        	} else if (mahasiswa.getJalur_masuk().equals("Ujian Tulis Mandiri")){
        		jm = "62";
        	} else if (mahasiswa.getJalur_masuk().equals("Undangan Paralel/PPKB")) {
        		jm = "55";
        	} else if (mahasiswa.getJalur_masuk().equals("Ujian Tulis Bersama/SBMPTN")) {
        		jm = "57";
        	} else {
        		jm = "53";
        	}
        	
        	String npmHsl = duaPer + digitSel + jm;
        	String isNpm = mahasiswaDAO.selectMahasiswaNpm(npmHsl);
        	if(isNpm != null) {
        		isNpm = "" + (Integer.parseInt(isNpm) + 1);
        		if (isNpm.length() == 1) {
        			npmHsl = npmHsl + "00" + isNpm;
        		}else if (isNpm.length() == 2) {
        			npmHsl = npmHsl + "0" + isNpm;
        		}else {
        			npmHsl = npmHsl + isNpm;
        		}
        	}else {
        		npmHsl = npmHsl + "001";
        	}
    		if (mahasiswaValid != null) {
        		mahasiswaDAO.ubahMahasiswa(mahasiswa.getNpm(),npmHsl,
        				mahasiswa.getNama(), mahasiswa.getTempat_lahir(), mahasiswa.getTanggal_lahir(), mahasiswa.getJenis_kelamin(),
        				mahasiswa.getAgama(), mahasiswa.getGolongan_darah(), "aktif", mahasiswa.getTahun_masuk(),
        				mahasiswa.getJalur_masuk(), mahasiswa.getId_prodi());
        				model.addAttribute("message", "Mahasiswa dengan NPM " + mahasiswa.getNpm() + " berhasil diubah, NPM Mahasiswa menjadi " + npmHsl);
        				
        	}
    		
			return "success-update";
           
    	}
    	
    }
    
    @RequestMapping("/kelulusan")
    public String kelulusan(Model model,
    						@RequestParam(value = "tahun_masuk", required = false) Optional<String> tahun_masuk,
    						@RequestParam(value = "prodi", required = false) Optional<String> prodi)
{
	if (tahun_masuk.isPresent() && prodi.isPresent()) {
		int jml_mahasiswa = mahasiswaDAO.selectTahunKelulusan(tahun_masuk.get(), Integer.parseInt(prodi.get()));
		int jml_mahasiswaLulus = mahasiswaDAO.selectKelulusan(tahun_masuk.get(), Integer.parseInt(prodi.get()));
		double peresent = ((double) jml_mahasiswaLulus / (double) jml_mahasiswa) * 100;
		String presentase = new DecimalFormat("##.##").format(peresent);
		ProgramStudyModel program_studi = mahasiswaDAO.selectProdi(Integer.parseInt(prodi.get()));
		FakultasModel fakultas = mahasiswaDAO.selectFakultas(program_studi.getId_fakultas());
		UniversitasModel universitas = mahasiswaDAO.selectUniversitas(fakultas.getId_univ());
		model.addAttribute("jml_mahasiswa", jml_mahasiswa);
		model.addAttribute("jml_mahasiswaLulus", jml_mahasiswaLulus);
		model.addAttribute("presentase", presentase);
		model.addAttribute("tahun_masuk", tahun_masuk.get());
		model.addAttribute("prodi", program_studi.getNama_prodi());
		model.addAttribute("fakultas", fakultas.getNama_fakultas());
		model.addAttribute("universitas", universitas.getNama_univ());
		
		return "lihatKelulusan";
		
	}else {
		List<ProgramStudyModel> programStudi = mahasiswaDAO.selectAllProdi();
		model.addAttribute("programStudi", programStudi);
		return "kelulusan";
	}
}
    @RequestMapping("/mahasiswa/cari")
	public String cariMahasiswa(Model model,
			@RequestParam(value = "universitas", required = false) String univ,
			@RequestParam(value = "idfakultas", required = false) String idfakultas,
			@RequestParam(value = "idprodi", required = false) String idprodi)
	{

			List<UniversitasModel> universitas = mahasiswaDAO.selectAllUniversitas();
			model.addAttribute ("universitas", universitas);
            	if(univ== null) {
            		return "cariUniv";
            	} else {
            		int idUniv = Integer.parseInt(univ);
            		UniversitasModel univers = mahasiswaDAO.selectUniversitas(idUniv);
            		int idUnivv = mahasiswaDAO.selectUniv(idUniv);
            		List<FakultasModel> fakultas = mahasiswaDAO.selectAllFakultasbyUniv(idUnivv);
            		if (idfakultas == null) {
            			model.addAttribute("fakultas", fakultas);
            			model.addAttribute("selectUniv",idUniv);
                		return "cariFakl";
            		}  
            		else {
            			int idFakul = Integer.parseInt(idfakultas);
            			FakultasModel fakultass = mahasiswaDAO.selectFakultas(idFakul);
            			int idFaks = mahasiswaDAO.selectFak(idFakul);
            			model.addAttribute("selectFak", idFakul);
            			List<ProgramStudyModel> prodd = mahasiswaDAO.selectProdibyFak(idFaks);
            			
            			if(idprodi == null) {
            				model.addAttribute("fakultas", fakultas);
                			model.addAttribute("selectUniv",idUniv);
                			model.addAttribute("prodi",prodd);
                			
                    		return "cariProdi";
            			}else {
            				int idprod = Integer.parseInt(idprodi);
            				ProgramStudyModel prodis = mahasiswaDAO.selectProdi(idprod);
            				List<MahasiswaModel> mahasiswaByProdi = mahasiswaDAO.selectMahasiswaByProdi(idprod);
            				model.addAttribute("mahasiswaByProdi", mahasiswaByProdi);
            				ProgramStudyModel prodiSelectObject = mahasiswaDAO.selectProdi(idprod);
            				model.addAttribute("prodiSelectObject", prodiSelectObject);
            				
            				return "tabelMhs";
            				
            			}
            			
            		}
            		
            	}
            
	        
	}
}
