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
public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        int[] vetInicial = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        int[] vetFinal = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        //int[] vetInicial = {1, 2, 3, 4, 5, 6, 8, 7, 0}; -- sem solução
        //0, 3, 7, 6, 8, 4, 5, 2, 1 -- 28 mov
        //4, 7, 6, 0, 1, 5, 8, 2, 3 -- 27 mov



        List estadoInicial = new ArrayList();
        List estadoFinal = new ArrayList();

        for (int i = 0; i < 9; i++) {
            estadoInicial.add(vetInicial[i]);
            estadoFinal.add(vetFinal[i]);
        }

        Heuristica heuristica = new Heuristica();


        //instancia os nós inicial e objetivo
        Tabuleiro inicio = new Tabuleiro(estadoInicial);
        Tabuleiro objetivo = new Tabuleiro(estadoFinal);

        inicio.randomize(100);
        System.out.println(inicio.getEstadoAtual());


        //nó raiz não possui nenhum antecessor
        inicio.setPai(null);

        //nó raiz tem profundidade 0
        inicio.setG();
        //define o resultado da função heurística
        inicio.setH();

        /*
         * define o valor de F
         */
        inicio.setF();

        Aestrela aestrela = new Aestrela();
        aestrela.setObjetivo(objetivo);

        long tempoInicioBusca = System.currentTimeMillis();
        if (aestrela.busca(inicio)) {
            long tempoFimBusca = System.currentTimeMillis();
            Tabuleiro solucao = new Tabuleiro();
            solucao = aestrela.getSolucao();

            aestrela.tracarCaminho(solucao);
            System.out.println("\nSolução encontrada em " + (aestrela.getCaminho().size() - 1) + " movimento(s)!");
            System.out.println("Busca realizada em " + (tempoFimBusca - tempoInicioBusca) / 1000 + " segundo(s).\n");
            System.out.println("-------");

            for (int i = 0; i < aestrela.getCaminho().size(); i++) {
                if (i == 0) {
                    aestrela.getCaminho().get(i).imprimir(Tabuleiro.Tipo.INICIAL);
                } else if (i == aestrela.getCaminho().size() - 1) {
                    aestrela.getCaminho().get(i).imprimir(Tabuleiro.Tipo.OBJETIVO);
                } else {
                    aestrela.getCaminho().get(i).imprimir(Tabuleiro.Tipo.COMUM);
                }
            }
            //System.out.println("Demorou: "+ (System.currentTimeMillis() - t0));
        } else {
            System.out.println("Não há solução!");
        }



    }
}
