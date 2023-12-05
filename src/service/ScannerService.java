package service;

import ui.ScannerFactory;

import java.util.Scanner;

public class ScannerService {


    public static Integer getIntInput(String message) {
        Integer result = null;
        boolean checkResult = false;
        Scanner scannerTemp = ScannerFactory.getScanner();

        while (!checkResult) {
            System.out.print(message);
            try {
                result = scannerTemp.nextInt();
            } catch (Exception e) {
                scannerTemp.nextLine();
                System.out.println("Please input integer number");
                continue;
            }

            checkResult = true;

        }
        return result;
    }

    public static String getStringInput(String message) {
        String result = "";
        boolean checkResult = false;
        Scanner scannerTemp = ScannerFactory.getScanner();
        while (!checkResult) {
            System.out.println(message);
            try {
                result = scannerTemp.next();
            } catch (Exception e) {
                scannerTemp.nextLine();
                System.out.println("Please input text");
                continue;
            }
            checkResult = true;
        }
        return result;
    }


}
