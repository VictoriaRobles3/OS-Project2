/*
 * This file reads text files and calls Process.java to convert data into processes.
 * The getSelectedFile method returns a string with the selected file by the user. This string can be 
 * used in other files.
 * Menu-driven approach and invalid input handling implemented.
 */

 import java.io.*;
 import java.util.*;
 
 class ReadFile {
 
     public List<Process> readFile(String fileName){
         List<Process> processList = new ArrayList<>();
 
         if(fileName.endsWith(".txt")){
             System.out.println("\nReading " + fileName + " file... ");
             try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
                 String line;
                 br.readLine();
                 while((line = br.readLine()) != null){
                     String[] fields = line.trim().split("\\s+");
                     if(fields.length == 4){
                         int pid = Integer.parseInt(fields[0]);
                         int arrival_time = Integer.parseInt(fields[1]);
                         int burst_time = Integer.parseInt(fields[2]);
                         int priority = Integer.parseInt(fields[3]);
 
                         Process process = new Process(pid, arrival_time, burst_time, priority);
                         processList.add(process);
                     } 
                     else{
                         System.err.println("Invalid format: " + line);
                     }
                 }
             } catch (IOException e){
                 e.printStackTrace();
             }
         }
         else{
             System.err.println("Cannot read file. File must be in txt format.");
             System.exit(0);
         }
         return processList;
     }
     public static String getSelectedFile(){
         String fileName = "";
         Scanner sc = new Scanner(System.in);
         while(true){
             System.out.println("Hello, select the file number and press Enter: ");
             System.out.println("1. processes.txt");
             System.out.println("2. process.ppt");
             System.out.println("3. sampleData2.txt");
             System.out.println("4. Exit");
 
             if(sc.hasNextInt()){
                 int input = sc.nextInt();
                 switch(input){
                     case 1:
                         fileName = "processes.txt";
                         break;
                     case 2: 
                         fileName = "process.ppt";
                         break;
                     case 3: 
                         fileName = "sampleData2.txt";
                         break;
                     case 4:
                         System.out.println("Terminating Program...");
                         sc.close();
                         System.exit(0);
                     default:
                         System.out.println("Not a valid option. Please try again.");
                         continue;
                 }
                 break;
             }
             else{
                 System.out.println("\nInvalid input. You must enter a number.");
                 sc.next();
             }
         }
        sc.close();
        return fileName;
     }
     public static void main (String[] args){
         ReadFile read1 = new ReadFile();
         List<Process> processes = read1.readFile(getSelectedFile());
 
         for(Process p: processes){
             System.out.println(p);
        }
     }
 }