package std29006.Projeto_02;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ListaServicosClientes extends Remote {

    public String consultaProcesso(String cmd) throws RemoteException;

    public int[][] multiplicaMatrizes(int[][] m1, int[][] m2) throws RemoteException;
    public int[][] somaMatrizes(int[][] m1, int[][] m2) throws RemoteException;
    public int[][] matrizResultante(int[][] m1, int[][] m2) throws RemoteException;
}