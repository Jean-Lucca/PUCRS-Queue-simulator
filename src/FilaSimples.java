import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class FilaSimples {
	
	private int fila = 0;
    private float tempoTotal = 0;
    private PriorityQueue<Evento> agenda;
	private HashMap<Integer, Float> estados;
    private LinkedList<Float> aleatorios;


	//servidores
	private int c;
	//capacidade
	private Integer k;
    private float[] intervaloChegada;
    private float[] intervalorAtendimento;
	
	//numero de aleatorios
	private int n;
	private int seed;

	public FilaSimples(int c, Integer k, float[] intervaloChegada, float[] intervalorAtendimento, float tempoInicial,
	int n, int seed) {

		this.c = c;
		this.k = k;
		this.intervaloChegada = intervaloChegada;
		this.intervalorAtendimento = intervalorAtendimento;
		this.n = n;
		this.seed = seed;
		agenda = new PriorityQueue<Evento>();
		estados = new HashMap<Integer, Float>();
		aleatorios = new LinkedList<Float>();
		geraAleatorios(n, seed);
		geraMapa();
		agenda.add(new Evento(tempoInicial, null, null, 'c'));
	
	}

    public void run() {

		while(aleatorios.size() > 0) {

			Evento e = agenda.poll();
			estados.put(fila, (e.tempo-tempoTotal) + estados.get(fila));
			tempoTotal = e.tempo;
			
			if(e.tipo == 'c') {
				if(fila < k || k == null) {
					fila++;
					if(fila <= c) {
						agenda.add(new Evento(tempoTotal + rand(intervalorAtendimento[0],intervalorAtendimento[1]), null, null, 's'));
					}
				}
				agenda.add(new Evento(tempoTotal + rand(intervaloChegada[0],intervaloChegada[1]), null, null, 'c'));
			}

			if(e.tipo == 's') {
				fila--;
				if(fila >= c) {
					agenda.add(new Evento(tempoTotal + rand(intervalorAtendimento[0],intervalorAtendimento[1]), null, null, 's'));
				}
			}
		}
		resultado();
	}

	public void geraMapa() {
		for(int i=0;i<=k;i++) {
			estados.put(i, (float) 0.0);
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
		System.out.println("\nG/G/"+c+"/"+k);
		for(int i=0;i<=k;i++) {
			System.out.println("fila "+i+": "+(estados.get(i)/tempoTotal)*100);
			estados.replace(i, (estados.get(i)/tempoTotal)*100);
		}
	}

	public float getProbabilidade(int i) {
		return estados.get(i);
	}
}