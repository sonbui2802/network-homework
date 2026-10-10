package RMI;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class bai2 {
    public static void main(String[] args) {
        String qCode = "nlMmgPYn"; // Nhớ kiểm tra lại mã mới trước khi bấm Run
        String server = "36.50.135.242";
        String studentCode = "B23DCCN716";
        int port = 1099;

        try {
            Registry registry = LocateRegistry.getRegistry(server, port);

            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");


            String inputStr = service.requestCharacter(studentCode, qCode);

            int num = Integer.parseInt(inputStr.trim());


            String roman = intToRoman(num);



            service.submitCharacter(studentCode, qCode, roman);

        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    private static String intToRoman(int num) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                sb.append(symbols[i]);
                num -= values[i];
            }
        }
        return sb.toString();
    }
}