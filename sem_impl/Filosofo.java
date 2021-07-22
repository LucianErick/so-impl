package sem_impl;

public class Filosofo implements Runnable {

    final static int TEMPO_MAXIMO = 1000;
    
    private int id;
    private String nome;

    Mesa mesa;

    public Filosofo(int id, String nome, Mesa mesa) {
        this.id = id;
        this.nome = nome;
        this.mesa = mesa;
        new Thread((Runnable)this, "filosofo" + id).start();
    }

    @Override
    public void run() {
        int tempo = 0;
        while (true) {
            tempo = (int) (Math.random() * TEMPO_MAXIMO);
            pensar(tempo);
            pegarTalheres();
            tempo = (int) (Math.random() * TEMPO_MAXIMO);
            comer(tempo);
            soltarTalheres();
        }
    }

    public void pensar(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            System.out.println("Pensou tanto que endoideceu.");
        }
    }

    public void comer(int tempo) {
        try {
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            System.out.println("Comeu tanto que explodiu");
        }
    }

    public void pegarTalheres() {
        mesa.pegarTalheres(this.id);
    }

    public void soltarTalheres() {
        mesa.soltarTalheres(this.id);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
