package mon_impl;

public class Jantar {
    public static void main(String[] args) {
        Mesa mesa = new Mesa();
        String[] nomesFilosofos = new String[] {"Pyton", "Aristóteles", "Platão", "Pitágoras", "Socrates", "Tales de Mileto"};
        for (int filosofo = 0; filosofo < 6; filosofo++) {
            new Filosofo("Filosofo_" + nomesFilosofos[filosofo], mesa, filosofo).start();
        }
    }
}
