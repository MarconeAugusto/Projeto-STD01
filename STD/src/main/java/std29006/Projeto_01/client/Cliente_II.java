package std29006.Projeto_01.client;
import std29006.Projeto_01.GerenciadorDistribuido;
import std29006.Projeto_01.ListaServicosClientes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cliente de uma aplicação Java RMI
 */
public class Cliente_II implements ListaServicosClientes {
    private static String nomeServidor = null;
    private static int porta = 12345;
    private static final String NOMEOBJDIST = "MeuGerenciador";
    public static void main(String args[]) {
        try {
            if (args[0] != null){
                nomeServidor = args[0];
            }

            ListaServicosClientes cliente =  new Cliente_II();
            ListaServicosClientes clienteDistribuido = (ListaServicosClientes) UnicastRemoteObject.exportObject(cliente,0);

            System.out.println("Conectando no servidor "+ nomeServidor);
            // Obtendo referência do serviço de registro
            Registry registro = LocateRegistry.getRegistry(nomeServidor, porta);
            // Procurando pelo objeto distribuído registrado previamente com o NOMEOBJDIST
            GerenciadorDistribuido stub = (GerenciadorDistribuido) registro.lookup(NOMEOBJDIST);

            stub.addListener(clienteDistribuido);

            // Invocando métodos do objeto distribuído
            String ip = stub.obtemIpCliente();
            System.out.println("IP cliente :" + ip);

        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(Cliente_II.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String consultaProcesso(String cmd) throws RemoteException{
        ProcessBuilder pb = new ProcessBuilder(cmd);
        String result = "";
        try
        {
            Process process = pb.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ( (line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            result = builder.toString();
        }
        catch (IOException e)
        { System.out.print("error");
            e.printStackTrace();
        }
        return result;
    }
}
