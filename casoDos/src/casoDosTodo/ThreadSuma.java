package casoDosTodo;

public class ThreadSuma extends Thread{
	
	private ProcesoDos procesoDos;
	
	public ThreadSuma(ProcesoDos proceso) 
	{
		this.procesoDos = proceso;
	}
	public void run()
	{
		for(int i=0; i<procesoDos.getvalorFilas(); i++)
		{
			for(int j=0; j<procesoDos.getvalorColumnas(); j++)
			{
				int[] pagsOperacion = new int[3];
				for(int k=0; k<3; k++) 
				{
					int paginaNeeded  = procesoDos.getvirtualAdres(i,j,k,0);
					pagsOperacion[k]= paginaNeeded;
					boolean estaEnRam = false;
					for(int l=0;l<procesoDos.getnumMarcos(); l++)
					{
						if(procesoDos.gettablaPaginas(i)==paginaNeeded)
						{
							estaEnRam = true;
						}
					}
					if(!estaEnRam)
					{
						procesoDos.addNumFallas();
						procesoDos.setpagNecesaria(paginaNeeded);
						while(procesoDos.getpagNecesaria()>=0)
						{
							try {
								this.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
				procesoDos.setPagsOperacion(pagsOperacion);
				
			}
		}
	}
}
