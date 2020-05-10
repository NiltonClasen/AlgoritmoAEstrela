
import java.util.ArrayList;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author Nilton E. Clasen, Gustavo K. Heinen e Mateus Kienen
 */
public class AlgoritmoEstrela {

    private Objeto[][] labirinto;
    private Objeto origem;
    private Objeto destino;
    private ArrayList<Objeto> listaAberta;
    private ArrayList<Objeto> listaFechada;
    private ArrayList<Objeto> listaCaminho;
    private ArrayList<Objeto> listaBarreira;

    public AlgoritmoEstrela(Objeto labirinto[][], Objeto origem, Objeto destino) {
        this.labirinto = labirinto;
        this.origem = origem;
        this.destino = destino;
        listaAberta = new ArrayList<Objeto>();
        listaFechada = new ArrayList<Objeto>();
        listaCaminho = new ArrayList<Objeto>();
        listaBarreira = new ArrayList<Objeto>();
    }

    //Verifica se pode começar a realizar a busca A*
    public boolean iniciarPesquisa() {
        if (getOrigem() == getDestino()) { //caso seja a mesma, nem precisa realizar a pesquisa
            return true;
        }

        listaAberta.add(getOrigem()); //adiciona origem a lista de Objetos que ja foram pesquisados
        if (pesquisar()) {
            return salvarCaminho(); //concluiu tudo e agora vai preencher o caminho correto
        }
        return false; //não conseguiu encontrar nenhum caminho
    }

    private boolean pesquisar() {
        //procura objeto com menor custo total nas listas abertas
        Objeto atual = listaAberta.get(0);
        for (int i = 1; i < listaAberta.size(); i++) {
            if (atual.getCustoMaximo() > listaAberta.get(i).getCustoMaximo()) {
                atual = listaAberta.get(i);
            }
        }

        listaFechada.add(atual); //Se chegou aqui é por que tem q mover o objeto
        listaAberta.remove(atual); //Atualiza a lista  

        if (atual == destino) { //Acabou
            return true;
        }

        VerificaObjetoDireita(atual);
        VerificaObjetoEsquerda(atual);
        VerificaObjetoAbaixo(atual);
        VerificaObjetoAcima(atual);

        if (listaAberta.isEmpty()) { //Não há nenhum caminho livre
            return false;
        }

        return pesquisar(); //novamente chama a pesquisa a procura do destino
    }

    //Vai preenchendo o caminho correto do destino até a origem
    private boolean salvarCaminho() {
        Objeto atual = getDestino();

        if (atual == null) {
            return false;
        }

        while (atual != null) {
            listaCaminho.add(atual);
            atual = atual.getPai();
        }
        return true;

    }

    public void addBloqueio(Objeto bloqueio) {
        listaBarreira.add(bloqueio);
    }

    private void VerificaObjetoDireita(Objeto atual) {
        int direita = atual.getX() + 1;

        if (direita >= labirinto[0].length) {
            return;
        }

        Objeto adjacenteDireta = labirinto[direita][atual.getY()];
        if (!listaFechada.contains(adjacenteDireta) && !listaBarreira.contains(adjacenteDireta)) {
            int custoPadrao = atual.getCustoPadrao() + 1;
            int custoHeuristica = Math.abs(destino.getX() - adjacenteDireta.getX())
                    +
                    Math.abs(destino.getY() - adjacenteDireta.getY()); //É aqui que realiza o cálculo da heuristica. Tem que ser abs, pq tem q ser sempre positivo

            if (!listaAberta.contains(adjacenteDireta)) { //Caso não esteja na lista, pega do adjacente
                adjacenteDireta.setPai(atual);
                listaAberta.add(adjacenteDireta);
                adjacenteDireta.setCustoPadrao(custoPadrao);
                adjacenteDireta.setCustoHeuristica(custoHeuristica);
            } else {
                if (adjacenteDireta.getCustoHeuristica() > custoHeuristica) { //Caso ja esteja e seu custo atual for maior que o novo
                    adjacenteDireta.setPai(atual); //Faz a troca e atualiza tudo
                    adjacenteDireta.setCustoPadrao(custoPadrao);
                    adjacenteDireta.setCustoHeuristica(custoHeuristica);
                }
            }
        }
    }

    private void VerificaObjetoEsquerda(Objeto atual) {
        int esquerda = atual.getX() - 1;

        if (esquerda < 0) {
            return;
        }

        Objeto adjacenteEsquerda = getGrade()[esquerda][atual.getY()];
        if (!listaFechada.contains(adjacenteEsquerda) && !listaBarreira.contains(adjacenteEsquerda)) {
            int custoPadrao = atual.getCustoPadrao() + 1;
            int custoHeuristica = Math.abs(getDestino().getX() - adjacenteEsquerda.getX())
                    + //calcula custo H
                    Math.abs(getDestino().getY() - adjacenteEsquerda.getY()); //É aqui que realiza o cálculo da heuristica. Tem que ser abs, pq tem q ser sempre positivo

            if (!listaAberta.contains(adjacenteEsquerda)) {//Caso não esteja na lista, pega do adjacente
                adjacenteEsquerda.setPai(atual);
                listaAberta.add(adjacenteEsquerda);
                adjacenteEsquerda.setCustoPadrao(custoPadrao);
                adjacenteEsquerda.setCustoHeuristica(custoHeuristica);
            } else {
                if (adjacenteEsquerda.getCustoHeuristica() > custoHeuristica) { //Caso ja esteja e seu custo atual for maior que o novo
                    adjacenteEsquerda.setPai(atual); //Faz a troca e atualiza tudo
                    adjacenteEsquerda.setCustoPadrao(custoPadrao);
                    adjacenteEsquerda.setCustoHeuristica(custoHeuristica);
                }
            }
        }
    }

    private void VerificaObjetoAbaixo(Objeto atual) {
        int abaixo = atual.getY() + 1;

        if (abaixo >= labirinto.length) {
            return;
        }

        Objeto adjacenteAbaixo = labirinto[atual.getX()][abaixo];
        if (!listaFechada.contains(adjacenteAbaixo) && !listaBarreira.contains(adjacenteAbaixo)) {
            int custoPadrao = atual.getCustoPadrao() + 1;
            int custoHeuristica = Math.abs(getDestino().getX() - adjacenteAbaixo.getX())
                    + //calcula custo H
                    Math.abs(getDestino().getY() - adjacenteAbaixo.getY()); //É aqui que realiza o cálculo da heuristica. Tem que ser abs, pq tem q ser sempre positivo

            if (!listaAberta.contains(adjacenteAbaixo)) {//Caso não esteja na lista, pega do adjacente
                adjacenteAbaixo.setPai(atual);
                listaAberta.add(adjacenteAbaixo);
                adjacenteAbaixo.setCustoPadrao(custoPadrao);
                adjacenteAbaixo.setCustoHeuristica(custoHeuristica);
            } else {
                if (adjacenteAbaixo.getCustoHeuristica() > custoHeuristica) {//Caso ja esteja e seu custo atual for maior que o novo
                    adjacenteAbaixo.setPai(atual); //Faz a troca e atualiza tudo
                    adjacenteAbaixo.setCustoPadrao(custoPadrao);
                    adjacenteAbaixo.setCustoHeuristica(custoHeuristica);
                }
            }
        }
    }

    private void VerificaObjetoAcima(Objeto atual) {
        int acima = atual.getY() - 1;

        if (acima < 0) {
            return;
        }

        Objeto adjacenteAcima = getGrade()[atual.getX()][acima];
        if (!listaFechada.contains(adjacenteAcima) && !listaBarreira.contains(adjacenteAcima)) {
            int custoPadrao = atual.getCustoPadrao() + 1;
            int custoHeuristica = Math.abs(getDestino().getX() - adjacenteAcima.getX())
                    + //calcula custo H
                    Math.abs(getDestino().getY() - adjacenteAcima.getY());//É aqui que realiza o cálculo da heuristica. Tem que ser abs, pq tem q ser sempre positivo

            if (!listaAberta.contains(adjacenteAcima)) {//Caso não esteja na lista, pega do adjacente
                adjacenteAcima.setPai(atual);
                listaAberta.add(adjacenteAcima);
                adjacenteAcima.setCustoPadrao(custoPadrao);
                adjacenteAcima.setCustoHeuristica(custoHeuristica);
            } else {
                if (adjacenteAcima.getCustoHeuristica() > custoHeuristica) {//Caso ja esteja e seu custo atual for maior que o novo
                    adjacenteAcima.setPai(atual); //Faz a troca e atualiza tudo
                    adjacenteAcima.setCustoPadrao(custoPadrao);  
                    adjacenteAcima.setCustoHeuristica(custoHeuristica);
                }
            }
        }
    }

    public Objeto[][] getGrade() {
        return labirinto;
    }

    public void setGrade(Objeto[][] labirinto) {
        this.labirinto = labirinto;
    }

    public Objeto getOrigem() {
        return origem;
    }

    public void setOrigem(Objeto origem) {
        this.origem = origem;
    }

    public Objeto getDestino() {
        return destino;
    }

    public void setDestino(Objeto destino) {
        this.destino = destino;
    }

    public ArrayList<Objeto> getListaCaminho() {
        return listaCaminho;
    }

    public ArrayList<Objeto> getListaBloqueios() {
        return listaBarreira;
    }
}
