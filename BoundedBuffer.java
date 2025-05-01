/*
 * This class implements a thread-safe bounded buffer using synchronization primitives.
 * 
 * It provides synchronized methods for producers to add items and consumers to remove them.

 * Used by the ProducerConsumer.java simulation to demonstrate proper thread coordination.
 */


 import java.util.LinkedList;
 import java.util.Queue;
 import java.util.concurrent.Semaphore;
 
 public class BoundedBuffer {
     private final Queue<Integer> buffer = new LinkedList<>();
     private final int capacity;
     private final Semaphore empty;
     private final Semaphore full;
     private final Semaphore mutex = new Semaphore(1);
     private final long startTime = System.currentTimeMillis();
 
     public BoundedBuffer(int capacity) {
         this.capacity = capacity;
         empty = new Semaphore(capacity);
         full = new Semaphore(0);
     }
 
     private String time() {
         return "[Time: " + ((System.currentTimeMillis() - startTime) / 1000.0) + "s]";
     }
 
     public void produce(int item, int producerId) throws InterruptedException {
         System.out.println(time() + " [Producer " + producerId + "] Waiting to produce item " + item);
         empty.acquire();
         mutex.acquire();
         System.out.println(time() + " [Producer " + producerId + "] Acquired lock, adding item " + item);
         buffer.add(item);
         mutex.release();
         System.out.println(time() + " [Producer " + producerId + "] Released lock, item " + item + " produced");
         full.release();
     }
 
     public int consume(int consumerId) throws InterruptedException {
         System.out.println(time() + " [Consumer " + consumerId + "] Waiting to consume");
         full.acquire();
         mutex.acquire();
         System.out.println(time() + " [Consumer " + consumerId + "] Acquired lock, removing item");
         int item = buffer.remove();
         mutex.release();
         System.out.println(time() + " [Consumer " + consumerId + "] Released lock, consumed item " + item);
         empty.release();
         return item;
     }
 }
 