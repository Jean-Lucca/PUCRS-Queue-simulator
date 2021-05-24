import java.util.HashMap;
import java.util.PriorityQueue;

public class Fila {
    private String id;
    private int f;
    private int c;
    private int k;
    private int perdas = 0;
    //par com fila/probabilidade
    private PriorityQueue<Par<Fila, Float>> q;
    private float inicioAtendimento;
    private float fimAtendimento;
	private HashMap<Integer, Float> estados;
    
    public Fila(String id, int c, int k, float inicioAtendimento, float fimAtendimento) {
        this.id = id;
        this.f = 0;
        this.c = c;
        this.k = k;
        this.q =  new PriorityQueue<Par<Fila, Float>>();
        this.inicioAtendimento = inicioAtendimento;
        this.fimAtendimento = fimAtendimento;
        estados = new HashMap<Integer, Float>();
    }

    public Fila(int c, int k, float inicioAtendimento, float fimAtendimento) {
        this.f = 0;
        this.c = c;
        this.k = k;
        this.inicioAtendimento = inicioAtendimento;
        this.fimAtendimento = fimAtendimento;
        estados = new HashMap<Integer, Float>();
    }

    public String getId() {
        return id;
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

    public float getInicioA() {
        return inicioAtendimento;
    }

    public float getFimA() {
        return fimAtendimento;
    }

    public void inc() {
        this.f++;
    }

    public void add(Fila target, Float prob) {
        q.add(new Par<Fila, Float>(target, prob));
    }

    //percorre a fila com as probabilidades e agenda saida ou passagem
    public Evento target(Float prob, Float tempo, Fila source) {
        //acumula as probabilidades em ordem decrescente ate encontrar a fila destino caso nao encontre agenda uma saida
        float acc = 0;
        for(Par<Fila, Float> par : q) {
            acc += par.getProb();
            if(prob <= acc) {
                //agenda uma passagem de uma fila para outra de acordo com as probabilidades
                return new Evento(tempo, source, par.getFila(), 'p');
            }
        }
        //ou agenda uma saida
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
