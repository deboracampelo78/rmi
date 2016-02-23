package br.univel.client;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import br.univel.common.ServicoRMI;

public class Client {

	public static void main(String[] args) {
		Registry registro;
		try {
			registro = LocateRegistry.getRegistry("127.0.0.1" ,
					1818); // coloquei i IP da m�quina local. aqui deve-se colocar o IP da m�quina que desejo conectar.
			ServicoRMI servico = (ServicoRMI) registro
					.lookup(ServicoRMI.NOME);
			String retorno = servico.greet("Ol�");
			System.out.println(retorno);

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
}