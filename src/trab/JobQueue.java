package trab;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

 public class JobQueue {
    private LinkedList<Integer> jobs = new LinkedList<>();
    private AtomicInteger processando = new AtomicInteger(0);
    private JobQueueListener listener;
    private JobQueueCompletedListener completedListener;

    public JobQueue() {
        super();
    }

    public void addJobQueueListener(JobQueueListener listener, JobQueueCompletedListener completedListener) {
        this.listener = listener;
        this.completedListener = completedListener;
    }

    public interface JobQueueListener {
        void jobQueueChanged(int newSize);
    }

    public interface JobQueueCompletedListener {
        void jobQueueCompletedChanged(int newSize);
    }

    public synchronized void queueJob(int job) {
        synchronized (this) {
            this.jobs.add(job);
            if (this.listener != null) {
                this.listener.jobQueueChanged(this.jobs.size());
            }

        }
    }

    public synchronized Integer getNextJob() {
        synchronized (this) {
            if (this.jobs.isEmpty()) {
                return null;
            }

            Integer job = this.jobs.getFirst();
            System.out.println("pegando outro job!");
            return job;
        }
    }

    public synchronized void assignJob() {
        synchronized (this) {
            this.processando.incrementAndGet();
            if (this.listener != null) {
                this.completedListener.jobQueueCompletedChanged(this.processando.get());
            }
        }
    }

    public synchronized void concludeJob() {
        synchronized (this) {
            this.processando.decrementAndGet();
            if (!this.jobs.isEmpty()) {
                this.jobs.removeFirst();
            }
            System.out.println("Concluindo o job!");
            if (this.listener != null) {
                this.completedListener.jobQueueCompletedChanged(this.jobs.size());
            } 
        }
    }


}