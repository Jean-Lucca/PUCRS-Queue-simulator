import java.util.HashMap;

public class Fila {
    private int f;
    //servidores
    private int c;
    //capacidade
    private int k;
    private double[] intervaloAtendimento;
	private HashMap<Integer, Double> estados;
    public Fila(int c, int k, double[] intervaloAtendimento) {
        this.f = 0;
        this.c = c;
        this.k = k;
        this. intervaloAtendimento = intervaloAtendimento;
        estados = new HashMap<Integer, Double>();
        geraMapa();
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
    
    public double[] getIntervaloAtendimento() {
        return intervaloAtendimento;
    }

    public double getInicioA() {
        return intervaloAtendimento[0];
    }

    public double getFimA() {
        return intervaloAtendimento[1];
    }

    public void inc() {
        this.f++;
    }

    public void dec() {
        this.f--;
    }
    
    public void count(int estado, double tempo) {
        estados.put(estado, tempo);
    }

    public Double get(int estado) {
        return estados.get(estado);
    }

    public void geraMapa() {
		for(int i=0;i<=k;i++) {
			estados.put(i, 0.0);
		}
	}

    public void setPoncentagem(int estado, double tempo, double tempoTotal) {
        estados.replace(estado, (tempo/tempoTotal)*100);
    }

    @Override
    public String toString() {
        return "G/G/"+c+"/"+k+" "+"Intervalo de atendimento: "+intervaloAtendimento[0]+"-"+intervaloAtendimento[1];
    }
}
