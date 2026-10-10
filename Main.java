package RMI;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String studentCode = "B23DCCN716";
        String qCode = "XNuaBbcP"; // Thay mã câu hỏi từ web
        String serverHost = "36.50.135.242";
        int port = 1099; // Cổng mặc định của RMI Registry

        try {

            Registry registry = LocateRegistry.getRegistry(serverHost, port);

            ByteService service = (ByteService) registry.lookup("RMIByteService");

            byte[] inputData = service.requestData(studentCode, qCode);

            Map<Byte, Integer> freqMap = new LinkedHashMap<>();
            for (byte b : inputData) {
                freqMap.put(b, freqMap.getOrDefault(b, 0) + 1);
            }


            byte minByte = inputData[0];
            int minCount = Integer.MAX_VALUE;

            for (Map.Entry<Byte, Integer> entry : freqMap.entrySet()) {
                if (entry.getValue() < minCount) {
                    minCount = entry.getValue();
                    minByte = entry.getKey();
                }
            }


            byte[] result = new byte[]{ minByte, (byte) minCount };


            service.submitData(studentCode, qCode, result);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}