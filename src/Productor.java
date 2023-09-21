public class Productor extends Thread{
    private Contenedor contenedor;
    private final int id;

    public Productor(Contenedor c){
        this.contenedor = c;
        this.id = (int)(Math.random()*100);
    }

    public void run(){
        for (int i = 0; i < 10; i++){
            contenedor.put(i);
            System.out.println("Productor,"+id+" put" + i);
            try{
                sleep((int)(Math.random()*100));
            }catch (InterruptedException e){
                System.out.println("[ERROR]" + e);
            }
        }
    }
}
