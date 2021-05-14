import java.util.LinkedList;
import java.util.PriorityQueue;

public class FilaTandem {
	//só pra duas filas
    private float tempoTotal = 0;
	private float tempoInicial = 0;
    private PriorityQueue<Evento> agenda;
    private LinkedList<Float> aleatorios;
	private LinkedList<Fila> filas;
	private Evento e;

    private float[] intervaloChegada;

	private int n;
	private int seed;

	public FilaTandem(LinkedList<Fila> filas, float[] intervaloChegada, float tempoInicial, int n, int seed) {
		this.filas= filas;
		this.intervaloChegada = intervaloChegada;
		this.n = n;
		this.seed = seed;
		agenda = new PriorityQueue<Evento>();
		aleatorios = new LinkedList<Float>();
		geraAleatorios(n, seed);
		agenda.add(new Evento(tempoInicial, filas.get(0), filas.get(1), 'c'));
	}

    public void run() {

		while(aleatorios.size() > 1) {

			e = agenda.poll();
			
			for(Fila f : filas) {
				if(f.get(f.getF()) == null) {
					f.count(f.getF(), (e.tempo-tempoTotal));
				} else {
					f.count(f.getF(), (e.tempo-tempoTotal) + f.get(f.getF()));
				}
			}

			tempoTotal = e.tempo;
			
			if(e.tipo == 'c') {
				chegada();
			}

			if(e.tipo == 'p') {
				passagem();
			}

			if(e.tipo == 's') {
				saida();
			}
		}
		resultado();
	}

	public void chegada() {
		if(e.getSource().getF() < e.getSource().getK() || e.getSource().getK() == -1) {
			e.getSource().inc();
			if(e.getSource().getF() <= e.getSource().getC()) {
				agenda.add(new Evento(tempoTotal + rand(e.getSource().getInicioA(),e.getSource().getFimA()), e.getSource(), e.getTarget(), 'p'));
			}
		}
		agenda.add(new Evento(tempoTotal + rand(intervaloChegada[0],intervaloChegada[1]), e.getSource(), e.getTarget(), 'c'));
	}

	public void saida() {
		e.getTarget().dec();
		if(e.getTarget().getF() >= e.getTarget().getC()) {
			agenda.add(new Evento(tempoTotal + rand(e.getTarget().getInicioA(),e.getTarget().getFimA()), e.getSource(), e.getTarget(), 's'));
		}
	}

	public void passagem() {
		e.getSource().dec();
		if(e.getSource().getF() >= e.getSource().getC()) {
			agenda.add(new Evento(tempoTotal + rand(e.getSource().getInicioA(),e.getSource().getFimA()), e.getSource(), e.getTarget(), 'p'));
		}
		if(e.getTarget().getF() < e.getTarget().getK() || e.getTarget().getK() == -1) {
			e.getTarget().inc();
			if(e.getTarget().getF() <= e.getTarget().getC()) {
				agenda.add(new Evento(tempoTotal + rand(e.getTarget().getInicioA(),e.getTarget().getFimA()), e.getSource(), e.getTarget(), 's'));
			}
		}
	}

	public void geraAleatorios(int n, int seed) {
		float aux = seed;
		for(int i=0;i<n;i++) {
			aux = (float) (((aux * 25.214903917) + 11) % Math.pow(2, 44));
			aleatorios.add((float)(aux/Math.pow(2, 44)));
		}
	}

	public float rand(float a, float b) {
        return ((b - a) * aleatorios.pop()) + a;
    }

	public void resultado() {
		for(Fila f : filas) {
			int i = 0;
			while(f.getEstados().get(i) != null) {
				f.setPoncentagem(i, f.get(i), tempoTotal);
				i++;
			}
			i = 0;
			System.out.println(f);
			System.out.println(f.getEstados());
		}
		System.out.println("Tempo Total: "+tempoTotal);
	}
}