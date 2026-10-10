package RMI;

import RMI.DataService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class bai3 {
    public static void main(String[] args) {
        String studentCode = "B23DCCN716";
        String qCode = "8IGzhk0W";
        String serverIp = "36.50.135.242";
        int port = 1099;

        try {

            Registry registry = LocateRegistry.getRegistry(serverIp, port);
            DataService service = (DataService) registry.lookup("RMIDataService");
            Object rawData = service.requestData(studentCode, qCode);
            List<Integer> arr = new ArrayList<>();
            if (rawData instanceof int[]) {
                for (int x : (int[]) rawData) arr.add(x);
            } else if (rawData instanceof Integer[]) {
                for (int x : (Integer[]) rawData) arr.add(x);
            } else if (rawData instanceof List<?>) {
                for (Object obj : (List<?>) rawData) {
                    arr.add((Integer) obj);
                }
            }
            List<Integer> peaks = new ArrayList<>();
            int n = arr.size();
            if (n == 1) {
                peaks.add(1);
            } else if (n > 1) {
                if (arr.get(0) > arr.get(1)) {
                    peaks.add(1);
                }
                for (int i = 1; i < n - 1; i++) {
                    if (arr.get(i) > arr.get(i - 1) && arr.get(i) > arr.get(i + 1)) {
                        peaks.add(i + 1);
                    }
                }


                if (arr.get(n - 1) > arr.get(n - 2)) {
                    peaks.add(n);
                }
            }




            service.submitData(studentCode, qCode, peaks);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}