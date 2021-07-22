package mon_impl;

public class Filosofo extends Thread {

   final static int TEMPO_MAXIMO = 100;

   Mesa mesa;
   int indice_filosofo;

   public Filosofo(String nome, Mesa mesaDosFilosofos, int filosofo) {
      super(nome);
      mesa = mesaDosFilosofos;
      indice_filosofo = filosofo;
   }

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
         sleep(tempo);
      } catch (InterruptedException e) {
         System.out.println("O Filófoso pensou tanto que endoideceu");
      }
   }

   public void comer(int tempo) {
      try {
         sleep(tempo);
      } catch (InterruptedException e) {
         System.out.println("O Filósofo enxeu demais o buxim");
      }
   }

   public void pegarTalheres() {
      mesa.pegarTalheres(indice_filosofo);
   }

   public void soltarTalheres() {
      mesa.soltarTalheres(indice_filosofo);
   }
}
