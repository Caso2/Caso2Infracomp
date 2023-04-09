package casoDosTodo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProcesoDos {
	private int filas;
	private int cols;
	private int bytesInt;
	private int sizePag;
	private int numMarcos;
	private String nombreArchivo;
	//MATRIZ DE DIRECCIONES VIRTUALES:
	private int virtualAdres[][][][];
	private ArrayList<ArrayList<String>> simulacionDisco = new  ArrayList<ArrayList<String>>(); 
	
	public ProcesoDos(int numMarcos, String nombreArchivo) {
		this.numMarcos = numMarcos;
		this.nombreArchivo = nombreArchivo;
	}

	public void hacerTodo() {
		File archivo = new File("C:/a_semestre_v/INFRACOMP/basura/InfraComp/InfraComp/caso2_jbarrios_sepenuela/casoDos/docs/inicial.txt");
		
		
		try (FileReader lector = new FileReader(archivo); BufferedReader br = new BufferedReader(lector);) {
			
			String tamPaginaStr = br.readLine();
			int valortamPagina;

			int indiceTamPagina = tamPaginaStr.indexOf("=");
			if (indiceTamPagina >= 0) { // Si se encontró el carácter "="
			    String valorTexto = tamPaginaStr.substring(indiceTamPagina + 1); // Se obtiene la cadena que sigue después del "="
			    valortamPagina = Integer.parseInt(valorTexto); // Se convierte la cadena a un valor entero
			} else { // Si no se encontró el carácter "="
			    valortamPagina = -1; // Se asigna un valor por defecto
			}
			
			
			String filasStr = br.readLine();
			int valorFilas;

			int indiceFilas = filasStr.indexOf("=");
			if (indiceFilas >= 0) { // Si se encontró el carácter "="
			    String valorTexto = filasStr.substring(indiceFilas + 1); // Se obtiene la cadena que sigue después del "="
			    valorFilas = Integer.parseInt(valorTexto); // Se convierte la cadena a un valor entero
			} else { // Si no se encontró el carácter "="
			    valorFilas = -1; // Se asigna un valor por defecto
			}
			
			String colsStr = br.readLine();
			int valorColumnas;

			int indiceCols = colsStr.indexOf("=");
			if (indiceCols >= 0) { // Si se encontró el carácter "="
			    String valorTexto = colsStr.substring(indiceCols + 1); // Se obtiene la cadena que sigue después del "="
			    valorColumnas = Integer.parseInt(valorTexto); // Se convierte la cadena a un valor entero
			} else { // Si no se encontró el carácter "="
			    valorColumnas = -1; // Se asigna un valor por defecto
			}
			
			String numRefsStr = br.readLine();
			int numRefsInt;
			int indicenumRefsInt = numRefsStr.indexOf("=");
			if (indicenumRefsInt >= 0) { // Si se encontró el carácter "="
			    String valorTexto = numRefsStr.substring(indicenumRefsInt + 1); // Se obtiene la cadena que sigue después del "="
			    numRefsInt = Integer.parseInt(valorTexto); // Se convierte la cadena a un valor entero
			} else { // Si no se encontró el carácter "="
				numRefsInt = -1; // Se asigna un valor por defecto
			}
			
			String renglon= br.readLine();
			//SE INICIALIZA LA MATRIZ DE DIRECCIONES VIRTUALES:
			/*Se tiene una matriz de cuatro dimensiones
			 * 1. fila
			 * 2. columna
			 * 3. matriz(A, B o C)
			 * 4. pagina(0) o desplazamiento(1)*/
			virtualAdres = new int[valorFilas][valorColumnas][3][2];
			
			while(!renglon.isBlank()) {
				//ARMADO DE LA TABLA DE PAGINAS INICIAL
				//"[A-0-0],0, 0" TENGO PRIMERO LA FILA LUEGO LA COLUMNA
				String matriz = Character.toString(renglon.charAt(1));
				int fila = Integer.parseInt(Character.toString(renglon.charAt(3)));
				int columna = Integer.parseInt(Character.toString(renglon.charAt(5)));
				int pagActual = Integer.parseInt(Character.toString(renglon.charAt(8)));
				int desplaActual = Integer.parseInt(Character.toString(renglon.charAt(11)));
				
				if(pagActual+1>simulacionDisco.size()) {
					ArrayList<String> simulacionPag = new ArrayList<String>();
					simulacionPag.add(renglon.substring(1, 6));
					simulacionDisco.add(simulacionPag);
					saveVirtualAdress(matriz, fila, columna, pagActual, desplaActual);
										
				} 
				else 
				{
					ArrayList<String> simulacionPag = simulacionDisco.get(pagActual);
					simulacionPag.add(renglon.substring(1, 6));
					simulacionDisco.remove(pagActual);
					simulacionDisco.add(pagActual, simulacionPag);
				}
				
				//ARMADO DE LA MATRIZ EN DISCO SIMULADA
				
			}
			
			
			


		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void saveVirtualAdress(String matriz, int fila, int columna, int pagActual, int desplaActual) {
		// TODO Auto-generated method stub
		if(matriz.equals("A"))
		{
			virtualAdres[fila][columna][0][0]=pagActual;
			virtualAdres[fila][columna][0][1]=desplaActual;
			
		}
		else if(matriz.equals("B")) 
		{
			virtualAdres[fila][columna][1][0]=pagActual;
			virtualAdres[fila][columna][1][1]=desplaActual;
		}
		else
		{
			virtualAdres[fila][columna][2][0]=pagActual;
			virtualAdres[fila][columna][2][1]=desplaActual;
		}
	}

}
