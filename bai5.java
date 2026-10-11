package ws;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class bai5{
    public static void main(String[] args) {
        String studentCode = "B23DCCN716";
        String qCode = "fE6HGOnV";
        String endpoint = "http://36.50.135.242:2221/ObjectService?wsdl";

        try {
            URL url = new URL(endpoint);
            ObjectService_Service service = new ObjectService_Service(url);
            ObjectService port = service.getObjectServicePort();

            List<EmployeeY> list = port.requestListEmployeeY(studentCode, qCode);

            if (list != null && !list.isEmpty()) {
                List<EmployeeY> sortedList = new ArrayList<>(list);

                Collections.sort(sortedList, new Comparator<EmployeeY>() {
                    @Override
                    public int compare(EmployeeY o1, EmployeeY o2) {
                        return o1.getStartDate().toGregorianCalendar().getTime()
                                .compareTo(o2.getStartDate().toGregorianCalendar().getTime());
                    }
                });

                port.submitListEmployeeY(studentCode, qCode, sortedList);
            }

            System.out.println("OK");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}