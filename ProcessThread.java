/*
 * Run this file to start the simulation
 * 
 * This file uses processes calling Process.java 
 * Convert each process into a thread.
 * Simulates the CPU burst using Thread.sleep().
 */

import java.util.ArrayList;
import java.util.List;

public class ProcessThread extends Thread{
    Process process;
    long startTime;

    public ProcessThread(Process process, long startTime) {
        this.process = process;
        this.startTime = startTime;
    }

    public void run(){
        try{
            long waitTime = process.arrival_time * 1000;
            long currentTime = System.currentTimeMillis() - startTime;
            if (waitTime > currentTime){
                Thread.sleep(waitTime - currentTime);
            }
            System.out.println("[Time: " + ((System.currentTimeMillis() - startTime) / 1000) + "s] Process " + process.pid + " started. (Burst time: " + process.burst_time + "s, Priority: " + process.priority + ")");
            Thread.sleep(process.burst_time * 1000);
            System.out.println("[Time: " + ((System.currentTimeMillis() - startTime) / 1000) + "s] Process " + process.pid + " finished.");
        }
        catch(InterruptedException e){
            System.out.println("[Time: " + ((System.currentTimeMillis() - startTime) / 1000) + "s] Process " + process.pid + "was interrupted.");
        }
    }
    public static void main(String[] args){
        ReadFile readFile1 = new ReadFile();
        String fileName = readFile1.getSelectedFile();
        List<Process> processes = readFile1.readFile(fileName);

        List<ProcessThread> threads = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        System.out.println("\nSTART: Process simulation started...");

        for(Process p : processes){
            ProcessThread thread = new ProcessThread(p, startTime);
            threads.add(thread);
            thread.start();
        }

        for(ProcessThread thread : threads){
            try{
                thread.join();
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        System.out.println("\nFINISH: All processes completed.");
    }
}
