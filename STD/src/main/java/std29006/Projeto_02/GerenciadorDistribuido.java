package std29006.Projeto_02;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;

/**
 * Interface que deve ser compartilhada por servidor e clientes
 */
public interface GerenciadorDistribuido extends Remote {
    public void addListener(ListaServicosClientes listener) throws RemoteException;

    public String obtemIpCliente() throws RemoteException, ServerNotActiveException;

    public List<String> consultaListaClientes() throws RemoteException;

    public int[][] multiplicaMatrizes(int[][] m1, int[][] m2, int pos) throws RemoteException;

    public int[][] somaMatrizes(int[][] m1, int[][] m2, int pos) throws RemoteException;

    public int[][] matrizResultante(int[][] m1, int[][] m2, int pos) throws RemoteException;

    public void imprimeMatriz(int[][] m) throws RemoteException;
}