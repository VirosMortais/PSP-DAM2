// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        //Process
        /*String[] cmd = {"ls", "-l"};
        String line = "";
        ProcessBuilder pb = new ProcessBuilder(cmd);

        try {
            Process p = pb.start();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(p.getInputStream()));
            System.out.println("Process output:");
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            System.err.println("Exception:" + e.getMessage());
        }*/

        //Thread
        /*CustomThread t1 = new CustomThread(1);
        t1.start();
        CustomThread t2 = new CustomThread(2);
        t2.start();*/

        Contenedor c = new Contenedor();
        Productor producer = new Productor(c);
        Productor producer2 = new Productor(c);
        Productor producer3 = new Productor(c);
        Productor producer4 = new Productor(c);
        Consumidor consumer = new Consumidor(c);
        Consumidor consumer2 = new Consumidor(c);
        Consumidor consumer3 = new Consumidor(c);
        Consumidor consumer4 = new Consumidor(c);
        producer.start();

        consumer.start();

        consumer2.start();
        consumer3.start();

        producer2.start();
        producer3.start();


        consumer4.start();
        producer4.start();
    }
}