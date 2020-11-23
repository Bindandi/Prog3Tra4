package trab;
import java.util.concurrent.atomic.AtomicBoolean;

public class JobConsumer extends Thread {
    private JobQueue jobs;
    private Integer assignedJob = null;
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    public JobConsumer(JobQueue jobs) {
        this.jobs = jobs;
    }

    @Override
    public void run() {
        while (isRunning.get()) {
            if (assignedJob == null || assignedJob == 0) {
                try {
                    assignedJob = jobs.getNextJob();
                    if (assignedJob == null) {
                        System.out.println("Nada para fazer zzz " + System.currentTimeMillis() + " " + this);
                        this.sleep(5000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                int workToDo = assignedJob;
                for (int i = assignedJob; i >= 0; i--) {
                    System.out.println("Trabalhando... Tamanho do Job " + assignedJob + ", " + System.currentTimeMillis() + ": " + this);                    
                    try {
                        this.sleep(1000);
                    } catch (InterruptedException err) {
                        err.printStackTrace();
                    }
                }
                jobs.concludeJob();
                assignedJob = null;
            }
            //testando...
        }
    }
    
    public void stopConsumer() {
        isRunning.set(false);
    }

}
