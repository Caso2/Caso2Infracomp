package casoDosTodo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProcesoUno {
	private int filas;
	private int cols;
	private int bytesInt;
	private int sizePag;
	private int numMarcos;
	public ProcesoUno(int filas, int cols, int bytesInt, int sizePag, int numMarcos) {
		this.filas = filas;
		this.cols = cols;
		this.bytesInt = bytesInt;
		this.sizePag = sizePag;
		this.numMarcos = numMarcos;
	}
	
	public String hacerTodo() throws IOException {
		String direccion = "C:\\a_semestre_v\\INFRACOMP\\basura\\InfraComp\\InfraComp\\caso2_jbarrios_sepenuela\\casoDos\\docs/procesoUno.txt";
		File archivo = new File(direccion);
		FileWriter escritor = new FileWriter(archivo);
		escritor.write("TP="+sizePag +"\n");
		escritor.write("NF="+filas+"\n");
		escritor.write("NC="+cols+"\n");
		int numRefs = filas*cols*3;
		escritor.write("NR="+numRefs+"\n");
		int pagina =0;
		int desplazamiento =0;
		int paginaBb = ((filas*cols*bytesInt)/sizePag);
		int desplazamientoBb = ((filas*cols*bytesInt)%sizePag);
		int paginaCc = ((filas*cols*bytesInt*2)/sizePag);
		int desplazamientoCc = ((filas*cols*bytesInt*2)%sizePag);
		for(int i =0; i<filas;i++)
		{
			for (int j=0; j<cols; j++)
			{
				
				String renglonAa = "[A-"+i+"-"+j+"],"+pagina+", "+desplazamiento+"\n";
				pagina+= ((desplazamiento+bytesInt)/sizePag);
				desplazamiento = ((desplazamiento+bytesInt)%sizePag);
				escritor.write(renglonAa);
				String renglonBb = "[B-"+i+"-"+j+"],"+paginaBb+", "+desplazamientoBb+"\n";
				paginaBb+= ((desplazamientoBb+bytesInt)/sizePag);
				desplazamientoBb = ((desplazamientoBb+bytesInt)%sizePag);
				escritor.write(renglonBb);
				String renglonCc = "[C-"+i+"-"+j+"],"+paginaCc+", "+desplazamientoCc+"\n";
				paginaCc+= ((desplazamientoCc+bytesInt)/sizePag);
				desplazamientoCc = ((desplazamientoCc+bytesInt)%sizePag);
				escritor.write(renglonCc);
			}
		}
			
		
		
		
		escritor.close();
		
		return direccion;


	}

}
