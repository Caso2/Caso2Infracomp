package casoDosTodo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcesoDos {
	private static int filas;
	private static int cols;
	private static int bytesInt;
	private static int sizePag;
	private static int numMarcos;
	private static String nombreArchivo;
	private static int numFallas;
	private static int pagNecesaria=-1;
	private static boolean estaCorriendo = true;
	private static int[] pagsOperacion = new int[] {-1, -1, -1};
	private static int[][] lruInfo;
	private static ArrayList<ArrayList<String>> simulacionRam = new  ArrayList<ArrayList<String>>();
	//MATRIZ DE DIRECCIONES VIRTUALES:
	private static int virtualAdres[][][][];
	private static ArrayList<ArrayList<String>> simulacionDisco = new  ArrayList<ArrayList<String>>(); 
	private static int[] tablaPaginas;
	private static int paginaCambiable = -1;
	
	public ProcesoDos(int numMarcos, String nombreArchivo) {
		this.numMarcos = numMarcos;
		this.nombreArchivo = nombreArchivo;
		this.simulacionRam = new  ArrayList<ArrayList<String>>(numMarcos);
		this.lruInfo = new int[numMarcos][8];
		
		
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

			int indiceFilas = filasStr.indexOf("=");
			if (indiceFilas >= 0) { // Si se encontró el carácter "="
			    String valorTexto = filasStr.substring(indiceFilas + 1); // Se obtiene la cadena que sigue después del "="
			    filas = Integer.parseInt(valorTexto); // Se convierte la cadena a un valor entero
			} else { // Si no se encontró el carácter "="
			    filas = -1; // Se asigna un valor por defecto
			}
			
			String colsStr = br.readLine();
			

			int indiceCols = colsStr.indexOf("=");
			if (indiceCols >= 0) { // Si se encontró el carácter "="
			    String valorTexto = colsStr.substring(indiceCols + 1); // Se obtiene la cadena que sigue después del "="
			    cols = Integer.parseInt(valorTexto); // Se convierte la cadena a un valor entero
			} else { // Si no se encontró el carácter "="
			    cols = -1; // Se asigna un valor por defecto
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
			virtualAdres = new int[filas][cols][3][2];
			
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
					saveVirtualAdres(matriz, fila, columna, pagActual, desplaActual);
										
				} 
				else 
				{
					ArrayList<String> simulacionPag = simulacionDisco.get(pagActual);
					simulacionPag.add(renglon.substring(1, 6));
					simulacionDisco.remove(pagActual);
					simulacionDisco.add(pagActual, simulacionPag);
					saveVirtualAdres(matriz, fila, columna, pagActual, desplaActual);
					
				}
								
			}
			tablaPaginas= new int[numMarcos+simulacionDisco.size()];
			for(int i=0;i<numMarcos; i++)
			{
				tablaPaginas[i]= -1;
			}
			for(int i=numMarcos;i<numMarcos+simulacionDisco.size(); i++)
			{
				tablaPaginas[i]= i-numMarcos;
			}
			//INICIA FASE 2: EL SUMADO DE LAS MATRICES
			//& LA UTILIZACION DE LOS THREADS
			ThreadEnvejecimiento t_enve = new ThreadEnvejecimiento(this);
			ThreadTablaPaginas t_pages = new ThreadTablaPaginas(this);
			t_enve.start();
			t_pages.start();
			ThreadSuma t_suma = new ThreadSuma(this);
			t_suma.start();
			
			


		}catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void saveVirtualAdres(String matriz, int fila, int columna, int pagActual, int desplaActual) {
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
	public boolean getEstaCorriendo()
	{
		return estaCorriendo;
	}
	public synchronized int getPagNecesaria() 
	{
		return pagNecesaria;
	}

	public int getvalorFilas() {
		// TODO Auto-generated method stub
		return filas;
	}

	public int getvalorColumnas() {
		// TODO Auto-generated method stub
		return cols;
	}

	public int getvirtualAdres(int i, int j, int k, int l) {
		return virtualAdres[i][j][k][l];
	}

	public int getnumMarcos() {
		// TODO Auto-generated method stub
		return numMarcos;
	}

	public synchronized int gettablaPaginas(int i) {
		// TODO Auto-generated method stub
		return tablaPaginas[i];
	}

	public synchronized void addNumFallas() {
		numFallas++;
		
	}

	public void setpagNecesaria(int paginaNeeded) {
		pagNecesaria = paginaNeeded;
		
	}

	

	public synchronized void setPagsOperacion(int[] pagsOperacion) {
		// TODO Auto-generated method stub
		this.pagsOperacion = pagsOperacion;
		
	}

	public int[] getPagsOperacion() {
		// TODO Auto-generated method stub
		return this.pagsOperacion;
	}
	public int[][] getlruInfo()
	{
		return lruInfo;
	}

	public synchronized int getIdPagina(int i) {
		// TODO Auto-generated method stub
		return tablaPaginas[i];
	}

	public void updateLru(int[][] lruPaModificar) {
		// TODO Auto-generated method stub
		this.lruInfo = lruPaModificar;
	}

	public void setPagCambiable(Integer integer) {
		// TODO Auto-generated method stub
		this.paginaCambiable = integer;
	}

	public int getPagCambiable() {
		// TODO Auto-generated method stub
		return this.paginaCambiable;
	}

	public void actualizarTabla() {
		// TODO Auto-generated method stub
		int paginaPaDisco = tablaPaginas[paginaCambiable];
		tablaPaginas[paginaCambiable]= pagNecesaria;
		
	}

	public void setEstaCorriendo(boolean b) {
		// TODO Auto-generated method stub
		estaCorriendo = b;
	}
}
