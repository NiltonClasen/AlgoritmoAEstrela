
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Nilton E. Clasen, Gustavo K. Heinen e Mateus Kienen
 */
public class GeradorCaminhos {

    public static void main(String[] args) {
        lerArquivo();
//        testes já prontos
//        teste5x5();
//        teste6x6();
//        teste7x7();
//        testeSemSaida();
//        testeDestinoOrigemIgual();
    }

    public static void lerArquivo() {
        try {
            String path = GeradorCaminhos.class.getResource("entrada5x5.txt").getPath();
            FileReader arq = new FileReader(path);
            BufferedReader lerArq = new BufferedReader(arq);
            String linha;
            Objeto[][] grade = null;
            Objeto origem = null;
            Objeto destino = null;
            ArrayList<Objeto> array = new ArrayList();
            while ((linha = lerArq.readLine()) != null) {
                if (linha.contains("tamanho")) {
                    String[] aux = linha.split(":");
                    int tamanho = Integer.parseInt(aux[1]);
                    grade = new Objeto[tamanho][tamanho];
                    for (int i = 0; i < grade.length; i++) {
                        for (int j = 0; j < grade[i].length; j++) {
                            grade[i][j] = new Objeto(i, j);
                        }
                    }
                } else if (linha.contains("origem")) {
                    linha = linha.substring(7);
                    String[] aux = linha.split("-");
                    int x = Integer.parseInt(aux[0]);
                    int y = Integer.parseInt(aux[1]);
                    origem = grade[x][y];
                } else if (linha.contains("destino")) {
                    linha = linha.substring(8);
                    String[] aux = linha.split("-");
                    int x = Integer.parseInt(aux[0]);
                    int y = Integer.parseInt(aux[1]);
                    destino = grade[x][y];
                } else if (linha.contains("bloqueio")) {
                    linha = linha.substring(9);
                    String[] aux = linha.split("-");
                    int x = Integer.parseInt(aux[0]);
                    int y = Integer.parseInt(aux[1]);
                    array.add(grade[x][y]);
                }
            }
            AlgoritmoEstrela aStar = new AlgoritmoEstrela(grade, origem, destino);
            for (Objeto obj : array) {
                aStar.addBloqueio(obj);
            }
            boolean resultado = aStar.iniciarPesquisa();
            imprimirDesafio(origem, destino, aStar, grade);
            imprimirResultado(origem, destino, aStar, grade);
            if (!resultado) {
                System.out.println("\nNão foi possível encontrar o destino");
            }

        } catch (Exception e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    public static void imprimirResultado(Objeto origem, Objeto destino, AlgoritmoEstrela aStar, Objeto[][] grade) {
        System.out.println("\n\n*********Desafio concluido*********");
        for (int i = 0; i < grade.length; i++) {
            System.out.println("");
            for (int j = 0; j < grade[i].length; j++) {
                if (origem.getX() == j && origem.getY() == i) {
                    System.out.print("[ O ]");
                } else if (destino.getX() == j && destino.getY() == i) {
                    System.out.print("[ D ]");
                } else {
                    boolean isCaminho = false;
                    for (int k = 0; k < aStar.getListaCaminho().size(); k++) {
                        if (aStar.getListaCaminho().get(k).getX() == j && aStar.getListaCaminho().get(k).getY() == i) {
                            System.out.print("[ x ]");
                            isCaminho = true;
                            break;
                        }
                    }
                    boolean isBloqueio = false;
                    for (int k = 0; k < aStar.getListaBloqueios().size(); k++) {
                        if (aStar.getListaBloqueios().get(k).getX() == j && aStar.getListaBloqueios().get(k).getY() == i) {
                            System.out.print("[ • ]");
                            isBloqueio = true;
                            break;
                        }
                    }
                    if (!isCaminho && !isBloqueio) {
                        System.out.print("[   ]");
                    }
                }

            }
        }
    }

    public static void imprimirDesafio(Objeto origem, Objeto destino, AlgoritmoEstrela aStar, Objeto[][] grade) {
        System.out.println("\n*********Desafio gerado*********");
        for (int i = 0; i < grade.length; i++) {
            System.out.println("");
            for (int j = 0; j < grade[i].length; j++) {
                if (origem.getX() == j && origem.getY() == i) {
                    System.out.print("[ O ]");
                } else if (destino.getX() == j && destino.getY() == i) {
                    System.out.print("[ D ]");
                } else {
                    boolean isBloqueio = false;
                    for (int k = 0; k < aStar.getListaBloqueios().size(); k++) {
                        if (aStar.getListaBloqueios().get(k).getX() == j && aStar.getListaBloqueios().get(k).getY() == i) {
                            System.out.print("[ • ]");
                            isBloqueio = true;
                            break;
                        }
                    }
                    if (!isBloqueio) {
                        System.out.print("[   ]");
                    }
                }

            }
        }
    }

    public static void testeDestinoOrigemIgual() {
        Objeto[][] grade = new Objeto[5][5];
        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                grade[i][j] = new Objeto(i, j);
            }
        }
        //configura caminho incial e destino
        Objeto origem = grade[0][0];
        Objeto destino = grade[0][0];
        /*configura bloqueios, exemplo
        *
        [ OD][   ][ • ][   ][   ]
        [   ][   ][ • ][   ][   ]
        [   ][   ][ • ][   ][   ]
        [   ][   ][ • ][   ][   ]
        [   ][   ][ • ][   ][   ]

        *
         */
        AlgoritmoEstrela aStar = new AlgoritmoEstrela(grade, origem, destino);
        aStar.addBloqueio(grade[2][0]);
        aStar.addBloqueio(grade[2][1]);
        aStar.addBloqueio(grade[2][2]);
        aStar.addBloqueio(grade[2][3]);
        aStar.addBloqueio(grade[2][4]);
        boolean resultado = aStar.iniciarPesquisa();
        imprimirDesafio(origem, destino, aStar, grade);
        imprimirResultado(origem, destino, aStar, grade);
        if (!resultado) {
            System.out.println("\nNão foi possível encontrar o destino");
        }
    }

    public static void testeSemSaida() {
        Objeto[][] grade = new Objeto[5][5];
        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                grade[i][j] = new Objeto(i, j);
            }
        }
        //configura caminho incial e destino
        Objeto origem = grade[0][0];
        Objeto destino = grade[4][0];
        /*configura bloqueios, exemplo
        *
        [ O ][   ][ • ][   ][ D ]
        [   ][   ][ • ][   ][   ]
        [   ][   ][ • ][   ][   ]
        [   ][   ][ • ][   ][   ]
        [   ][   ][ • ][   ][   ]

        *
         */
        AlgoritmoEstrela aStar = new AlgoritmoEstrela(grade, origem, destino);
        aStar.addBloqueio(grade[2][0]);
        aStar.addBloqueio(grade[2][1]);
        aStar.addBloqueio(grade[2][2]);
        aStar.addBloqueio(grade[2][3]);
        aStar.addBloqueio(grade[2][4]);
        boolean resultado = aStar.iniciarPesquisa();
        imprimirDesafio(origem, destino, aStar, grade);
        imprimirResultado(origem, destino, aStar, grade);
        if (!resultado) {
            System.out.println("\nNão foi possível encontrar o destino");
        }
    }

    public static void teste5x5() {
        Objeto[][] grade = new Objeto[5][5];
        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                grade[i][j] = new Objeto(i, j);
            }
        }
        //configura caminho incial e destino
        Objeto origem = grade[0][0];
        Objeto destino = grade[4][0];
        /*configura bloqueios, exemplo
        *
        *[O][ ][X][ ][D]
        *[ ][ ][X][ ][ ]
        *[ ][X][X][ ][ ]
        *[ ][ ][ ][ ][ ]
        *[ ][ ][ ][ ][ ]
        *
         */
        AlgoritmoEstrela aStar = new AlgoritmoEstrela(grade, origem, destino);
        aStar.addBloqueio(grade[2][0]);
        aStar.addBloqueio(grade[2][1]);
        aStar.addBloqueio(grade[2][2]);
        aStar.addBloqueio(grade[1][2]);
        boolean resultado = aStar.iniciarPesquisa();
        imprimirDesafio(origem, destino, aStar, grade);
        imprimirResultado(origem, destino, aStar, grade);
        if (!resultado) {
            System.out.println("\nNão foi possível encontrar o destino");
        }
    }

    public static void teste6x6() {
        Objeto[][] grade = new Objeto[6][6];
        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                grade[i][j] = new Objeto(i, j);
            }
        }

        //configura caminho incial e destino
        Objeto origem = grade[1][1];
        Objeto destino = grade[5][5];
        /*configura bloqueios, exemplo
        *
        [ ][ ][X][ ][ ][ ]
        [ ][O][X][ ][ ][ ]
        [ ][X][X][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][ ]
        [ ][ ][ ][ ][ ][D]
        *
         */
        AlgoritmoEstrela aStar = new AlgoritmoEstrela(grade, origem, destino);
        aStar.addBloqueio(grade[2][0]);
        aStar.addBloqueio(grade[2][1]);
        aStar.addBloqueio(grade[2][2]);
        aStar.addBloqueio(grade[1][2]);
        boolean resultado = aStar.iniciarPesquisa();
        imprimirDesafio(origem, destino, aStar, grade);
        imprimirResultado(origem, destino, aStar, grade);
        if (!resultado) {
            System.out.println("\nNão foi possível encontrar o destino");
        }
    }

    public static void teste7x7() {
        Objeto[][] grade = new Objeto[7][7];
        for (int i = 0; i < grade.length; i++) {
            for (int j = 0; j < grade[i].length; j++) {
                grade[i][j] = new Objeto(i, j);
            }
        }
        //configura caminho incial e destino
        Objeto origem = grade[0][2];
        Objeto destino = grade[6][3];
        /*configura bloqueios, exemplo
        *
        [   ][   ][   ][ • ][   ][   ][   ]
        [   ][ • ][   ][ • ][   ][ • ][ • ]
        [ O ][ • ][   ][   ][   ][ • ][   ]
        [   ][   ][   ][ • ][ • ][ • ][ D ]
        [   ][ • ][ • ][   ][   ][ • ][   ]
        [   ][ • ][   ][ • ][ • ][ • ][   ]
        [   ][   ][   ][   ][   ][   ][   ]
        *
         */
        AlgoritmoEstrela aStar = new AlgoritmoEstrela(grade, origem, destino);
        aStar.addBloqueio(grade[1][1]);
        aStar.addBloqueio(grade[1][2]);
        aStar.addBloqueio(grade[1][4]);
        aStar.addBloqueio(grade[1][5]);
        aStar.addBloqueio(grade[2][4]);
        aStar.addBloqueio(grade[3][0]);
        aStar.addBloqueio(grade[3][1]);
        aStar.addBloqueio(grade[3][3]);
        aStar.addBloqueio(grade[3][5]);
        aStar.addBloqueio(grade[4][3]);
        aStar.addBloqueio(grade[4][5]);
        aStar.addBloqueio(grade[5][1]);
        aStar.addBloqueio(grade[5][2]);
        aStar.addBloqueio(grade[5][3]);
        aStar.addBloqueio(grade[5][4]);
        aStar.addBloqueio(grade[5][5]);
        aStar.addBloqueio(grade[6][1]);

        boolean resultado = aStar.iniciarPesquisa();
        imprimirDesafio(origem, destino, aStar, grade);
        imprimirResultado(origem, destino, aStar, grade);
        if (!resultado) {
            System.out.println("\nNão foi possível encontrar o destino");
        }
    }
}
