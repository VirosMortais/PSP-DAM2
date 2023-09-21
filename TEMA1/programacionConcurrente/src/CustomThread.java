public class CustomThread extends Thread{
    long id;
    CustomThread(long id) {
        this.id = id;
    }

    public void run() {
        System.out.printf("Hola, soy %d\n", this.id);
    }

}
