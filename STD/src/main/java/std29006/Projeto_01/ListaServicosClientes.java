package std29006.Projeto_01;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ListaServicosClientes extends Remote {

    public String consultaProcesso(String cmd) throws RemoteException;
}