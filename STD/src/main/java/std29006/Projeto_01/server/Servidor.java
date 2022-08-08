package std29006.Projeto_01.server;
import std29006.Projeto_01.GerenciadorDistribuido;

import java.io.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

/**
 * Classe responsável por criar uma instância do objeto Gerenciador e registrá-la
 * em um serviço de registro de objetos distribuídos
 */
public class Servidor {
    // Constantes que indicam onde está sendo executado o serviço de registro,
// qual porta e qual o nome do objeto distribuído
    private static String nomeServidor = null;
    private static int porta = 12345;
    private static final String NOMEOBJDIST = "MeuGerenciador";
    public static void main(String args[]){
        try {
            // recebendo nome do servidor por argumento de linha de comando
           if (args[0] != null){
                nomeServidor = args[0];
            }
            // Criando
            Gerenciador c = new Gerenciador();
            // Definindo o hostname do servidor
            System.setProperty("java.rmi.server.hostname", nomeServidor);
            GerenciadorDistribuido stub = (GerenciadorDistribuido)
                    UnicastRemoteObject.exportObject(c, 0);
            // Criando serviço de registro
            Registry registro = LocateRegistry.createRegistry(porta);
            // Registrando objeto distribuído
            registro.bind(NOMEOBJDIST, stub);
            System.out.println("Servidor pronto!\n");


            while (stub.consultaListaClientes().size() == 0){
                sleep(1000);
            }
           if (stub.consultaListaClientes().size() != 0){
               Scanner teclado = new Scanner(System.in);
                int op = 9;
                do {
                    System.out.println("------------------------------------");
                    System.out.println("Digite 1, Para consulta de clientes");
                    System.out.println("Digite 2, Para consultar processos");
                    System.out.println("Digite 0, para encerrar consulta");
                    System.out.println("------------------------------------");
                    op = teclado.nextInt();
                    switch (op) {
                        case 1:
                            System.out.println("\n Lista de clientes: "+stub.consultaListaClientes());
                            break;

                        case 2:
                            System.out.println("\nEscolha o ip que deseja acessar: ");
                            System.out.println(stub.consultaListaClientes());
                            Scanner scannerIP = new Scanner(System.in);
                            System.out.println("digite o IP desejado: ");
                            String ip = scannerIP.next();
                            int pos = stub.obtemPosicaoCliente(ip);
                            Scanner scanner = new Scanner(System.in);
                            System.out.println("\n"+ "Digite o comando desejado: ");
                            String cmd =  scanner.next();
                            System.out.println(stub.consultaProcesso(cmd, pos));
                            break;
                        case 0:
                            System.out.println("Pressione CTRL + C para encerrar...");
                            break;
                    }

                }while (op !=0);
            }
            //System.out.println("Conectou um cliente: " +stub.obtemIpCliente());
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } //catch (ServerNotActiveException e) {
            //e.printStackTrace();
        //}
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}