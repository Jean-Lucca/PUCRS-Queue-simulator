public class Evento implements Comparable<Evento> {
    //o evento consiste em uma fila de origem e ma fila destino
    protected float tempo;
    private Fila source;
    private Fila target;
    protected char tipo;

    public Evento(float tempo, Fila source, Fila target ,char tipo) {
        this.tempo = tempo;    
        this.source = source;
        this.target = target;
        this.tipo = tipo;
    }


    public int compareTo(Evento e) {
        return Float.compare(tempo, e.tempo);
    }

    public float getTempo() {
        return tempo;
    }
    public Fila getSource() {
        return source;
    }
    public Fila getTarget() {
        return target;
    }
    public int getTipo() {
        return tipo;
    }

    public String toString() {
        return "Tempo: "+tempo+" Source: "+source+" Target: "+target+" Tipo: "+tipo;
    }
}