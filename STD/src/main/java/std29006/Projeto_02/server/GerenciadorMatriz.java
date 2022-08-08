package std29006.Projeto_02.server;

import std29006.Projeto_02.GerenciadorDistribuido;
import std29006.Projeto_02.ListaServicosClientes;

import java.rmi.RemoteException;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que implementa a interface do objeto distribuído
 */
public class GerenciadorMatriz implements GerenciadorDistribuido {

    List<String> clientes = new ArrayList<>();
    List<ListaServicosClientes> listeners = new ArrayList<>();

    @Override
    public void addListener(ListaServicosClientes listener) throws RemoteException {
        listeners.add(listener);
    }

    /**
     * Função que obtem o endereço IP do cliente conectado, para uso na IDE comentar as partes indicadas e descomentar as partes comentadas
     * @return retorna o endereço IP do cliente
     * @throws RemoteException
     * @throws ServerNotActiveException
     */
    @Override
    public String obtemIpCliente() throws RemoteException, ServerNotActiveException {
        String cliente = RemoteServer.getClientHost();
        //Random random = new Random();
        //String auxiliar = String.valueOf(random.nextInt(1000));
        if (clientes.size() == 0) {
            clientes.add(cliente);
            //clientes.add(auxiliar);
            return cliente;       //COMENTAR
            //return auxiliar;
        } else {
            boolean existe = true;
            for (int i = 0; i < clientes.size(); i++) {
                if (cliente.equals(clientes.get(i))) {
                    existe = true;
                    break;
                } else existe = false;
            }
            if (existe == false) {
                clientes.add(cliente);         //COMENTAR
                //clientes.add(auxiliar);
            } else System.out.println("Cliente já cadastrado");
        }
        //return auxiliar;
        return cliente;             //COMENTAR
    }


    @Override
    public List<String> consultaListaClientes() throws RemoteException {
        return clientes;
    }


    @Override
    public int[][] multiplicaMatrizes(int[][] m1, int[][] m2, int pos) throws RemoteException {
        int[][] resposta = new int[m1.length][m2[0].length];
        ListaServicosClientes listener = listeners.get(pos);
        try {
            resposta = listener.multiplicaMatrizes(m1, m2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return resposta;

    }

    @Override
    public int[][] somaMatrizes(int[][] m1, int[][] m2, int pos) throws RemoteException {
        int[][] resposta = new int[m1.length][m2[0].length];
        ListaServicosClientes listener = listeners.get(pos);
        try {
            resposta = listener.somaMatrizes(m1, m2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return resposta;
    }

    @Override
    public int[][] matrizResultante(int[][] m1, int[][] m2, int pos) throws RemoteException {
        int[][] resposta = new int[m1.length+m1.length][m2[0].length];
        ListaServicosClientes listener = listeners.get(pos);
        try {
            resposta = listener.matrizResultante(m1, m2);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return resposta;
    }

    @Override
    public void imprimeMatriz(int[][] m) throws RemoteException {
        int i, j;
        for (i = 0; i < m.length; i++) {
            for (j = 0; j < m[0].length; j++) {
                System.out.printf(String.valueOf(m[i][j]) + " ");
            }
            System.out.printf("\n");
        }
    }


}

