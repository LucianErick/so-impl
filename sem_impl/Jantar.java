package sem_impl;

public class Jantar {
    public static void main(String[] args) {
        Mesa mesa = new Mesa();
        String[] nomesFilosofos = new String[] {"Pyton", "Aristóteles", "Platão", "Pitágoras", "Socrates", "Tales de Mileto"};
        for (int i = 0; i < Mesa.QTD_FILOSOFOS; i++) {
            new Filosofo(i, nomesFilosofos[i], mesa);
        }

    }
}
