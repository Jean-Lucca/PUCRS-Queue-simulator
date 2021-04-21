import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class FilaTandem {
	
	private Fila fila1;
	private Fila fila2;
    private double tempoTotal = 0;
    private PriorityQueue<Evento> agenda;
    private LinkedList<Double> aleatorios;

    private double[] intervaloChegada;

	//numero de aleatorios
	private int n;
	private int seed;

	public FilaTandem(Fila fila1, Fila fila2, double[] intervaloChegada, double tempoInicial, int n, int seed) {
		this.fila1 = fila1;
		this.fila2 = fila2;
		this.intervaloChegada = intervaloChegada;
		this.n = n;
		this.seed = seed;
		agenda = new PriorityQueue<Evento>();
		aleatorios = new LinkedList<Double>();
		geraAleatorios(n, seed);
		agenda.add(new Evento(tempoInicial, 'c'));
	
	}

    public void run() {

		while(aleatorios.size() > 0) {

			Evento e = agenda.poll();
			fila1.count(fila1.getF(), (e.tempo-tempoTotal) + fila1.get(fila1.getF()));
			fila2.count(fila2.getF(), (e.tempo-tempoTotal) + fila2.get(fila2.getF()));
			tempoTotal = e.tempo;
			
			if(e.tipo == 'c') {
				if(fila1.getF() < fila1.getK()) {
					fila1.inc();
					if(fila1.getF() <= fila1.getC()) {
						agenda.add(new Evento(tempoTotal + rand(fila1.getInicioA(),fila1.getFimA()), 'p'));
					}
				}
				agenda.add(new Evento(tempoTotal + rand(intervaloChegada[0],intervaloChegada[1]), 'c'));
			}

			if(e.tipo == 'p') {
				fila1.dec();
				if(fila1.getF() >= fila1.getC()) {
					agenda.add(new Evento(tempoTotal + rand(fila1.getInicioA(),fila1.getFimA()), 'p'));
				}
				if(fila2.getF() < fila2.getK()) {
					fila2.inc();
					if(fila2.getF() <= fila2.getC()) {
						agenda.add(new Evento(tempoTotal + rand(fila2.getInicioA(),fila2.getFimA()), 's'));
					}
				}
			}

			if(e.tipo == 's') {
				fila2.dec();
				if(fila2.getF() >= fila2.getC()) {
					agenda.add(new Evento(tempoTotal + rand(fila2.getInicioA(),fila2.getFimA()), 's'));
				}
			}
		}
		resultado();
	}

	public void contabilizaTempo() {
		
	}

	public void geraAleatorios(int n, int seed) {
		double aux = seed;
		for(int i=0;i<n;i++) {
			aux = ((aux * 25) + 11) % 7;
			aleatorios.add(aux/7);
		}
	}

	public double rand(double a, double b) {
        return ((b - a) * aleatorios.pop()) + a;
    }

	public void resultado() {
		System.out.println(fila1);
		for(int i=0;i<=fila1.getK();i++) {
			System.out.println("fila "+i+": "+(fila1.get(i)/tempoTotal)*100);
			fila1.setPoncentagem(i, fila1.get(i), tempoTotal);
		}

		System.out.println(fila2);
		for(int i=0;i<=fila2.getK();i++) {
			System.out.println("fila "+i+": "+(fila2.get(i)/tempoTotal)*100);
			fila2.setPoncentagem(i, fila2.get(i), tempoTotal);
		}

		System.out.println("Tempo Total: "+tempoTotal);
	}
}