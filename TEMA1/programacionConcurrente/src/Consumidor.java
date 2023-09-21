public class Consumidor extends Thread{

    private Contenedor contenedor;
    private final int id;
    public Consumidor(Contenedor c){
        contenedor = c;
        this.id = (int)(Math.random()*100);
    }

    public void run(){
        int value = 0;
        for(int i = 0; i < 10; i++){
            value = contenedor.get();
            System.out.println("Consumidor,"+id+" get" + value);
        }
    }
}
