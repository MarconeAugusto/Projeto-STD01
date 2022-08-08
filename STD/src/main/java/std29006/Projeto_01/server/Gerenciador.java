package std29006.Projeto_01.server;
import std29006.Projeto_01.GerenciadorDistribuido;
import std29006.Projeto_01.ListaServicosClientes;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe que implementa a interface do objeto distribuído
 */
public class Gerenciador implements GerenciadorDistribuido {

    List<String> clientes = new ArrayList<>();
    List<ListaServicosClientes> listeners = new ArrayList<>();

    @Override
    public void addListener(ListaServicosClientes listener) throws RemoteException {
        listeners.add(listener);
    }

    @Override
    public  String obtemIpCliente() throws RemoteException, ServerNotActiveException {
        String cliente = RemoteServer.getClientHost();
        if (clientes.size() == 0) {
            clientes.add(cliente);
            return cliente;
        }
        else{
            boolean existe = true;
            for (int i = 0; i < clientes.size(); i++) {
                if (cliente.equals(clientes.get(i))) {
                  existe = true;
                  break;
                } else existe = false;
            }
            if (existe == false){
                clientes.add(cliente);
            }else System.out.println("Cliente já cadastrado");
        }
        return cliente;
    }
    @Override
    public List<String> consultaListaClientes() throws RemoteException{
        return clientes;
    }

    @Override
    public int obtemPosicaoCliente(String ip) throws RemoteException {
        int j = 0;
        if (clientes.size() == 0) {
            System.out.println("A lista está vazia");
            return -1;
        }
        else{
            boolean existe = true;
            for (int i = 0; i < clientes.size(); i++) {
                if (ip.equals(clientes.get(i))) {
                    j = i;
                    existe = true;
                    System.out.println("posição : " +i);
                    break;
                } else existe = false;
            }
            if (existe == false){
                System.out.println("Endereço IP não encontrado");
            }
        }
        return j;
    }

    @Override
    public String consultaProcesso(String cmd, int pos) throws RemoteException{
        String resposta = "";
        ListaServicosClientes listener = listeners.get(pos);
            try {
                resposta = listener.consultaProcesso(cmd);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        //}
        return resposta;
    }

}

