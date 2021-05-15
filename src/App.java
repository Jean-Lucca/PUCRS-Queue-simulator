import java.util.LinkedList;

import java.util.*;
public class App {
    static float[] intervaloChegada;
    static LinkedList<Fila> filas = new LinkedList<Fila>();
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {      
        menu();
    }

    public static FilaRoteamento centralDoAlunoCL() {
        filas = new LinkedList<Fila>();
        int primeiraChegada = 1;
        intervaloChegada = new float[] {1,5};

        Fila F1 = new Fila("SENHA", 1, -1, (float) 0.08, 1);
        Fila F2 = new Fila("ATENDIMENTO", 3, 10, 5, 15);
        Fila F3 = new Fila("FINANCEIRO", 2, 10, 5, 25);

        filas.add(F1);filas.add(F2);filas.add(F3);

        addNetwork("SENHA", "ATENDIMENTO", (float) 0.8);
        addNetwork("SENHA", "FINANCEIRO", (float) 0.2);
        addNetwork("ATENDIMENTO", "FINANCEIRO", (float) 0.3);
        addNetwork("FINANCEIRO", "ATENDIMENTO", (float) 0.5);

        return new FilaRoteamento(filas, intervaloChegada, primeiraChegada, 100000, 123);
    }

    public static FilaRoteamento modeloCL() {
        filas = new LinkedList<Fila>();
        int primeiraChegada = 1;
        intervaloChegada = new float[] {1,4};

        Fila F1 = new Fila("F1", 1, -1, 1, (float) 1.5);
        Fila F2 = new Fila("F2", 3, 5, 5, 10);
        Fila F3 = new Fila("F3", 2, 8, 10, 20);

        filas.add(F1);filas.add(F2);filas.add(F3);

        addNetwork("F1", "F2", (float) 0.8);
        addNetwork("F1", "F3", (float) 0.2);
        addNetwork("F2", "F3", (float) 0.5);
        addNetwork("F2", "F1", (float) 0.3);
        addNetwork("F3", "F2", (float) 0.7);

        return new FilaRoteamento(filas, intervaloChegada, primeiraChegada, 100000, 123);
    }

    public static FilaRoteamento centralDoAlunoT() {
        filas = new LinkedList<Fila>();
        LinkedList<Float> aleatorios = t();

        int primeiraChegada = 1;
        intervaloChegada = new float[] {1,5};

        Fila F1 = new Fila("SENHA", 1, -1, (float) 0.08, 1);
        Fila F2 = new Fila("ATENDIMENTO", 3, 10, 5, 15);
        Fila F3 = new Fila("FINANCEIRO", 2, 10, 5, 25);

        filas.add(F1);filas.add(F2);filas.add(F3);

        addNetwork("SENHA", "ATENDIMENTO", (float) 0.8);
        addNetwork("SENHA", "FINANCEIRO", (float) 0.2);
        addNetwork("ATENDIMENTO", "FINANCEIRO", (float) 0.3);
        addNetwork("FINANCEIRO", "ATENDIMENTO", (float) 0.5);

        return new FilaRoteamento(filas, intervaloChegada, primeiraChegada, aleatorios);
    }

    public static FilaRoteamento modeloT() {
        filas = new LinkedList<Fila>();
        LinkedList<Float> aleatorios = t();
        int primeiraChegada = 1;
        intervaloChegada = new float[] {1,4};

        Fila F1 = new Fila("F1", 1, -1, 1, (float) 1.5);
        Fila F2 = new Fila("F2", 3, 5, 5, 10);
        Fila F3 = new Fila("F3", 2, 8, 10, 20);

        filas.add(F1);filas.add(F2);filas.add(F3);

        addNetwork("F1", "F2", (float) 0.8);
        addNetwork("F1", "F3", (float) 0.2);
        addNetwork("F2", "F3", (float) 0.5);
        addNetwork("F2", "F1", (float) 0.3);
        addNetwork("F3", "F2", (float) 0.7);

        return new FilaRoteamento(filas, intervaloChegada, primeiraChegada, aleatorios);
    }

    public static LinkedList<Float> t() {
        LinkedList<Float> aleatorios = new LinkedList<Float>();
        aleatorios.add((float) 0.2176);
		aleatorios.add((float) 0.0103);
		aleatorios.add((float) 0.1109);
		aleatorios.add((float) 0.3456);
		aleatorios.add((float) 0.9910);
		aleatorios.add((float) 0.2323);
		aleatorios.add((float) 0.9211);
		aleatorios.add((float) 0.0322);
		aleatorios.add((float) 0.1211);
		aleatorios.add((float) 0.5131);
		aleatorios.add((float) 0.7208);
		aleatorios.add((float) 0.9172);
		aleatorios.add((float) 0.9922);
		aleatorios.add((float) 0.8324);
		aleatorios.add((float) 0.5011);
		aleatorios.add((float) 0.2931);
		//--
		aleatorios.add((float) 0.5011);
		aleatorios.add((float) 0.2931);
        return aleatorios;
    }

    public static void menu() {
        Scanner scan = new Scanner(System.in);

        System.out.println("1. centralDoAlunoT");
        System.out.println("2. modeloT");
        System.out.println("3. centralDoAlunoCL");
        System.out.println("4. modeloCL");
        System.out.println("0. sair");
        while(true) {
			FilaRoteamento r1 = centralDoAlunoT();
			FilaRoteamento r2 = modeloT();
			FilaRoteamento r3 = centralDoAlunoCL();
			FilaRoteamento r4 = modeloCL();
            switch (scan.nextInt()) {
                case 1:
                    r1.run();
                    break;
                case 2:
                    r2.run();
                    break;
                case 3:
                    r3.run();
                    break;
                case 4:
                    r4.run();
                    break;
                case 0:
                    return;
                default:
                    break;
            }
        }
    }
    
    public static void addNetwork(String sourceId, String targetId, float prob) {
        Fila source = null;

        for(Fila f : filas) {
            if(f.getId().equals(sourceId)) {
                source = f;
                break;
            }
        }

        for(Fila target : filas) {
            if(target.getId().equals(targetId)) {
                source.add(target, prob);
                return;
            }
        }
    }

}