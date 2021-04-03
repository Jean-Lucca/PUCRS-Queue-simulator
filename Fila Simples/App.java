public class App {
    public static void main(String[] args) {
        double[] intervaloChegada = {2,4};
        double[] intervaloSaida = {3,5};

        FilaSimples gg151 = new FilaSimples(1, 5, intervaloChegada, intervaloSaida, 3.0000, 100000, 1);
        FilaSimples gg152 = new FilaSimples(1, 5, intervaloChegada, intervaloSaida, 3.0000, 100000, 2);
        FilaSimples gg153 = new FilaSimples(1, 5, intervaloChegada, intervaloSaida, 3.0000, 100000, 3);
        FilaSimples gg154 = new FilaSimples(1, 5, intervaloChegada, intervaloSaida, 3.0000, 100000, 4);
        FilaSimples gg155 = new FilaSimples(1, 5, intervaloChegada, intervaloSaida, 3.0000, 100000, 5);

        gg151.run();
        gg152.run();
        gg153.run();
        gg154.run();
        gg155.run();

        FilaSimples gg251 = new FilaSimples(2, 5, intervaloChegada, intervaloSaida, 3.0000, 100000, 1);
        FilaSimples gg252 = new FilaSimples(2, 5, intervaloChegada, intervaloSaida, 3.0000, 100000, 2);
        FilaSimples gg253 = new FilaSimples(2, 5, intervaloChegada, intervaloSaida, 3.0000, 100000, 3);
        FilaSimples gg254 = new FilaSimples(2, 5, intervaloChegada, intervaloSaida, 3.0000, 100000, 4);
        FilaSimples gg255 = new FilaSimples(2, 5, intervaloChegada, intervaloSaida, 3.0000, 100000, 5);

        gg251.run();
        gg252.run();
        gg253.run();
        gg254.run();
        gg255.run();

        calculaMedia("G/G/1/5", 5,gg151, gg152, gg153, gg154, gg155);
        calculaMedia("G/G/2/5", 5,gg251, gg252, gg253, gg254, gg255);

        
    }

    public static void calculaMedia(String nome, int k, FilaSimples f1, FilaSimples f2, FilaSimples f3, FilaSimples f4, FilaSimples f5) {
        System.out.println("\nMedia - "+nome);
        double aux= 0;
        for(int i=0;i<=k;i++) {
            aux = 
            f1.getProbabilidade(i)+
            f2.getProbabilidade(i)+
            f3.getProbabilidade(i)+
            f4.getProbabilidade(i)+
            f5.getProbabilidade(i);
            System.out.println("fila "+i+": "+((aux)/k));
        }
    }
}