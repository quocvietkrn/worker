package view;

import java.util.ArrayList;

import controller.Manager;
import model.History;
import model.Worker;

public class Main {

    public static void main(String[] args) {
        ArrayList<Worker> workerList = new ArrayList<>();
        ArrayList<History> historyList = new ArrayList<>();
        //loop until user want to exit
        while (true) {
            int choice = Manager.menu();
            switch (choice) {
                case 1:
                    Manager.addWorker(workerList);
                    break;
                case 2:
                    Manager.changeSalary(workerList, historyList, 1);
                    break;
                case 3:
                    Manager.changeSalary(workerList, historyList, 2);
                    break;
                case 4:
                    Manager.printListHistory(historyList);
                    break;
                case 5:
                    return;
            }
        }
    }
}
