package mon_impl;

public class Mesa {

    final static int PENSANDO = 1;
    final static int COM_FOME = 2;
    final static int COMENDO = 3;

    final static int QTD_FILOSOFOS = 6;
    // Eh importante definir o primeiro na ordem pois, dessa maneira, previne-se
    // deadlocks.
    final static int PRIMEIRO_FILOSOFO = 0;
    final static int ULTIMO_FILOSOFO = QTD_FILOSOFOS - 1;

    // Em que se o estado do talher for 'True' ele esta livre e 'False' caso nao.
    boolean[] talheres = new boolean[QTD_FILOSOFOS];
    int[] filosofos = new int[QTD_FILOSOFOS];
    int[] tentativas = new int[QTD_FILOSOFOS];

    public Mesa() {
        for (int i = 0; i < QTD_FILOSOFOS; i++) {
            talheres[i] = true;
            filosofos[i] = PENSANDO;
            tentativas[i] = 0;
        }
    }

    public synchronized void pegarTalheres(int indice_filosofo) {
        filosofos[indice_filosofo] = COM_FOME;
        while (filosofos[esquerda(indice_filosofo)] == COMENDO || filosofos[direita(indice_filosofo)] == COMENDO) {
            try {
                tentativas[indice_filosofo]++;
                wait();
            } catch (InterruptedException e) {
                System.out.println("O filósofo morreu devido a starvation." + "\n Press F to pay respects.");
            }
        }
        tentativas[indice_filosofo] = 0;
        talheres[talherEsquerdo(indice_filosofo)] = false;
        talheres[talherDireito(indice_filosofo)] = false;
        filosofos[indice_filosofo] = COMENDO;
        mostraEstadosFilosofos();
        mostraEstadoTalheres();
        mostraTentativas();
    }

    public synchronized void soltarTalheres(int indice_filosofo) {
        talheres[talherEsquerdo(indice_filosofo)] = true;
        talheres[talherDireito(indice_filosofo)] = true;
        if (filosofos[esquerda(indice_filosofo)] == COM_FOME || filosofos[direita(indice_filosofo)] == COM_FOME) {
            notifyAll();
        }
        filosofos[indice_filosofo] = PENSANDO;
        mostraEstadosFilosofos();
        mostraEstadoTalheres();
        mostraTentativas();
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

    public int talherEsquerdo(int indice_filosofo) {
        int talherEsquerdo = indice_filosofo;
        return talherEsquerdo;
    }

    public int talherDireito(int indice_filosofo) {
        int talherDireito;
        if (indice_filosofo == ULTIMO_FILOSOFO) {
            talherDireito = 0;
        } else {
            talherDireito = indice_filosofo + 1;
        }
        return talherDireito;
    }

    public void mostraEstadosFilosofos() {
        String estado = "-";
        System.out.print("Filósofos = [ ");
        for (int i = 0; i < QTD_FILOSOFOS; i++) {
            switch (filosofos[i]) {
                case PENSANDO:
                    estado = "PENSANDO";
                    break;
                case COMENDO:
                    estado = "COMENDO";
                    break;
                case COM_FOME:
                    estado = "COM_FOME";
                    break;
            }
            System.out.print(estado + " ");
        }
        System.out.println("]");
    }

    public void mostraEstadoTalheres() {
        String estado = "-";
        System.out.print("Talheres = [ ");
        for (int i = 0; i < QTD_FILOSOFOS; i++) {
            if (talheres[i]) {
                estado = "LIVRE";
            } else {
                estado = "OCUPADO";
            }
            System.out.print(estado + " ");
        }
        System.out.println("]");
    }

    public void mostraTentativas() {
        System.out.print("Tentou comer = [ ");
        for (int i = 0; i < QTD_FILOSOFOS; i++) {
            System.out.print(filosofos[i] + " ");
        }
        System.out.println("]\n");
    }
}