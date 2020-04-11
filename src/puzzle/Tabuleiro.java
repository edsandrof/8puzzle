/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package puzzle;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Classe para objetos do tipo Tabuleiro. São os nós da árvore.
 * @author user
 */
public class Tabuleiro implements Serializable {

    private int g;
    private int h;
    private int f;
    private Tabuleiro pai;
    private List estadoAtual;

    public enum Direcao {

        ABAIXO, ACIMA, ESQUERDA, DIREITA
    }

    public enum Tipo {

        INICIAL, COMUM, OBJETIVO;
    }

    /** Clona o objeto (neste caso, Tabuleiro) completamente, sobreescrevendo o
     * método clone existente. É necessário pois ao realizar um clone com o método
     * original alguns atributos (como vetores) são copiados como referência
     * somente, logo, ao alterar um desses dados a mudança é refletida no
     * objeto original. Desta forma, todo o objeto é copiado e clonado
     * independente do original.
     * fonte: https://alemdocafe.wordpress.com/2009/08/26/clonagem-de-objetos/
     *
     * @return Object - cópia do objeto original (Tabuleiro)
     * @throws CloneNotSupportedException     * 
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        Tabuleiro novo = null;
        try {
            /*
             * serializa o objeto
             */
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(this);
            byte[] result = baos.toByteArray();

            /*
             * deserializa em um novo objeto, TOTALMENTE independente
             */
            ByteArrayInputStream bais = new ByteArrayInputStream(result);
            ObjectInputStream in = new ObjectInputStream(bais);
            novo = (Tabuleiro) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return novo;
    }

    public Tabuleiro() {
    }

    /**Retorna o valor da função heurística do tabuleiro atual f = g + h.
     *
     * @return int - retorna o valor de g + h.
     */
    public int getF() {
        return f;
    }

    /**Seta o atributo f (função heurística).
     *
     * @param f
     */
    public void setF(int f) {
        this.f = f;
    }

    /**Seta o atributo f (função heurística), resultando em f = g + h.
     */
    public void setF() {
        this.f = this.g + this.h;
    }

    /**Retorna a distância do nó inicial até este.
     *
     * @return int - distância do nó inicial até este.
     */
    public int getG() {
        return g;
    }

    /**Seta o valor da distância deste nó até o nó inicial.
     *
     * @param g - distância do nó inicial até este.
     */
    public void setG(int g) {
        this.g = g;
    }

    /**Seta o valor da distância deste nó até o nó inicial baseado no valor
     * de g do pai deste nó. Caso não tenha pai, g = 0, se tiver, g = g do pai + 1.
     */
    public void setG() {
        if (this.pai != null) {
            this.g = this.pai.getG() + 1;
        } else {
            this.g = 0;
        }
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setH() {
        Heuristica heuristica = new Heuristica();
        this.h = heuristica.distanciaManhattan(this);
    }

    public Tabuleiro getPai() {
        return pai;
    }

    public void setPai(Tabuleiro pai) {
        this.pai = pai;
    }

    public List getEstadoAtual() {
        return estadoAtual;
    }

    public void setEstadoAtual(List estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    public Tabuleiro(List estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

    public Tabuleiro(int g, int h, int f, Tabuleiro pai, List estadoAtual) {
        this.g = g;
        this.h = h;
        this.f = f;
        this.pai = pai;
        this.estadoAtual = estadoAtual;
    }

    /**
     *
     * @param direcao - enum com a direção da peça que ocupará o lugar do
     * espaço em branco.
     * @return Tabuleiro - com a nova configuração de peças resultante
     * @throws CloneNotSupportedException
     */
    public Tabuleiro mover(Direcao direcao) throws CloneNotSupportedException {
        Tabuleiro novo = (Tabuleiro) this.clone();

        //pega a configuração de peças atual
        List atual = novo.getEstadoAtual();

        //pega a posição da peça em branco (definido como 0)
        int pos = atual.indexOf(0);

        switch (direcao) {
            case ABAIXO: {
                if (pos > 2) {
                    Collections.swap(atual, pos, pos - 3);
                }
                break;
            }
            case ACIMA: {
                if (pos < 6) {
                    Collections.swap(atual, pos, pos + 3);
                }
                break;
            }
            case ESQUERDA: {
                if (pos != 2 && pos != 5 && pos != 8) {
                    Collections.swap(atual, pos, pos + 1);
                }
                break;
            }
            case DIREITA: {
                if (pos != 0 && pos != 3 && pos != 6) {
                    Collections.swap(atual, pos, pos - 1);
                }
                break;
            }
        }

        novo.setEstadoAtual(atual);
        novo.setPai(this);
        novo.setG();
        novo.setH();
        novo.setF();
        return novo;

    }

    /*
     * veja se os objetos são iguais (possuem a mesma
     * configuação de peças
     */

    /**Verifica se os Tabuleiros são iguais (possuem a mesma configuração de peças).
     *
     * @param tabuleiro - comparo da forma if(tabuleiro1.equals(tabuleiro2))...
     * @return boolean - se a ordem das peças for igual, retorna true, senão false.
     */
    public boolean equals(Tabuleiro tabuleiro) {
        List objetivo = tabuleiro.getEstadoAtual();
        List atual = this.getEstadoAtual();
        return objetivo.equals(atual);
    }

    /**Embaralha a ordem das peças em um determinado número de movimentos. Deve-se
     * partir de um estado objetivo e então embaralhar.
     *
     * @param quantidade - quantidade de movimentos que serão realizados para embaralhar.
     * @throws CloneNotSupportedException
     */
    public void randomize(int quantidade) throws CloneNotSupportedException {
        int valor;
        Tabuleiro novo = new Tabuleiro();

        for (int i = 0; i < quantidade; i++) {
            valor = (int) (Math.random() * 4);

            switch (valor) {
                case 0: {
                    novo = this.mover(Direcao.ABAIXO);
                    break;
                }
                case 1: {
                    novo = this.mover(Direcao.ACIMA);
                    break;
                }
                case 2: {
                    novo = this.mover(Direcao.ESQUERDA);
                    break;
                }
                case 3: {
                    novo = this.mover(Direcao.DIREITA);
                    break;
                }
            }

            this.setEstadoAtual(novo.getEstadoAtual());
        }
    }

    /**Imprime o Tabuleiro no formato:<br>
     * |1|2|3|<br>
     * |4|5|6|<br>
     * |7|8|  |
     *
     * @param tipo - enum que determina se o nó é inicial ou final. Esta informação
     * também é mostrada nestes nós.
     */
    public void imprimir(Tipo tipo) {
        for (int i = 0; i < 9; i++) {

            System.out.print("|");

            if ((Integer) this.getEstadoAtual().get(i) == 0) {
                System.out.print(" ");
            } else {
                System.out.print(this.getEstadoAtual().get(i));
            }

            if ((i + 1) % 3 == 0) {
                System.out.print("|");
                /*
                 * se está na segunda linha, última coluna
                 */
                if (i == 5) {
                    switch (tipo) {
                        case INICIAL: {
                            System.out.print(" <- início");
                            break;
                        }
                        case OBJETIVO: {
                            System.out.print(" <- objetivo");
                            break;
                        }
                    }
                }
                System.out.print("\n");
            }
        }
        System.out.println("-------");
    }
}
