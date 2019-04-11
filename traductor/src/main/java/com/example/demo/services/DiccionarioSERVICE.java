package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Diccionario;
import com.example.demo.dao.DiccionarioRepository;
import com.example.demo.dto.DiccionarioDTO;
import com.example.demo.interfaces.IDiccionarioSERVICE;
@Service
public class DiccionarioSERVICE implements IDiccionarioSERVICE {
	@Autowired
	DiccionarioRepository diccionarioRepository;

	@Override
	public List<String> listarEspanol(String ingles) {
		List<String> listaEspanol = new ArrayList<String>();
		Iterable<Diccionario> diccionarios = diccionarioRepository.findByIngles(ingles);
		for (Diccionario daux : diccionarios) {
			//DiccionarioDTO ddto = new DiccionarioDTO(daux.getId(),daux.getEspanol(), daux.getIngles(),daux.getTipo(), daux.getCategoria(),daux.getNumUso());
			listaEspanol.add(daux.getEspanol());
		}
		return listaEspanol;
	}

	@Override
	public List<String> listarIngles(String espanol) {
		List<String> listaIngles = new ArrayList<String>();
		Iterable<Diccionario> diccionarios = diccionarioRepository.findByEspanol(espanol);
		for (Diccionario daux : diccionarios) {
			//DiccionarioDTO ddto = new DiccionarioDTO(daux.getId(),daux.getEspanol(), daux.getIngles(),daux.getTipo(), daux.getCategoria(),daux.getNumUso());
			listaIngles.add(daux.getIngles());
		}
		return listaIngles;
	}

	@Override
	public List<DiccionarioDTO> listarDiccionarios() {
		List<DiccionarioDTO> listaDiccionarios = new ArrayList<DiccionarioDTO>();
		Iterable<Diccionario> diccionarios = diccionarioRepository.findAll();
		for (Diccionario daux : diccionarios) {
			DiccionarioDTO ddto = new DiccionarioDTO(daux.getId(),daux.getEspanol(), daux.getIngles(),daux.getTipo(), daux.getCategoria(),daux.getNum_uso());
			listaDiccionarios.add(ddto);
		}
		return listaDiccionarios;
	}
	

	@Override
	public List<DiccionarioDTO> listarByTipo(String tipo) {
		List<DiccionarioDTO> listaDiccionarios = new ArrayList<DiccionarioDTO>();
		Iterable<Diccionario> diccionarios = diccionarioRepository.findByTipo(tipo);
		for (Diccionario daux : diccionarios) {
			DiccionarioDTO ddto = new DiccionarioDTO(daux.getId(),daux.getEspanol(), daux.getIngles(),daux.getTipo(), daux.getCategoria(),daux.getNum_uso());
			listaDiccionarios.add(ddto);
		}
		return listaDiccionarios;
	}

	

	@Override
	public void borrarDiccionario(int id) {
		diccionarioRepository.deleteById(id);
	}

	@Override
	public DiccionarioDTO buscarDiccionario(int id) {
		Optional<Diccionario> dic = diccionarioRepository.findById(id);
		if(dic.isPresent()) {
			Diccionario daux = dic.get();
			DiccionarioDTO diccionario = new DiccionarioDTO(daux.getId(),daux.getEspanol(), daux.getIngles(),daux.getTipo(), daux.getCategoria(),daux.getNum_uso());
			return diccionario;
		}
		return null;
	}

	@Override
	public String encontarparejaespanolingles(String espanol, String ingles) {
		// TODO Auto-generated method stub
		String valorid=diccionarioRepository.findByEspanolIngles(espanol, ingles);
		System.out.println(" acceso OPTION devuelve: " + valorid);
		if (valorid==null) {
			valorid="-1"; 
		} 
		
		return valorid;
	}

	@Override
	public void altaDiccionario(List<Diccionario> listadiccionario) {
		int cont=maximoid()+1;
		for (Diccionario dic : listadiccionario) {
			dic.setId(cont++);
			System.out.println("SERVICIO ALTA DICCIONARIO: " + dic);
			diccionarioRepository.save(dic);
		}
	}

	@Override
	public int maximoid() {
		// TODO Auto-generated method stub
		return diccionarioRepository.findMAXId();
	}

	@Override
	public void altaDiccionario(Diccionario diccionario) {
		diccionarioRepository.save(diccionario);
		
	}
	
	@Override
	public List<DiccionarioDTO> buscarTexto(String textobuscar) {
		List<Diccionario> dics = diccionarioRepository.findtextobucado(textobuscar);
		List<DiccionarioDTO> diccionarioDTO = new ArrayList<DiccionarioDTO>();
		for (Diccionario daux : dics) {
			DiccionarioDTO ddto = new DiccionarioDTO(daux.getId(),daux.getEspanol(), daux.getIngles(),daux.getTipo(), daux.getCategoria(),daux.getNum_uso());
			diccionarioDTO.add(ddto);
		}
		return diccionarioDTO;
	}


}
