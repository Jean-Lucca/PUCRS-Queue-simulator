import java.util.Collections;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Fila {
    private int f;
    //servidores
    private int c;
    //capacidade
    private int k;
    private int perdas = 0;
    private PriorityQueue<Par<Fila, Float>> q;
    private float[] intervaloAtendimento;
	private HashMap<Integer, Float> estados;
    
    public Fila(int c, int k, float[] intervaloAtendimento, PriorityQueue<Par<Fila, Float>> q) {
        this.f = 0;
        this.c = c;
        this.k = k;
        this.q = q;
        this. intervaloAtendimento = intervaloAtendimento;
        estados = new HashMap<Integer, Float>();
    }

    public Fila(int c, int k, float[] intervaloAtendimento) {
        this.f = 0;
        this.c = c;
        this.k = k;
        this. intervaloAtendimento = intervaloAtendimento;
        estados = new HashMap<Integer, Float>();
    }

    public int getF() {
        return f;
    }

    public int getC() {
        return c;
    }
    
    public int getK() {
        return k;
    }

    public float[] getIntervaloAtendimento() {
        return intervaloAtendimento;
    }

    public float getInicioA() {
        return intervaloAtendimento[0];
    }

    public float getFimA() {
        return intervaloAtendimento[1];
    }

    public void inc() {
        this.f++;
    }

    public Evento target(Float prob, Float tempo, Fila source) {
        float acc = 0;
        for(Par<Fila, Float> par : q) {
            acc += par.getProb();
            if(prob < acc) {
                return new Evento(tempo, source, par.getFila(), 'p');
            }
        }
        return new Evento(tempo, source, source, 's');
    }

    public void dec() {
        this.f--;
    }

    public void incPerda() {
        this.perdas++;
    }

    public int getPerdas() {
        return perdas;
    }
    
    public void count(int estado, float tempo) {
        estados.put(estado, tempo);
    }

    public Float get(int estado) {
        return estados.get(estado);
    }
    
    public HashMap<Integer, Float> getEstados() {
        return estados;
    }

    public void setPoncentagem(int estado, float tempo, float tempoTotal) {
        estados.replace(estado, (tempo/tempoTotal)*100);
    }

    @Override
    public String toString() {
        if(k == -1) {
            return "G/G/"+c;
        }
        return "G/G/"+c+"/"+k;
    }
}
