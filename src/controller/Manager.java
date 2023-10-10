package controller;

import model.History;
import model.Worker;
import view.Validate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class Manager {

    //show menu
    public static int menu() {
        System.out.println("1. Add worker.");
        System.out.println("2. Increase salary for worker.");
        System.out.println("3. Decrease for worker");
        System.out.println("4. Show adjusted salary worker information");
        System.out.println("5. Exist");
        System.out.print("Enter your choice: ");
        int choice = Validate.checkInputIntLimit(1, 5);
        return choice;
    }

    //add
    public static void addWorker(ArrayList<Worker> workerList) {
        System.out.print("Enter code: ");
        String id = Validate.checkInputString();
        System.out.print("Enter name: ");
        String name = Validate.checkInputString();
        System.out.print("Enter age: ");
        int age = Validate.checkInputIntLimit(18, 50);
        System.out.print("Enter salary: ");
        int salary = Validate.checkInputSalary();
        System.out.print("Enter work location: ");
        String workLocation = Validate.checkInputString();
        if (!Validate.checkWorkerExist(workerList, id, name, age, salary, workLocation)) {
            System.err.println("Duplicate.");
        } else {
            workerList.add(new Worker(id, name, age, salary, workLocation));
            System.err.println("Add success.");
        }
    }

    // user increase salary for worker
    public static void changeSalary(ArrayList<Worker> workerList, ArrayList<History> historyList, int status) {
        if (workerList.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.print("Enter code: ");
        String id = Validate.checkInputString();
        Worker worker = getWorkerByCode(workerList, id);
        if (worker == null) {
            System.err.println("Not exist worker.");
        } else {
            int salaryCurrent = worker.getSalary();
            int salaryUpdate;
            //check user want to update salary
            if (status == 1) {
                System.out.print("Enter salary: ");
                //loop until user input salary update > salary current
                while (true) {     
                    salaryUpdate = Validate.checkInputSalary();
                    //check user input salary update > salary current
                    if (salaryUpdate <= salaryCurrent) {
                        System.err.println("Must be greater than current salary.");
                        System.out.print("Enter again: ");
                    } else {
                        break;
                    }
                }
                historyList.add(new History("UP", getCurrentDate(), worker.getId(),
                        worker.getName(), worker.getAge(), salaryUpdate,
                        worker.getWorkLocation()));
            } else {
                System.out.print("Enter salary: ");
                //loop until user input salary update < salary current
                while (true) {
                    salaryUpdate = Validate.checkInputSalary();
                    //check user input salary update < salary current
                    if (salaryUpdate >= salaryCurrent) {
                        System.err.println("Must be smaller than current salary.");
                        System.out.print("Enter again: ");
                    } else {
                        break;
                    }
                }
                historyList.add(new History("DOWN", getCurrentDate(), worker.getId(),
                        worker.getName(), worker.getAge(), salaryUpdate,
                        worker.getWorkLocation()));
            }
            worker.setSalary(salaryUpdate);
            System.err.println("Update success");
        }
    }

    //allow user print history
    public static void printListHistory(ArrayList<History> historyList) {
        if (historyList.isEmpty()) {
            System.err.println("List empty.");
            return;
        }
        System.out.printf("%-5s%-15s%-5s%-10s%-10s%-20s\n", "Code", "Name", "Age",
                "Salary", "Status", "Date");
        Collections.sort(historyList);
        //print history from first to last list
        for (History history : historyList) {
            printHistory(history);
        }
    }

    //get worker by code
    public static Worker getWorkerByCode(ArrayList<Worker> workerList, String id) {
        for (Worker worker : workerList) {
            if (id.equalsIgnoreCase(worker.getId())) {
                return worker;
            }
        }
        return null;
    }

    //ngày hiện tại
    public static String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    //print history
    public static void printHistory(History history) {
        System.out.printf("%-5s%-15s%-5d%-10d%-10s%-20s\n", history.getId(),
                history.getName(), history.getAge(), history.getSalary(),
                history.getStatus(), history.getDate());
    }
}