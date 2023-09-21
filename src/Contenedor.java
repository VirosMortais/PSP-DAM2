public class Contenedor {
    private int dato;
    private boolean hayDato = false;

    public synchronized int get() {
        while(!hayDato){
            try {
                wait();
            }catch (InterruptedException e){
                System.out.println("[ERROR] " + e);
            }
        }

        hayDato = false;
        notifyAll();
        return dato;
    }

    public synchronized void put(int valor){
        while (hayDato){
            try {
                System.out.println("Esperando a que se consuma el dato.");
                wait();
            }catch (InterruptedException e){
                System.out.println("[ERROR] " + e);
            }
        }
        dato = valor;
        hayDato = true;
        System.out.println("Notificado" + dato);
        notifyAll();
    }
}
