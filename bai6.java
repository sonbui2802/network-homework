package ws;

public class bai6 {
    public static void main(String[] args) {
        String studentCode = "B23DCCN716";
        String qCode = "FxvC7Mef";
        String endpoint = "http://36.50.135.242:2221/ObjectService?wsdl";

        try {
            java.net.URL url = new java.net.URL(endpoint);
            ObjectService_Service service = new ObjectService_Service(url);
            ObjectService port = service.getObjectServicePort();

            java.util.List<Customer> list = port.requestListCustomer(studentCode, qCode);
            java.util.List<Customer> filteredList = new java.util.ArrayList<>();

            if (list != null) {
                for (Customer c : list) {
                    if (c.getTotalSpent() > 5000 && c.getPurchaseCount() >= 5) {
                        filteredList.add(c);
                    }
                }
            }

            port.submitListCustomer(studentCode, qCode, filteredList);
            System.out.println("OK");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}