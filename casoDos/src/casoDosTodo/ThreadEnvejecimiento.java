package casoDosTodo;

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
			
		}
		
	}
	private void actualizarLru()
	{
		int[] pags = procesoDos.getPagsOperacion();
		if(pags[0]==-1)
		{
			
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
