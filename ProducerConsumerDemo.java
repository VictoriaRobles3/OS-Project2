/*
 * Run this file to start the Producer-Consumer simulation.
 * 
 * This program solves the classic Producer-Consumer synchronization problem.
 * It creates multiple producer and consumer threads that operate on a shared buffer.
 * 
 * Synchronization is handled using semaphores and a mutex lock to:
 * - Prevent producers from overfilling the buffer.
 * - Prevent consumers from consuming from an empty buffer.
 * - Ensure mutual exclusion when accessing the shared resource.
 * 
 * Output includes thread actions with timestamps to observe proper coordination.
 */

 public class ProducerConsumerDemo {
    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer(5);
        Thread[] producers = new Thread[2];
        Thread[] consumers = new Thread[3];

      
        for (int i = 0; i < 2; i++) {
            int producerId = i + 1;
            producers[i] = new Thread(() -> {
                for (int j = 1; j <= 3; j++) {
                    try {
                        buffer.produce(j, producerId);
                        Thread.sleep((int)(Math.random() * 1000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Producer-" + producerId);
            producers[i].start();
        }

     
        for (int i = 0; i < 3; i++) {
            int consumerId = i + 1;
            consumers[i] = new Thread(() -> {
                for (int j = 0; j < 2; j++) {
                    try {
                        buffer.consume(consumerId);
                        Thread.sleep((int)(Math.random() * 1200));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                }
            }, "Consumer-" + consumerId);
            consumers[i].start();
        }

      
        for (Thread t : producers) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        for (Thread t : consumers) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("[Main] All producers and consumers finished.");
    }
}
