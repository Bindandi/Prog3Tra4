package trab;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class JobProducer extends Thread {
    private final JobQueue jobs;
    private AtomicBoolean isRunning = new AtomicBoolean(true);

    public JobProducer(JobQueue jobs) {
        this.jobs = jobs;
    }

    @Override
    public void run() {
            try {
                System.out.println("Adicionando uma atividade, " + this );
                this.jobs.queueJob(1);
            } catch (Exception err) {
                System.out.println("Thread interoompida, " + this);
            }
        System.out.println("parando a thread, " + this);
        this.interrupt();
    }

}
