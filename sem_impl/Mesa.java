package sem_impl;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class Mesa {

    final static int QTD_FILOSOFOS = 6;
    final static int PRIMEIRO_FILOSOFO = 0;
    final static int ULTIMO_FILOSOFO = QTD_FILOSOFOS - 1;

    Semaphore mutex;
    Estado[] estadosAtuais = new Estado[QTD_FILOSOFOS];
    Semaphore[] talheres = new Semaphore[QTD_FILOSOFOS];
    Semaphore[] filosofos = new Semaphore[QTD_FILOSOFOS];

    public Mesa() {
        for (int i = 0; i < QTD_FILOSOFOS; i++) {
            talheres[i] = new Semaphore(1);
            estadosAtuais[i] = Estado.PENSANDO;
            filosofos[i] = new Semaphore(0);
        }
        this.mutex = new Semaphore(1);
        System.out.println(Arrays.toString(estadosAtuais));
    }

    public int esquerda(int indice_filosofo) {
        int esquerdo;
        if (indice_filosofo == PRIMEIRO_FILOSOFO) {
            esquerdo = ULTIMO_FILOSOFO;
        } else {
            esquerdo = indice_filosofo - 1;
        }
        return esquerdo;
    }

    public int direita(int indice_filosofo) {
        int direito;
        if (indice_filosofo == ULTIMO_FILOSOFO) {
            direito = PRIMEIRO_FILOSOFO;
        } else {
            direito = indice_filosofo + 1;
        }
        return direito;
    }

    public void pegarTalheres(int indice_filosofo) {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        estadosAtuais[indice_filosofo] = Estado.COM_FOME;
        if (aptoAComer(indice_filosofo)) {
            filosofos[indice_filosofo].release();
            estadosAtuais[indice_filosofo] = Estado.COMENDO;
        }
        mutex.release();
        try {
            filosofos[indice_filosofo].acquire();
            System.out.println(Arrays.toString(estadosAtuais));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void soltarTalheres(int indice_filosofo) {
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(Arrays.toString(estadosAtuais));
        estadosAtuais[indice_filosofo] = Estado.PENSANDO;
        if (direitaAptoAComer(indice_filosofo)) {
            estadosAtuais[direita(indice_filosofo)] = Estado.COMENDO;
            filosofos[direita(indice_filosofo)].release();
        }

        if (esquerdaAptoAComer(indice_filosofo)) {
            estadosAtuais[esquerda(indice_filosofo)] = Estado.COMENDO;
            filosofos[esquerda(indice_filosofo)].release();
        }

        mutex.release();
    }

    public boolean direitaAptoAComer(int indice_filosofo) {
        return estadosAtuais[direita(indice_filosofo)] == Estado.COM_FOME
                && estadosAtuais[direita(direita(indice_filosofo))] != Estado.COMENDO;
    }

    public boolean esquerdaAptoAComer(int indice_filosofo) {
        return estadosAtuais[esquerda(indice_filosofo)] == Estado.COM_FOME
                && estadosAtuais[esquerda(esquerda(indice_filosofo))] != Estado.COMENDO;
    }

    public boolean aptoAComer(int indice_filosofo) {
        return estadosAtuais[direita(indice_filosofo)] != Estado.COMENDO
                || estadosAtuais[esquerda(indice_filosofo)] != Estado.COMENDO;
    }
}
