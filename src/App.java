import java.util.LinkedList;
import java.util.PriorityQueue;
public class App {
    public static void main(String[] args) {
        float[] intervaloChegada = {1,4};
        float[] intervaloAtendimento1 = {1, 2};
        float[] intervaloAtendimento2 = {5, 10};
        float[] intervaloAtendimento3 = {10, 20};
        PriorityQueue<Par<Fila, Float>> q1 = new PriorityQueue();
        PriorityQueue<Par<Fila, Float>> q2 = new PriorityQueue();
        PriorityQueue<Par<Fila, Float>> q3 = new PriorityQueue();

        LinkedList<Fila> filas = new LinkedList<Fila>();
        Fila F1;
        Fila F2;
        Fila F3;
        F1 = new Fila(1, -1, intervaloAtendimento1, q1);
        F2 = new Fila(3, 5, intervaloAtendimento2, q2);
        F3 = new Fila(2, 8, intervaloAtendimento3, q3);
        q1.add(new Par<Fila, Float>(F2, (float) 0.8));q1.add(new Par<Fila, Float>(F3, (float) 0.2));
        q2.add(new Par<Fila, Float>(F3, (float) 0.5));q2.add(new Par<Fila, Float>(F1, (float) 0.3));
        q3.add(new Par<Fila, Float>(F2, (float) 0.7));
        filas.add(F1);filas.add(F2);filas.add(F3);
        FilaRoteamento F1F2a = new FilaRoteamento(filas, intervaloChegada, 1, 100000, 123);
        F1F2a.run();
    }
}