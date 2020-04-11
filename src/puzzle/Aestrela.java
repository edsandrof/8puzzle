/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class Aestrela {

    private List<Tabuleiro> fechados = new ArrayList<Tabuleiro>();
    private List<Tabuleiro> abertos = new ArrayList<Tabuleiro>();
    private List<Tabuleiro> caminho = new ArrayList<Tabuleiro>();
    private Tabuleiro objetivo;
    private Tabuleiro solucao;

    public Tabuleiro getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(Tabuleiro objetivo) {
        this.objetivo = objetivo;
    }

    public Tabuleiro getSolucao() {
        return solucao;
    }

    public void setSolucao(Tabuleiro solucao) {
        this.solucao = solucao;
    }

    public List<Tabuleiro> getCaminho() {
        return caminho;
    }

    public void setCaminho(List<Tabuleiro> caminho) {
        this.caminho = caminho;
    }



    public Aestrela() {
    }

    public boolean busca(Tabuleiro inicial) throws CloneNotSupportedException {
        Tabuleiro corrente = new Tabuleiro();

        List<Tabuleiro> sucessores = new ArrayList<Tabuleiro>();

        /* 
         * adiciona o nó inicial a lista de aberto
         */
        this.insereEmAbertos(inicial);


        while (!this.abertos.isEmpty()) {        
            /*
             * pega o nó com menor valor de f do vetor de abertos
             */
            corrente = this.abertos.get(0);

            /*
             * adiciona o nó corrente a lista de fechados.
             */
            this.fechados.add(corrente);

            /*
             * remove o nó corrente da lista de abertos
             */
            this.abertos.remove(0);

            /*
             * se o estado corrente é a solução, seta na classe
             * e retorna verdadeiro
             */            
            if (corrente.equals(this.objetivo)) {
                this.solucao = corrente;
                return true;
            }

            sucessores = expande(corrente);
            /*
             * remove os sucessores que já estão na lista de fechados
             */
            sucessores = removeFechados(sucessores);

            /*
             * analiza a lista abertos
             */

            trataSucessores(sucessores, corrente);

        }

        return false;

    }

    /*
     * remove os sucessores que já estão na lista fechada
     */
    public List<Tabuleiro> removeFechados(List<Tabuleiro> sucessores) {
        boolean achouFechados = false;
        List<Tabuleiro> aux = new ArrayList<Tabuleiro>();

        for (int i = 0; i < sucessores.size(); i++) {
            for (int j = 0; j < this.fechados.size(); j++) {
                if (sucessores.get(i).equals(this.fechados.get(j))) {
                    aux.add(sucessores.get(i));
                    achouFechados = true;
                }
            }
        }
        if (achouFechados) {
            sucessores.removeAll(aux);
        }

        return sucessores;
    }

    /*
     * expande o nó atual em todos os possíveis sucessores
     * retorna um vetor com estes sucessores
     */
    public List<Tabuleiro> expande(Tabuleiro tabuleiro) throws CloneNotSupportedException {

        List<Tabuleiro> sucessores = new ArrayList<Tabuleiro>();

        /*
         * pega a configuração de peças atual
         */
        List atual = tabuleiro.getEstadoAtual();

        /*
         * pega a posição da peça em branco (definido como 0)
         */
        int pos = atual.indexOf(0);

        switch (pos) {
            case 0: {
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ACIMA));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ESQUERDA));
                break;
            }
            case 1: {
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ACIMA));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ESQUERDA));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.DIREITA));
                break;
            }
            case 2: {
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ACIMA));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.DIREITA));
                break;
            }
            case 3: {
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ABAIXO));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ESQUERDA));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ACIMA));
                break;
            }
            case 4: {
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ABAIXO));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.DIREITA));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ACIMA));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ESQUERDA));
                break;
            }
            case 5: {
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ABAIXO));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ACIMA));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.DIREITA));
                break;
            }
            case 6: {
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ABAIXO));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ESQUERDA));
                break;
            }
            case 7: {
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ABAIXO));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.DIREITA));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ESQUERDA));
                break;
            }
            case 8: {
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.ABAIXO));
                sucessores.add(tabuleiro.mover(Tabuleiro.Direcao.DIREITA));
                break;
            }
        }

        return sucessores;
    }

    public void trataSucessores(List<Tabuleiro> sucessores, Tabuleiro corrente) {


        List<Tabuleiro> remover = new ArrayList<Tabuleiro>();
        Tabuleiro melhor = new Tabuleiro();

        for (int i = 0; i < sucessores.size(); i++) {
            if (existeEmAbertos(sucessores.get(i))) {
                melhorCaminho(sucessores.get(i));
            } else {
                /*
                 * não existe na lista aberta, acrescente
                 */
                sucessores.get(i).setPai(corrente);
                sucessores.get(i).setG();
                sucessores.get(i).setH();
                sucessores.get(i).setF();
                insereEmAbertos(sucessores.get(i));
            }
        }
    }

    /*
     * verifica se o nó existe em abertos
     */
    public boolean existeEmAbertos(Tabuleiro sucessor) {
        for (int i = 0; i < this.abertos.size(); i++) {
            if (sucessor.equals(this.abertos.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void melhorCaminho(Tabuleiro sucessor) {
        Tabuleiro emAbertos = new Tabuleiro();
        for (int i = 0; i < this.abertos.size(); i++) {
            if (this.abertos.get(i).equals(sucessor)) {
                /*
                 * se o caminho (G) do nó sucessor for melhor do que o que está
                 * em abertos, atualiza o pai de abertos para o pai do sucessor,
                 * e atualiza G e F
                 */
                if (sucessor.getG() < this.abertos.get(i).getG()) {
                    emAbertos = this.abertos.get(i);
                    this.abertos.remove(i);
                    emAbertos.setPai(sucessor.getPai());
                    emAbertos.setG();
                    emAbertos.setF();
                    insereEmAbertos(emAbertos);
                }
                break;
            }
        }
    }

    /* insere os nós no vetor ABERTOS, de maneira que
     * o primeiro nó seja o com menor f.
     */
    public void insereEmAbertos(Tabuleiro tabuleiro) {
        /*
         * insere nó no final do vetor
         */
        if ((this.abertos.size() == 0)
                || this.abertos.get(this.abertos.size() - 1).getF() <= tabuleiro.getF()) {
            this.abertos.add(tabuleiro);
        } else {
            for (int i = 0; i < this.abertos.size(); i++) {
                if (tabuleiro.getF() < this.abertos.get(i).getF()) {
                    this.abertos.add(i, tabuleiro);
                    break;
                }
            }
        }
    }

    public void tracarCaminho(Tabuleiro pai) {
        if (pai != null) {
            this.caminho.add(0, pai);
            tracarCaminho(pai.getPai());
        }
    }

    public List<Tabuleiro> getAbertos() {
        return abertos;
    }

    public void setAbertos(List<Tabuleiro> abertos) {
        this.abertos = abertos;
    }

    public List<Tabuleiro> getFechados() {
        return fechados;
    }

    public void setFechados(List<Tabuleiro> fechados) {
        this.fechados = fechados;
    }

    
}

