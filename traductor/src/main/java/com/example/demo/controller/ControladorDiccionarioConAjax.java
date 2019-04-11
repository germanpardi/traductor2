package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.beans.Diccionario;
import com.example.demo.dto.DiccionarioDTO;
import com.example.demo.interfaces.IDiccionarioSERVICE;
import com.example.demo.interfaces.IUsuarioSERVICE;
import com.example.demo.util.ValidadorAportar;

@Controller
@RequestMapping("/")
public class ControladorDiccionarioConAjax {
	
	String diccionario=""; //Inicializo el diccionario como no seleccionado
	String salida="";//Inicializo vacia la salida, para ir "traduciendo" palabra por palabra
	
	Hashtable<String, List<String>> espanolInglesPalabra = new Hashtable<String, List<String>>();
	Hashtable<String, List<String>> espanolInglesFrase = new Hashtable<String, List<String>>();
	Hashtable<String, List<String>> inglesEspanolPalabra = new Hashtable<String, List<String>>();
	Hashtable<String, List<String>> inglesEspanolFrase = new Hashtable<String, List<String>>();	
	
	@Autowired
	IDiccionarioSERVICE diccionarioService;
	
	@RequestMapping("/traducir/{textoBuscar}")
	public @ResponseBody String traducir(@RequestParam("textotraduccir") String texto) {
		
		//Establezco el texto recibido en minusculas
		texto=texto.toLowerCase();
		//Reinicio el diccionario
		diccionario="";
		salida=texto;
		//Carga de diccionarios
		if(espanolInglesPalabra.size()<1||inglesEspanolPalabra.size()<1||texto.length()<2) {
			cargarDiccionarios();
		}
		//Comprobar que diccioario es la salida
		//Cargo en salida el texto introduccido
		//salida=texto;
		//Separo por frases
		if(diccionario.equals("")&&salida.length()>2) {//si el diccionario, no esta asignado 
			String [] arraySepararFrases = salida.split(",|;|\\.");
			System.out.println("traduzco frase");
			cerrar: for(int i=0;i<arraySepararFrases.length;i++) {
				
					//Quito posibles espacios dobles dentro del String
					ValidadorAportar vt = new ValidadorAportar();
					arraySepararFrases[i] = vt.quitarEspacios(arraySepararFrases[i]);
					//Separo por espacios la frase
					String [] frase = arraySepararFrases[i].split(" ");
					for(int j=0;j<frase.length;j++) {
						if(frase[i].length()>1) {	
							if(this.comprobarPalabraEspañol(frase[i])) {
								//Si me devuelve true, asigno el diccionario
								diccionario="espanol";
								break cerrar;
							}
							
							if(this.comprobarPalabraIngles(frase[i])){
								diccionario="ingles";
								break cerrar;
							}
						}
					
					}
			}
		}
		//Comprobar por frase
		//Cargo las frases y reseto la salida
		salida="";
		salida=texto;
		//Para que compruebe la ultima palabra de la frase bien
		salida = salida + " q";
			//Frases Espanol
			//Compruebo frase, si: estoy buscando, en español, (debe estar en español ya que lo compruebo cuando existe una unica palabra) y estoy buscando. 
			//pd: Una vez dentro me encontraria todas las frases que coincidan dentro del array del String
			if(diccionario.equals("espanol")) {
				System.out.println("traduzco");
				//Separo cada frase dentro del String
				String [] frases = salida.split(",|;|\\.");
				//System.out.println(frases.length);
				for(int i=0;i<frases.length;i++) {
					ValidadorAportar vt = new ValidadorAportar();
					frases[i] = vt.quitarEspacios(frases[i]);
					//Ahora realizo un Split por " " para devolver cada palabra de la frase
					String [] palabras = frases[i].split(" ");
					//Establezco dos marcadores, uno de inicio y otro de fin
					int inicio=0;//Primera palabra dentro del String
					int fin=palabras.length-1;//Ultima palabra dentro del String
					int retorno=1;//Conforme mas iteraciones quiero que el retorno de fin, sea mayor, me creo un contador fuera.
					while(true) {
						
						//Pongo la condicion de salida
						if((fin-inicio)<2&&fin==palabras.length-1) {break;}
						String cadenaTexto="";
						for(int k=inicio;k<=fin;k++) {
							cadenaTexto= cadenaTexto + " " + palabras[k];
						}
						
						if(this.comprobarFraseEspañol(cadenaTexto)) {
							//Si encuentra la frase, la traduccira, y quiero que cierre esta frase
							break;
						}else {
							//Si no ha pasado, quiero que si fin no esta al final, se mueva hacia la derecha, si esta al final que acorte 1 la diferencia y empiece desde el inicio
							if(fin<palabras.length-1) {
								inicio ++;
								fin++;
							}else {
								//Restablezco el inicio
								inicio=0;
								//El fin de la comparacion sera un String mas pequeño que el anterior.
								fin = (palabras.length-1)-retorno;
								//El retorno se hace mas grande
								retorno++;								
							}
						}
					}
				
				}
				
			}
			//Frase ingles
			if(diccionario.equals("ingles")) {
				System.out.println("traduzco");
				//Separo cada frase dentro del String(primero con espacios hola, nombre) y despues sin espacios hola,nombre
				String [] frases = salida.split(",|;|\\.");
				for(int i=0;i<frases.length;i++) {
					
					String [] palabras = frases[i].split(" ");
					
					int inicio=0;
					int fin=palabras.length-1;
					int retorno=1;
					while(true) {
						
						if((fin-inicio)<2&&fin==palabras.length-1) {break;}
						String cadenaTexto="";
						for(int k=inicio;k<=fin;k++) {
							cadenaTexto= cadenaTexto + " " + palabras[k];
						}
						
						//this.comprobarFraseIngles(request, cadenaTexto);
						if(this.comprobarFraseIngles(cadenaTexto)) {
							//Si encuentra la frase, la traduccira, y quiero que cierre esta frase
							break;
						}else {
							//Si no ha pasado, quiero que si fin no esta al final, se mueva hacia la derecha, si esta al final que acorte 1 la diferencia y empiece desde el inicio
							if(fin<palabras.length-1) {
								
								inicio ++;
								fin++;
							}else {

								
								//Restablezco el inicio
								inicio=0;
								//El fin de la comparacion sera un String mas pequeño que el anterior.
								fin = (palabras.length-1)-retorno;
								//El retorno se hace mas grande
								retorno++;								
							}
						}
					}
				
				}
				
			}		
		//Comprobar por palabra
			//Palabra en español
			String [] palabras = salida.split(" |,|;|\\.");
			
			
			for (int i=0;i<palabras.length;i++) {
				//Si el array es 1, empieza a selecionar el diccionario, una vez establecido, no se cambia hasta la siguiente ejecucion del boton(que pulse traduccir)
				if(diccionario.equals("espanol")) {
					//Seleecionar diccionario
					if(this.comprobarPalabraEspañol(palabras[i])) {
						//El metodo traduce la palabra
						
					}
					
				}
			}
			//Palabra en ingles
			for (int i=0;i<palabras.length;i++) {
				//Si el array es 1, empieza a selecionar el diccionario, una vez establecido, no se cambia hasta la siguiente ejecucion del boton(que pulse traduccir)
				if(diccionario.equals("ingles")) {
					//Seleecionar diccionario
					if(this.comprobarPalabraIngles(palabras[i])) {
						
					}
				}
				
			}
		
		//Devolver por ajax el resultado
		//Quito la ultima letra que la paso para que me lea la frase al final
		salida = salida.substring(0, salida.length()-1);	
		
		
		
		diccionario="";
		
		return salida;
	}
	
	
	@RequestMapping("/traduccionesfrecuentes/{ultima}")
	public @ResponseBody List<String> frecuentes(@RequestParam("ultima") String ultima) {
		List<String> palabrasFrecuentes=new ArrayList<String>();
		if(!diccionario.equals("")) {//Si el diccionario esta asignado
			
			if(diccionario.equals("espanol")) {
				System.out.println("|"+ultima+"|");
				palabrasFrecuentes = diccionarioService.listarIngles(ultima);
//				System.out.println(palabrasFrecuentes.size());
			}else if(diccionario.equals("ingles")){
				
				palabrasFrecuentes = diccionarioService.listarEspanol(ultima);
			}
		}
	
//		for (String string : palabrasFrecuentes) {
//			System.out.println(string);
//		}
		
		return palabrasFrecuentes;
	}


	public boolean comprobarFraseEspañol(String texto) {
		while(texto.charAt(0)==' ') {//Si el primer/segundo/tercero caracter es un espacio, que lo quite
			texto = texto.substring(1);
		}
		List<String> listaFrasesEspanolIngles=null;
		if(espanolInglesFrase.containsKey(texto)) {
			listaFrasesEspanolIngles=espanolInglesFrase.get(texto);
			//Si entra no es nulo, por lo tanto rescata valor
			this.cambiar(texto, listaFrasesEspanolIngles.get(0));
			return true;
		}
		
		
		return false;
		
	}
	
	
	public boolean comprobarFraseIngles(String texto) {
		while(texto.charAt(0)==' ') {//Si el primer/segundo/tercero caracter es un espacio, que lo quite
			texto = texto.substring(1);
		}
		List<String> listaFrasesInglesEspanol=null;
		if(inglesEspanolFrase.containsKey(texto)) {
			listaFrasesInglesEspanol=inglesEspanolFrase.get(texto);
			//Si entra no es nulo, por lo tanto rescata valor
			this.cambiar(texto, listaFrasesInglesEspanol.get(0));
			return true;
		}
		return false;
	}
	
	public boolean comprobarPalabraEspañol(String texto) {
		List<String> listaPalabrasEspanolIngles=null;
		
		if(espanolInglesPalabra.containsKey(texto)) {
			listaPalabrasEspanolIngles=espanolInglesPalabra.get(texto);
			//Si entra no es nulo, por lo tanto rescata valor
			this.cambiar(texto, listaPalabrasEspanolIngles.get(0));
			return true;
		}
		return false;
		
		
	}
	
	public boolean comprobarPalabraIngles(String texto) {
		List<String> listaPalabrasInglesEspanol=null;
		if(inglesEspanolPalabra.containsKey(texto)) {
			listaPalabrasInglesEspanol=inglesEspanolPalabra.get(texto);
			//Si entra no es nulo, por lo tanto rescata valor
			this.cambiar(texto, listaPalabrasInglesEspanol.get(0));
			return true;
		}
		
		return false;
		
		
	}
	
	
	//Metodo que me cambia las coincidencias, una vez encontradas, dentro del String salida(tanto para frases como para palabras) por las pasadas por parametro
	public void cambiar(String textoSinTraduccir, String textoTraduccido) {
		//Pongo busco el patron dentro del String y lo sustituyo por el traducido(si no encuentro no entro aqui y no lo cambia)
		//No tengo que tener en cuenta los signos de puntuacion
	    salida = salida.replaceAll(textoSinTraduccir, textoTraduccido);
	}
	
	//Carga de diccionarios, sino estan cargados.
	private void cargarDiccionarios() {
		List<DiccionarioDTO> listaPalabras = diccionarioService.listarByTipo("p");

		List<DiccionarioDTO> listaFrases = diccionarioService.listarByTipo("f");
		
		
		for (DiccionarioDTO diccionarioDTO : listaFrases) {
			espanolInglesFrase.put(diccionarioDTO.getEspanol().toLowerCase(), diccionarioService.listarIngles(diccionarioDTO.getEspanol().toLowerCase()));
			inglesEspanolFrase.put(diccionarioDTO.getIngles().toLowerCase(), diccionarioService.listarEspanol(diccionarioDTO.getIngles().toLowerCase()));	
		}
		
		
		for (DiccionarioDTO diccionarioDTO : listaPalabras) {
			espanolInglesPalabra.put(diccionarioDTO.getEspanol().toLowerCase(), diccionarioService.listarIngles(diccionarioDTO.getEspanol().toLowerCase()));
			inglesEspanolPalabra.put(diccionarioDTO.getIngles().toLowerCase(), diccionarioService.listarEspanol(diccionarioDTO.getIngles().toLowerCase()));	
		}		
	}	
}
