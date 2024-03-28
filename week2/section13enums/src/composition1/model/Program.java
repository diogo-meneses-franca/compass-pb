package composition1.model;


import composition1.model.entities.Department;
import composition1.model.entities.HourContract;
import composition1.model.entities.Worker;
import composition1.model.enums.WorkerLevel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws ParseException {

        Scanner sc = new Scanner(System.in);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


        System.out.println("Enter worker's data: ");
        System.out.println("Department: ");
        String departmentName = sc.nextLine();
        System.out.println("Name: ");
        String workerName = sc.nextLine();
        System.out.println("Level: ");
        String workerLevel = sc.nextLine();
        System.out.println("Base salary: ");
        Double baseSalary = sc.nextDouble();
        Worker worker = new Worker(
                workerName,
                WorkerLevel.valueOf(workerLevel),
                baseSalary,
                new Department(departmentName)
        );

        System.out.println("How many contracts to this worker?");
        int contracts = sc.nextInt();
        for(int i = 0; i < contracts; i++){
            System.out.println("Enter the contract data: ");
            System.out.print("Date (DD/MM/YYYY): ");
            Date contractDate = sdf.parse(sc.next());
            System.out.print("Value per hour: ");
            double valuePerHour = sc.nextDouble();
            System.out.print("Duration: ");
            int hour = sc.nextInt();
            HourContract contract = new HourContract(contractDate, valuePerHour, hour);
            worker.addContract(contract);
        }

        System.out.println();
        System.out.print("Enter month and year to calculate income (MM/YYYY): ");
        String monthAndYear = sc.next();
        int month = Integer.parseInt(monthAndYear.substring(0,2));
        int year = Integer.parseInt(monthAndYear.substring(3));
        System.out.println("Name: " + worker.getName());
        System.out.println("Department: " + worker.getDepartment().getName());
        System.out.println("Income for " + monthAndYear + " : " + String.format("%.2f", worker.income(month,year)));


    }
}
