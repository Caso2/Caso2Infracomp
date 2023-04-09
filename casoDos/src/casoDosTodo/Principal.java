package casoDosTodo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Principal {
	private static int filas;
	private static int cols;
	private static int bytesInt;
	private static int sizePag;
	private static int numMarcos;
	
	public static void main(String[] args) throws IOException {

		Principal instancia = new Principal();
		File archivo = new File("C:/a_semestre_v/INFRACOMP/basura/InfraComp/InfraComp/caso2_jbarrios_sepenuela/casoDos/docs/inicial.txt");
		
		
		try (FileReader lector = new FileReader(archivo); BufferedReader br = new BufferedReader(lector);) {
			
			String filasStr = br.readLine();
			filas = Integer.parseInt(filasStr);
			
			String colsStr = br.readLine();
			cols = Integer.parseInt(colsStr);
			
			String bytesIntStr = br.readLine();
			bytesInt = Integer.parseInt(bytesIntStr);
			
			String sizePagStr = br.readLine();
			sizePag = Integer.parseInt(sizePagStr);
			
			String NumMarcosStr = br.readLine();
			numMarcos = Integer.parseInt(NumMarcosStr);


		}
		ProcesoUno procesoUno = new ProcesoUno(filas, cols, bytesInt, sizePag, numMarcos);
		String nomArchivo = procesoUno.hacerTodo();
		ProcesoDos procesoDos = new ProcesoDos (numMarcos, nomArchivo);
		procesoDos.hacerTodo();
		
	}
	
	

}
