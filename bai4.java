import RMI.ObjectService;
import RMI.TicketSla;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class bai4 {
    public static void main(String[] args) {
        String studentCode = "B23DCCN716";
        String qCode = "rNeJlKcL";         // Thay bằng mã câu hỏi thực tế của bạn
        String serverIp = "36.50.135.242";   // IP máy chủ thi
        int port = 1099;

        try {
            Registry registry = LocateRegistry.getRegistry(serverIp, port);
            ObjectService service = (ObjectService) registry.lookup("RMIObjectService");

            TicketSla ticket = (TicketSla) service.requestObject(studentCode, qCode);


            String priority = ticket.getPriority();
            int hours = ticket.getOpenedHoursAgo();


            boolean isBreached = false;
            switch (priority) {
                case "CRITICAL":
                    isBreached = (hours > 2);
                    break;
                case "HIGH":
                    isBreached = (hours > 8);
                    break;
                case "MEDIUM":
                    isBreached = (hours > 24);
                    break;
                case "LOW":
                    isBreached = (hours > 72);
                    break;
            }
            ticket.setBreached(isBreached);

            // 4. Thiết lập action
            if (!isBreached) {
                ticket.setAction("MONITOR");
            } else {
                if ("CRITICAL".equals(priority) || hours > 96) {
                    ticket.setAction("ESCALATE_L2");
                } else {
                    ticket.setAction("ESCALATE_L1");
                }
            }


            service.submitObject(studentCode, qCode, ticket);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}