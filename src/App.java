public class App {
    public static void main(String[] args) {
        double[] intervaloChegada = {2,3};
        double[] intervaloAtendimento1 = {2,5};
        double[] intervaloAtendimento2 = {3,5};
        Fila F1 = new Fila(2, 3, intervaloAtendimento1);
        Fila F2 = new Fila(1, 3, intervaloAtendimento2);
        FilaTandem F1F2a = new FilaTandem(F1, F2, intervaloChegada, 2.5000, 100000, 1);
        F1F2a.run();
    }
}