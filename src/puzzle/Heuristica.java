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
public class Heuristica {

    public Heuristica() {
    }

    /*
     * distância entre a posição atual e a posição objetivo de casa peça
     */
    /**
     *
     * @param atual
     * @return
     */
    public int distanciaManhattan(Tabuleiro atual) {
        int[] vetFinal = {1, 2, 3, 4, 5, 6, 7, 8, 0};

        List estadoObjetivo = new ArrayList();

        for (int i = 0; i < 9; i++) {
            estadoObjetivo.add(vetFinal[i]);
        }

        int h = 0;
        int x0, y0, x1, y1;
        float xyAtual;
        float xyObjetivo;

        List<Integer> estadoAtual = atual.getEstadoAtual();


        for (int i = 1; i < 9; i++) {
            /*
             * se não está na posição correta
             */
            if (estadoAtual.indexOf(i) != estadoObjetivo.indexOf(i)) {
                xyAtual = coordenada(estadoAtual.indexOf(i));
                xyObjetivo = coordenada(estadoObjetivo.indexOf(i));


                x0 = retornaX(xyAtual);
                y0 = retornaY(xyAtual);

                x1 = retornaX(xyObjetivo);
                y1 = retornaY(xyObjetivo);

                //faz o somatório da heurística
                h += Math.abs(x0 - x1) + Math.abs(y0 - y1);
            }
        }

        return h;
    }

    /*
     * retorna a parte inteira do número decimal
     * (correspodendo ao X)
     */
    public int retornaX(float coordenada){
        return ((int) coordenada);
    }

    /*
     * retorna a parte decimal do número
     * (como inteiro, correspondendo ao Y)
     */
    public int retornaY(float coordenada){
        /*
         * pega a parte decimal SOMENTE
         */
        double y = coordenada - Math.floor(coordenada);
        
        /*
         * remove o zero do número decimal, deixando apenas o valor de Y
         */
        y = y * 10;

        /*
         * arredonda o valor para cima
         */
        y = Math.round(y);

        return ((int) y);
    }

    public float coordenada(int index) {
        float valor = 0.0f;

        switch (index) {

            case 0: {
                valor = 1.1f;
                break;
            }
            case 1: {
                valor = 2.1f;
                break;
            }
            case 2: {
                valor = 3.1f;
                break;
            }
            case 3: {
                valor = 1.2f;
                break;
            }
            case 4: {
                valor = 2.2f;
                break;
            }
            case 5: {
                valor = 3.2f;
                break;
            }
            case 6: {
                valor = 1.3f;
                break;
            }
            case 7: {
                valor = 2.3f;
                break;
            }
            case 8: {
                valor = 3.3f;
                break;
            }
        }
        return valor;
    }
}
