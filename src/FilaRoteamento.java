import java.util.LinkedList;
import java.util.PriorityQueue;

public class FilaRoteamento {
	
    private float tempoTotal = 0;
	private float tempoInicial = 0;
    private PriorityQueue<Evento> agenda;
    private LinkedList<Float> aleatorios;
	private LinkedList<Fila> filas;
	private Evento e;

    private float[] intervaloChegada;

	private int n;
	private int seed;

	public FilaRoteamento(LinkedList<Fila> filas, float[] intervaloChegada, float tempoInicial, int n, int seed) {
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

		while(aleatorios.size() > 2) {
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
				chegada(e.getSource(), e.getTarget());
			}

			if(e.tipo == 'p') {
				passagem(e.getSource(), e.getTarget());
			}

			if(e.tipo == 's') {
				saida(e.getSource(), e.getTarget());
			}
		}
		resultado();
	}

	public void chegada(Fila source, Fila target) {
		if(source.getF() < source.getK() || source.getK() == -1) {
			source.inc();
			if(source.getF() <= source.getC()) {
				agenda.add(source.target(aleatorios.pop(), (tempoTotal + rand(source.getInicioA(), source.getFimA())), source));
			}
		} else {
			source.incPerda();
		}
		agenda.add(new Evento(tempoTotal + rand(intervaloChegada[0],intervaloChegada[1]), source, target, 'c'));
	}

	public void saida(Fila source, Fila target) {
		source.dec();
		if(source.getF() >= source.getC()) {
			agenda.add(source.target(aleatorios.pop(), (tempoTotal + rand(source.getInicioA(), source.getFimA())), source));
		}
	}

	public void passagem(Fila source, Fila target) {
		source.dec();
		if(source.getF() >= source.getC()) {
			agenda.add(source.target(aleatorios.pop(), (tempoTotal + rand(source.getInicioA(), source.getFimA())), source));
		}
		
		if(target.getF() < target.getK() || target.getK() == -1) {
			target.inc();
			if(target.getF() <= target.getC()) {
				agenda.add(target.target(aleatorios.pop(), (tempoTotal + rand(target.getInicioA(), target.getFimA())), target));
			}
		} else {
			target.incPerda();
		}
	}

	public void geraAleatorios(int n, int seed) {
		float aux = seed;
		/*for(int i=0;i<n;i++) {
			aux = (float) (((aux * 25.214903917) + 11) % Math.pow(2, 44));
			aleatorios.add((float)(aux/Math.pow(2, 44)));
		}*/
		aleatorios.add((float) 0.2176);
		aleatorios.add((float) 0.0103);
		aleatorios.add((float) 0.1109);
		aleatorios.add((float) 0.3456);
		aleatorios.add((float) 0.991);
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
			System.out.println("Perdas: "+f.getPerdas());
		}
		System.out.println("Tempo Total: "+tempoTotal);
	}
}