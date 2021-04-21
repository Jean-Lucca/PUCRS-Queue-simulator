public class Evento implements Comparable<Evento> {
    protected double tempo;
    protected char tipo;

    public Evento(double tempo, char tipo) {
        this.tempo = tempo;    
        this.tipo = tipo;
    }

    public int compareTo(Evento e) {
        return Double.compare(tempo, e.tempo);
    }

    public double getTempo() {
        return tempo;
    }
    
    public int getTipo() {
        return tipo;
    }
}