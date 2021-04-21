import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class FilaSimples {
	
	private int fila = 0;
    private double tempoTotal = 0;
    private PriorityQueue<Evento> agenda;
	private HashMap<Integer, Double> estados;
    private LinkedList<Double> aleatorios;


	//servidores
	private int c;
	//capacidade
	private int k;
    private double[] intervaloChegada;
    private double[] intervalorAtendimento;
	
	//numero de aleatorios
	private int n;
	private int seed;

	public FilaSimples(int c, int k, double[] intervaloChegada, double[] intervalorAtendimento, double tempoInicial,
	int n, int seed) {

		this.c = c;
		this.k = k;
		this.intervaloChegada = intervaloChegada;
		this.intervalorAtendimento = intervalorAtendimento;
		this.n = n;
		this.seed = seed;
		agenda = new PriorityQueue<Evento>();
		estados = new HashMap<Integer, Double>();
		aleatorios = new LinkedList<Double>();
		geraAleatorios(n, seed);
		geraMapa();
		agenda.add(new Evento(tempoInicial, 'c'));
	
	}

    public void run() {

		while(aleatorios.size() > 0) {

			Evento e = agenda.poll();
			estados.put(fila, (e.tempo-tempoTotal) + estados.get(fila));
			tempoTotal = e.tempo;
			
			if(e.tipo == 'c') {
				if(fila < k) {
					fila++;
					if(fila <= c) {
						agenda.add(new Evento(tempoTotal + rand(intervalorAtendimento[0],intervalorAtendimento[1]), 's'));
					}
				}
				agenda.add(new Evento(tempoTotal + rand(intervaloChegada[0],intervaloChegada[1]), 'c'));
			}

			if(e.tipo == 's') {
				fila--;
				if(fila >= c) {
					agenda.add(new Evento(tempoTotal + rand(intervalorAtendimento[0],intervalorAtendimento[1]), 's'));
				}
			}
		}
		resultado();
	}

	public void geraMapa() {
		for(int i=0;i<=k;i++) {
			estados.put(i, 0.0);
		}
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
		System.out.println("\nG/G/"+c+"/"+k);
		for(int i=0;i<=k;i++) {
			System.out.println("fila "+i+": "+(estados.get(i)/tempoTotal)*100);
			estados.replace(i, (estados.get(i)/tempoTotal)*100);
		}
	}

	public double getProbabilidade(int i) {
		return estados.get(i);
	}
}