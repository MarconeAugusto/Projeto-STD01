package std29006.Projeto_02.server;

import std29006.Projeto_02.GerenciadorDistribuido;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Random;
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
    private static int qtdRaspPI = 0;
    private static int nLinhasA = 0;
    private static int nColunasA = 0;
    private static int nLinhasB = 0;
    private static int nColunasB = 0;

    private static int[][] matrizA = new int[0][0]; // declaração combinada
    private static int[][] matrizB = new int[0][0]; // declaração combinada

    private static int[][] matrizA1 = new int[0][0];
    private static int[][] matrizA2 = new int[0][0];
    private static int[][] matrizA3 = new int[0][0];
    private static int[][] matrizA4 = new int[0][0];

    private static int[][] matrizB1 = new int[0][0];
    private static int[][] matrizB2 = new int[0][0];
    private static int[][] matrizB3 = new int[0][0];
    private static int[][] matrizB4 = new int[0][0];


    public static void main(String args[]) {
        try {
            // recebendo nome do servidor por argumento de linha de comando
            if (args[0] != null) {
                nomeServidor = args[0];
            }
            if (args[1] != null) {
                qtdRaspPI = Integer.parseInt(args[1]);
            }
            if (args[2] != null) {
                nLinhasA = Integer.parseInt(args[2]);
            }
            if (args[3] != null) {
                nColunasA = Integer.parseInt(args[3]);
            }
            if (args[4] != null) {
                nLinhasB = Integer.parseInt(args[4]);
            }
            if (args[5] != null) {
                nColunasB = Integer.parseInt(args[5]);
            }

            //testar se as matrizes são válidas
            if (nColunasA != nLinhasB) {
                System.out.println("Favor inserir uma matriz válida");
                return;
            }

            System.out.println("Servidor:" + nomeServidor + ", Quantidade de RaspberryPI: " + qtdRaspPI + "\n");
            System.out.println("Matriz A => Linhas: " + nLinhasA + ", Colunas: " + nColunasA + "\n");
            System.out.println("Matriz B => Linhas: " + nLinhasB + ", Colunas: " + nColunasB + "\n");

            // Criando
            GerenciadorMatriz c = new GerenciadorMatriz();
            // Definindo o hostname do servidor
            System.setProperty("java.rmi.server.hostname", nomeServidor);
            GerenciadorDistribuido stub = (GerenciadorDistribuido)
                    UnicastRemoteObject.exportObject(c, 0);
            // Criando serviço de registro
            Registry registro = LocateRegistry.createRegistry(porta);
            // Registrando objeto distribuído
            registro.bind(NOMEOBJDIST, stub);
            System.out.println("Servidor pronto!\n");
            System.out.println("\n");

            if (carregaMatrizes() == true) {
                divideMatrizA(matrizA);
                divideMatrizB(matrizB);
            }

            System.out.println("\nAguardando Conecções \n");
            while (stub.consultaListaClientes().size() == 0) {
                sleep(1000);
            }
            if (stub.consultaListaClientes().size() != qtdRaspPI) {
                Scanner teclado = new Scanner(System.in);
                int op;
                do {
                    System.out.println("----------------------------------------------------------");
                    System.out.println("Digite 1, Para consulta de clientes");
                    System.out.println("Digite 2, Para realizar a multiplicação das matrizes");
                    System.out.println("Digite 0, para encerrar consulta");
                    System.out.println("----------------------------------------------------------");
                    op = teclado.nextInt();
                    switch (op) {
                        case 1:
                            System.out.println("\n Lista de clientes: " + stub.consultaListaClientes());
                            break;

                        case 2:
                            if (qtdRaspPI == 4) {
                                Random gerador = new Random();

                                int[][] matrizR1; // declaração combinada
                                matrizR1 = stub.multiplicaMatrizes(matrizA1, matrizB1, 0);
                                //System.out.println("\nMultiplicação 1");
                                //stub.imprimeMatriz(matrizR1);
                                int[][] matrizR2; // declaração combinada
                                matrizR2 = stub.multiplicaMatrizes(matrizA2, matrizB2, 1);
                                //System.out.println("\nMultiplicação 2");
                                //stub.imprimeMatriz(matrizR2);
                                int[][] matrizR3; // declaração combinada
                                matrizR3 = stub.multiplicaMatrizes(matrizA3, matrizB1, 2);
                                //System.out.println("\nMultiplicação 3");
                                //stub.imprimeMatriz(matrizR3);
                                int[][] matrizR4; // declaração combinada
                                matrizR4 = stub.multiplicaMatrizes(matrizA4, matrizB2, 3);
                                //System.out.println("\nMultiplicação 4");
                                //stub.imprimeMatriz(matrizR4);
                                int cl = gerador.nextInt(4);
                                if (cl == 4) {
                                    cl = cl--;
                                }
                                matrizR1 = stub.somaMatrizes(matrizR1, matrizR2, cl);
                                System.out.println("\nMatriz resultante 1 + Matriz resltante 2, calculado no cliente: " + cl);
                                stub.imprimeMatriz(matrizR1);
                                cl = gerador.nextInt(4);
                                if (cl == 4) {
                                    cl = cl--;
                                }
                                matrizR2 = stub.somaMatrizes(matrizR3, matrizR4, cl);
                                System.out.println("\nMatriz resultante 3 + Matriz resltante 4, calculado no cliente: " + cl);
                                stub.imprimeMatriz(matrizR2);
                                cl = gerador.nextInt(4);
                                if (cl == 4) {
                                    cl = cl--;
                                }
                                matrizR1 = stub.matrizResultante(matrizR1, matrizR2, cl);
                                System.out.println("\nMatriz resultante, calculado no cliente: " + cl);
                                stub.imprimeMatriz(matrizR1);
                            } else
                                System.out.println("Quantidade de placas inválida");

                            break;
                        case 0:
                            System.out.println("Pressione CTRL + C para encerrar...");
                            break;
                    }

                } while (op != 0);
            }
        } catch (RemoteException | AlreadyBoundException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static boolean carregaMatrizes() {
        matrizA = new int[nLinhasA][nColunasA]; // declaração combinada
        matrizB = new int[nLinhasB][nColunasB]; // declaração combinada
        int n = 20; // limite para vaores ramdômicos

        //instância um objeto da classe Random usando o construtor básico
        Random gerador = new Random();
        System.out.printf("\n");
        System.out.printf("Preenchendo a matriz A com valores aleatórios\n");
        //System.out.printf("--------------------------------------------------------------------------------------------------------- ");
        //System.out.printf("\n");
        //preenchendo a matrizA com valores aleatórios
        int i, j;
        for (i = 0; i < nLinhasA; i++) {
            for (j = 0; j < nColunasA; j++) {
                matrizA[i][j] = gerador.nextInt(n);
            //    System.out.printf(String.valueOf(matrizA[i][j]) + " ");
            }
          //  System.out.printf("\n");
        }
        System.out.printf("\n");
        System.out.printf("Preenchendo a matriz B com valores aleatórios\n ");
        //System.out.printf("---------------------------------------------------------------------------------------------------------");
        //System.out.printf("\n");
        //preenchendo a matrizB com valores aleatórios
        i = 0;
        j = 0;
        for (i = 0; i < nLinhasB; i++) {
            for (j = 0; j < nColunasB; j++) {
                matrizB[i][j] = gerador.nextInt(n);
                //System.out.printf(String.valueOf(matrizB[i][j]) + " ");
            }
            //System.out.printf("\n");
        }
        //System.out.printf("\n");
        //System.out.printf("\n");
        return true;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void divideMatrizA(int[][] matrizA) {

        matrizA1 = new int[nLinhasA / 2][nColunasA / 2];
        matrizA2 = new int[nLinhasA / 2][nColunasA / 2];
        matrizA3 = new int[nLinhasA / 2][nColunasA / 2];
        matrizA4 = new int[nLinhasA / 2][nColunasA / 2];

        int i, j;
        for (i = 0; i < nLinhasA / 2; i++) {
            for (j = 0; j < nColunasA / 2; j++) {
                matrizA1[i][j] = matrizA[i][j];
            }
        }
        int k = 0;
        for (i = 0; i < nLinhasA / 2; i++) {
            for (j = nColunasA / 2; j < nColunasA; j++) {
                matrizA2[i][k] = matrizA[i][j];
                k++;
                if (k == nColunasA / 2) {
                    k = 0;
                }
            }
        }
        k = 0;
        for (i = nLinhasA / 2; i < nLinhasA; i++) {
            for (j = 0; j < nColunasA / 2; j++) {
                matrizA3[k][j] = matrizA[i][j];
            }
            k++;
            if (k == nLinhasA / 2) {
                k = 0;
            }
        }
        k = 0;
        int l = 0;
        for (i = nLinhasA / 2; i < nLinhasA; i++) {
            for (j = nColunasA / 2; j < nColunasA; j++) {
                matrizA4[k][l] = matrizA[i][j];
                l++;
                if (l == nColunasA / 2) {
                    l = 0;
                }
            }
            k++;
            if (k == nLinhasA / 2) {
                k = 0;
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*    public static void divideMatrizB(int matrizB[][]) {

        matrizB1 = new int[nLinhasB / 2][nColunasB / 2];
        matrizB2 = new int[nLinhasB / 2][nColunasB / 2];
        matrizB3 = new int[nLinhasB / 2][nColunasB / 2];
        matrizB4 = new int[nLinhasB / 2][nColunasB / 2];

        int i, j;
        for (i = 0; i < nLinhasB / 2; i++) {
            for (j = 0; j < nColunasB / 2; j++) {
                matrizB1[i][j] = matrizB[i][j];
            }
        }
        int k = 0;
        for (i = 0; i < nLinhasB / 2; i++) {
            for (j = nColunasB / 2; j < nColunasB; j++) {
                matrizB2[i][k] = matrizB[i][j];
                k++;
                if (k == nColunasB / 2) {
                    k = 0;
                }
            }
        }
        k = 0;
        for (i = nLinhasB / 2; i < nLinhasB; i++) {
            for (j = 0; j < nColunasB / 2; j++) {
                matrizB3[k][j] = matrizB[i][j];
            }
            k++;
            if (k == nLinhasB / 2) {
                k = 0;
            }
        }
        k = 0;
        int l = 0;
        for (i = nLinhasB / 2; i < nLinhasB; i++) {
            for (j = nColunasB / 2; j < nColunasB; j++) {
                matrizB4[k][l] = matrizB[i][j];
                l++;
                if (l == nColunasB / 2) {
                    l = 0;
                }
            }
            k++;
            if (k == nLinhasB / 2) {
                k = 0;
            }
        }
    }*/
    public static void divideMatrizB(int matrizB[][]) {
        matrizB1 = new int[nLinhasB / 2][nColunasB];
        matrizB2 = new int[nLinhasB / 2][nColunasB];
        //matrizB3 = new int[nLinhasB / 2][nColunasB / 2];
        //matrizB4 = new int[nLinhasB / 2][nColunasB / 2];
        int i, j;
        for (i = 0; i < nLinhasB / 2; i++) {
            for (j = 0; j < nColunasB; j++) {
                matrizB1[i][j] = matrizB[i][j];
            }
        }
        int k = 0;
        for (i = nLinhasB / 2; i < nLinhasB; i++) {
            for (j = 0; j < nColunasB; j++) {
                matrizB2[k][j] = matrizB[i][j];
            }
            k++;
            if (k == nLinhasB / 2) {
                k = 0;
            }
        }
    }
}