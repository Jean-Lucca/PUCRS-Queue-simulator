public class Par<Fila, Float> implements Comparable<Par>{
    private Fila fila;
    private float prob;

    public Par(Fila fila, float prob) {
        this.fila = fila;
        this.prob = prob;
    }

    public Fila getFila() {
        return fila;
    }

    public float getProb() {
        return prob;
    }

    public void setFila(Fila fila) {
        this.fila = fila;
    }

    public void setProb(float prob) {
        this.prob = prob;
    }

    @Override
    public int compareTo(Par o) {
        return (int) (o.getProb() - this.prob);
    }
}