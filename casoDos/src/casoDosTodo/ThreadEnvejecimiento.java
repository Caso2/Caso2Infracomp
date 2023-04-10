package casoDosTodo;

import java.util.ArrayList;

public class ThreadEnvejecimiento extends Thread{
	
	private ProcesoDos procesoDos;
	
	public ThreadEnvejecimiento(ProcesoDos proceso) 
	{
		this.procesoDos = proceso;
	}
	public void run()
	{
		while(procesoDos.getEstaCorriendo())
		{
			actualizarRam();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	private void actualizarRam() {
		if(procesoDos.getPagNecesaria()==-1)
		{
			actualizarLru();
		}
		else
		{
			actualizarLru();
			ArrayList<Integer> mejores = new ArrayList<Integer>();
			int[][] lruPaBuscar = procesoDos.getlruInfo();
			for(int x=0;x<lruPaBuscar.length;x++)
			{
				mejores.add(x);
			}
			int centinela = 0;
			while(centinela<8 && mejores.size()>1)
			{
				for(int y=0; y<mejores.size(); y++)
				{
					if(lruPaBuscar[mejores.get(y)][centinela]==1)
					{
						if(mejores.size()>1)
						{
							mejores.remove(y);
						}
						
					}
				}
				centinela++;
			}
			int[] cambio = new int[] {mejores.get(0),mejores.get(0),mejores.get(0)};
			procesoDos.setPagsOperacion(cambio);
			procesoDos.setPagCambiable(mejores.get(0));
			
			
		}
		
	}
	private void actualizarLru()
	{
		int[] pags = procesoDos.getPagsOperacion();
		if(pags[0]==-1)
		{
			
		}
		else if(pags[0]==pags[1])
		{
			int[][] lruPaModificar = procesoDos.getlruInfo();
			for(int j=0; j<procesoDos.getnumMarcos();j++)
			{
				if(procesoDos.getIdPagina(j)==pags[0]) 
				{
					for(int k=6; k<=0;k--)
					{
						lruPaModificar[j][k+1]=lruPaModificar[j][k];
					}
					lruPaModificar[j][0]=1;
				}
			}
			procesoDos.updateLru(lruPaModificar);
		}
		else
		{
			int[][] lruPaModificar = procesoDos.getlruInfo();
			for(int i=0; i<3;i++)
			{
				for(int j=0; j<procesoDos.getnumMarcos();j++)
				{
					if(procesoDos.getIdPagina(j)==pags[i]) 
					{
						for(int k=6; k<=0;k--)
						{
							lruPaModificar[j][k+1]=lruPaModificar[j][k];
						}
						lruPaModificar[j][0]=1;
					}
				}
			}
			procesoDos.updateLru(lruPaModificar);
		}
		int[] reseteo = new int[] {-1,-1,-1};
		procesoDos.setPagsOperacion(reseteo);
	}

}
