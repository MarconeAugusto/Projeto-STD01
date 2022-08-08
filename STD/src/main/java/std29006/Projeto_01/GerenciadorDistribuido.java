package std29006.Projeto_01;

import java.rmi.Remote;
        import java.rmi.RemoteException;
        import java.rmi.server.ServerNotActiveException;
        import java.util.List;

/**
 * Interface que deve ser compartilhada por servidor e clientes
 */
public interface GerenciadorDistribuido extends Remote{
    public void addListener(ListaServicosClientes listener) throws RemoteException;
    public  String obtemIpCliente() throws RemoteException, ServerNotActiveException;
    public List<String> consultaListaClientes() throws RemoteException;
    public int obtemPosicaoCliente(String ip) throws RemoteException;
    public String consultaProcesso(String cmd, int pos) throws RemoteException;
}