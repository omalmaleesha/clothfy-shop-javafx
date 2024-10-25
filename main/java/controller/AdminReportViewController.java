package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import service.ServiceFactory;
import service.SuperService;
import service.custom.OrderService;
import service.custom.ProductService;
import util.ServiceType;

import java.io.IOException;
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
    public BarChart<String, Number> barchartForSupplierProducts;

    @FXML
    private PieChart pieChart;

    @FXML
    void btnOnActionNavigateReports(ActionEvent event) {
        Stage primaryStage = (Stage) pieChart.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminReports.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void btnOnActionNavigateToEmployee(ActionEvent event) {
        Stage primaryStage = (Stage) pieChart.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminEmplyee.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void btnOnActionNavigateToSuppliers(ActionEvent event) {
        Stage primaryStage = (Stage) pieChart.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminSupplier.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @FXML
    void btnOnActionProductViewNavigate(ActionEvent event) {
        Stage primaryStage = (Stage) pieChart.getScene().getWindow();
        primaryStage.hide();
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/adminView.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDatachart();
        loadBarChart();

    }

    private void loadBarChart() {
        ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        List<Object[]> productCategoryData = service.getProductQuantitiesBySupplier();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Product Quantities by Supplier");

        for (Object[] row : productCategoryData) {
            String supplierID = (String) row[0];
            Double totalQty = (Double) row[1];

            series.getData().add(new XYChart.Data<>(supplierID.toString(), totalQty));
        }

        barchartForSupplierProducts.getData().add(series);
    }

    private void loadDatachart() {
        try {
            ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
            List<Object[]> productCategoryData = service.getProductCategoryData();

            OrderService orderService = ServiceFactory.getInstance().getService(ServiceType.ORDER);
            List<Object[]> orderDistribution = orderService.getOrderDistributeData();

            for (Object[] row : orderDistribution) {
                Integer userId = (Integer) row[0];
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
