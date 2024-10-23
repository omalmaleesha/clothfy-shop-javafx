package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import service.ServiceFactory;
import service.SuperService;
import service.custom.OrderService;
import service.custom.ProductService;
import util.ServiceType;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;

public class AdminReportViewController implements Initializable {

    @FXML
    public PieChart pieChartfororder;

    @FXML
    private PieChart pieChart;

    @FXML
    void btnOnActionNavigateReports(ActionEvent event) {

    }

    @FXML
    void btnOnActionNavigateToEmployee(ActionEvent event) {

    }

    @FXML
    void btnOnActionNavigateToSuppliers(ActionEvent event) {

    }

    @FXML
    void btnOnActionProductViewNavigate(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDatachart();

    }
    private void loadDatachart() {
        try {
            ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
            List<Object[]> productCategoryData = service.getProductCategoryData();

            OrderService orderService = ServiceFactory.getInstance().getService(ServiceType.ORDER);
            List<Object[]> orderDistribution = orderService.getOrderDistributeData();

            for (Object[] row : orderDistribution) {
                Long userId = (Long) row[0];
                Long count = (Long) row[1];

                PieChart.Data data = new PieChart.Data("User: " + String.valueOf(userId), count);
                pieChartfororder.getData().add(data);
            }


            for (Object[] row : productCategoryData) {
                String category = (String) row[0];
                Long count = (Long) row[1];

                PieChart.Data data = new PieChart.Data(category, count);
                pieChart.getData().add(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
