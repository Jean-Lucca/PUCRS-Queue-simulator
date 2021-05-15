import java.util.LinkedList;
import java.util.PriorityQueue;

public class FilaRoteamento {
	
    private float tempoTotal = 0;
    private PriorityQueue<Evento> agenda;
    private LinkedList<Float> aleatorios;
	private LinkedList<Fila> filas;
	private Evento e;

    private float[] intervaloChegada;


	public FilaRoteamento(LinkedList<Fila> filas, float[] intervaloChegada, float tempoInicial, int n, int x0) {
		this.filas= filas;
		this.intervaloChegada = intervaloChegada;
		agenda = new PriorityQueue<Evento>();
		aleatorios = new LinkedList<Float>();
		geraAleatorios(n, x0);
		agenda.add(new Evento(tempoInicial, filas.getFirst(), filas.getFirst(), 'c'));
	}

	public FilaRoteamento(LinkedList<Fila> filas, float[] intervaloChegada, float tempoInicial, LinkedList<Float> aleatorios) {
		this.filas= filas;
		this.intervaloChegada = intervaloChegada;
		this.aleatorios = aleatorios;
		agenda = new PriorityQueue<Evento>();
		aleatorios = new LinkedList<Float>();
		agenda.add(new Evento(tempoInicial, filas.getFirst(), filas.getFirst(), 'c'));
	}


    public void run() {
		//usa dois aleatorios a menos lembrar disso ao testar com aleatorios controlados
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

	public void geraAleatorios(int n, int x0) {
		float aux = x0;
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
			System.out.println("---------------------------------------");
			System.out.println("Fila: "+f);
			System.out.println("Atendimento: "+f.getInicioA()+" ... "+f.getFimA());
			System.out.println("---------------------------------------");
			System.out.println("Estado			Probabilidade");
			int j = 0;
			while(f.getEstados().get(j) != null) {
				System.out.println(j+"                       "+ String.format("%.2f", f.get(j)) +"%");
				j++;
			}
			j = 0;
			System.out.println("Perdas: "+f.getPerdas());
		}
		System.out.println("Tempo Total: "+tempoTotal);
	}
}