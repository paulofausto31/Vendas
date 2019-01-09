package venda.util;

import android.os.Handler;


public abstract class GerenciadorComunicacao {
	
	private Thread _threadProcessa;
	private Thread _threadAtualiza;
	private Handler handler = new Handler();
	
	public GerenciadorComunicacao() throws InterruptedException{
		_threadAtualiza = new Thread(new Runnable() {			
			@Override
			public void run() {
					int nada = 0;
					//atualiza();
			}
		});
		_threadProcessa = new Thread(new Runnable() {			
			@Override
			public void run() {
				for(String item : getTabelas()){
					try {
						processa(item);
						handler.post(new Runnable() {
							
							@Override
							public void run() {
								try {
									atualiza();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
							}
						});
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});	
		_threadProcessa.start();
		_threadAtualiza.start();
		
		_threadProcessa.join();
		_threadAtualiza.join();

	}
	
	protected abstract String[] getTabelas();
	protected abstract void processa(String tabela) throws InterruptedException;
	protected abstract void atualiza() throws InterruptedException;
}
