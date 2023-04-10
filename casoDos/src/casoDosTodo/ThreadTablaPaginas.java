package casoDosTodo;

public class ThreadTablaPaginas extends Thread{
	
	private ProcesoDos procesoDos;
	
	public ThreadTablaPaginas(ProcesoDos proceso) 
	{
		this.procesoDos = proceso;
	}
	public void run()
	{
		while(procesoDos.getEstaCorriendo())
		{
			actualizarTabla();
			actualizarMarcos();
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	private void actualizarMarcos() {
		// TODO Auto-generated method stub
		
	}
	private void actualizarTabla() {
		// TODO Auto-generated method stub
		if(procesoDos.getPagNecesaria()!=-1 && procesoDos.getPagCambiable()!=-1)
		{
			procesoDos.actualizarTabla();
		}
	}
}
