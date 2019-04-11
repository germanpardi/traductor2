package com.example.demo.controller;




import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.beans.Diccionario;
import com.example.demo.interfaces.IDiccionarioSERVICE;
import com.example.demo.util.ValidarTransformar;


@Controller				//--->> lo mas importante y lo que indica que es el controlador.
@RequestMapping("/")
public class ControladorAportar {
//Controlador general
	

	@Autowired
    private IDiccionarioSERVICE diccionarioservice;
		
	@RequestMapping("/aportar")	     //   http://localhost:8080/aportar
	public String aportar (HttpServletRequest request) {
		boolean seguir=false;
		int contadortrozostratadosEsp=0;
		
		System.out.println("ControladoAportar - aportar");
		
		
		//validaciones iniciales de formatos ----------------------
		String aportarEsp=request.getParameter("aportarEsp"); 
		String aportarIng=request.getParameter("aportarIng");
		
		String aportarEspini=aportarEsp;
		String aportarIngini=aportarIng;
		
		ValidarTransformar vtesp=new ValidarTransformar();
		aportarEsp=vtesp.validarFormatoAportacion(aportarEsp);  //nos ha hecho una primera evaluacion
		
		ValidarTransformar vting=new ValidarTransformar();
		
		if (vtesp.getErrores()>0) {
			request.setAttribute("mensaje", vtesp.getMensaje());
			seguir=false;
		}else {
			aportarIng=vting.validarFormatoAportacion(aportarIng);  //nos ha hecho una primera evaluacion
					
			if (vting.getErrores()>0) {
				request.setAttribute("mensaje", vting.getMensaje());
				seguir=false;
			}else {
				seguir=true;
			}
		}
		
		System.out.println("ControladoAportar - aportar - seguir= " + seguir);
		
		//procedemos a dar de alta ----------------------------------
		if(seguir) {  
						
			List<Diccionario> listadicccionario=new ArrayList<Diccionario>();
						
			
			Diccionario diccionario=new Diccionario();			
			
			
			// separamos puntos y comas de ESPAÑOL -----------------------------------------
			
			int contadortrozos=0;
			
			String[] cortes = aportarEsp.split("\\.|,|;");
			System.out.println("ESPAÑOL - cortes detectados: "+cortes.length);
			for (int num=0; num < cortes.length; num++) {
				diccionario=new Diccionario();
				diccionario.setId(0);
				diccionario.setCategoria("apo");
				diccionario.setNum_uso(1);				
				diccionario.setEspanol(cortes[num]);
				
				listadicccionario.add(diccionario);  
				contadortrozos++;
				System.out.println("hemos incorporado: "+cortes[num] + ", en la posicion del array listadicccionario: "+num+". valor diccionario: "+ diccionario);
			}
			
			
			for (Diccionario d : listadicccionario) {
				System.out.println("COMPROBACION ESPA - tenemos en listadiccionario: " + 	d);
			}
			
			
			contadortrozostratadosEsp= contadortrozos;
			
			// separamos puntos y comas de INGLES y hay que ponerlos en el mismo array que su respectivo ESPAÑOL ---------
			
			contadortrozos=0;
			String[] cortesING = aportarIng.split("\\.|,|;");
			System.out.println("INGLES - cortes detectados: "+cortesING.length);
			for (int num=0; num < cortesING.length; num++) {
				if (contadortrozos<=contadortrozostratadosEsp) { //vamos bien
					Diccionario dicvolcar=new Diccionario();
					dicvolcar=listadicccionario.get(contadortrozos);
					dicvolcar.setIngles(cortesING[num]);
					listadicccionario.set(contadortrozos, dicvolcar);
					System.out.println("Hemos modificado en "+contadortrozos+" del diccionario: " + dicvolcar);
					contadortrozos++;
			 	}
			}
			
			for (Diccionario d : listadicccionario) {
				System.out.println("COMPROBACION INGLES - tenemos en listadiccionario: " + 	d);
			}
			
			int aportaciones=0;
			List<Diccionario> listadicccionarioALTAS=new ArrayList<Diccionario>();
			
			
			if (contadortrozos!=contadortrozostratadosEsp) {
				request.setAttribute("mensaje", "Debe escribir los mismos signos en ambas partes"); 
			}else { //vamos a grabar
				int contador=0;
				//actualizmos el tipo, segun la longitud guardada de texto, y comprobamos si es ALTa o YA EXISTE.
				System.out.println("vamos a proceder a analizar para GRABAR -----------");
				 
				for (Diccionario dicionariorecorrer : listadicccionario) {
					System.out.println("tratamos listadicccionario array: " + 	contador +"con datos: " + diccionario);
					String tipoEsp=vtesp.averiguarTipo(dicionariorecorrer.getEspanol());
					String tipoIng=vting.averiguarTipo(dicionariorecorrer.getIngles());
					if (tipoEsp.equals("f")|tipoIng.equals("f")) {
						dicionariorecorrer.setTipo("f");
					}else{
						dicionariorecorrer.setTipo("p");
					}
					
					dicionariorecorrer.setEspanol(vtesp.eliminarespacioprimero(dicionariorecorrer.getEspanol()));
					dicionariorecorrer.setIngles(vting.eliminarespacioprimero(dicionariorecorrer.getIngles()));
					
					
					System.out.println("Tenemos preparado diccionario: " + dicionariorecorrer);
					String id=diccionarioservice.encontarparejaespanolingles(dicionariorecorrer.getEspanol(), dicionariorecorrer.getIngles());
					int numid=Integer.parseInt(id);
					System.out.println("la relacion se ha buscado e indice devuelto ha sido: " + numid+" .Diccionario= " + diccionario);
					if(numid<0) { //es que NO EXISTE, podemos darla de alta. Vienen el valor -1.
						listadicccionarioALTAS.add(dicionariorecorrer);
						aportaciones++;
						System.out.println("no existe, por lo que daremos de alta "+ dicionariorecorrer);
					}
					contador++;
					
				}
				
				for (Diccionario d : listadicccionarioALTAS) {
					System.out.println("COMPROBACION listadicccionarioALTAS - tenemos en listadicccionarioALTAS: " + 	d);
				}
				
				
				if (aportaciones==0) {
					request.setAttribute("mensaje", "La aportacion ya existe. "); 
					System.out.println("NO SE DA NADA DE ALTA"); 
				}else {
					request.setAttribute("mensaje", "Alta realizada. Gracias por su aportacion.");
					System.out.println("VAMOS A DAR DE ALTA listadicccionarioALTAS. Debe llevar filas:  " + aportaciones);
					diccionarioservice.altaDiccionario(listadicccionarioALTAS);
					aportarEspini="";
					aportarIngini="";
					
				}
			}
			
	
		
		}	    //   http://localhost:8080/file/cargamasiva
	
		request.setAttribute("aportarEsp", aportarEspini);
		request.setAttribute("aportarIng", aportarIngini);
		
		
		
		
		return "index";
		
	}	
	
}


