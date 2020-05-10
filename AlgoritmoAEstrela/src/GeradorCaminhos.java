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
        teste5x5();
        teste6x6();
        teste7x7();
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

        long tempoAntes = System.nanoTime();
        boolean pesquisaOk = aStar.iniciarPesquisa();
        long tempoDepois = System.nanoTime();
        imprimirDesafio(origem, destino, aStar, grade);
        imprimirResultado(origem, destino, aStar, grade);
        System.out.println("\nTempo de pesquisa: " + (tempoDepois - tempoAntes) + "ns");
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

        long tempoAntes = System.nanoTime();
        aStar.iniciarPesquisa();
        long tempoDepois = System.nanoTime();
        imprimirDesafio(origem, destino, aStar, grade);
        imprimirResultado(origem, destino, aStar, grade);
        System.out.println("\nTempo de pesquisa: " + (tempoDepois - tempoAntes) + "ns");
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

        long tempoAntes = System.nanoTime();
        aStar.iniciarPesquisa();
        long tempoDepois = System.nanoTime();
        imprimirDesafio(origem, destino, aStar, grade);
        imprimirResultado(origem, destino, aStar, grade);
        System.out.println("\nTempo de pesquisa: " + (tempoDepois - tempoAntes) + "ns");
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
        System.out.println("*********Desafio gerado*********");
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
}
