package controller;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import dto.OrderDetails;
import dto.Orders;
import dto.Products;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import org.hibernate.query.Order;
import service.ServiceFactory;
import service.SuperService;
import service.custom.OrderService;
import service.custom.ProductService;
import util.ServiceType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderBillController  {

    @FXML
    private GridPane gridToBill;

    @FXML
    private Label lblBalance;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDiscount;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblTime;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Label txtEmpID;

    @FXML
    private Label txtOrderId;

    @FXML
    private TextField txtpaidMoney;

    private Orders orders;

    private List<OrderDetails> OrderDetail;

    @FXML
    void btnOnActionPlaceOrder(ActionEvent event) {

    }

    @FXML
    void btnOnActionPrintBill(ActionEvent event) {
        generatePDFBill(orders,OrderDetail);
    }

    private void generatePDFBill(Orders orders, List<OrderDetails> orderDetails) {
        String filePath = "report.pdf";
        Document document = new Document();
        ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph("Invoice Report"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Order ID: " + orders.getOrderID()));
            document.add(new Paragraph("Customer Name: " + orders.getEmployeeID()));
            document.add(new Paragraph("Order Date: " + orders.getOrderDate()));
            document.add(new Paragraph("Total Amount: $" + orders.getTotalCost()));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Order Details:"));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.addCell("Product Name");
            table.addCell("Quantity");
            table.addCell("Unit Price");
            table.addCell("Total Price");

            for (OrderDetails detail : orderDetails) {
                Products product = service.findProductById(detail.getProductID());
                String productName = (product != null) ? product.getName() : "Unknown Product";

                table.addCell(productName);
                table.addCell(String.valueOf(detail.getQuantity()));
                table.addCell(String.format("$%.2f", product.getUnitPrice()));
                table.addCell(String.format("$%.2f", detail.getQuantity()*product.getUnitPrice()));
            }

            document.add(table);
            document.close();
            System.out.println("PDF created successfully: " + filePath);
            new Alert(Alert.AlertType.INFORMATION,"Done").show();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {

            if (document.isOpen()) {
                document.close();
            }
        }
    }


    @FXML
    void btnOnActionProductView(ActionEvent event) {

    }

    public void setOrderID(String text,Orders orders,List<OrderDetails> orderDetails) {
        this.orders = orders;
        this.OrderDetail = orderDetails;
        txtOrderId.setText(text);
        loadBillCart(orders,orderDetails);
    }
    private void loadBillCart(Orders orders,List<OrderDetails> orderDetails) {
        ProductService service = ServiceFactory.getInstance().getService(ServiceType.PRODUCT);
        gridToBill.getChildren().clear();

        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetails orderDetail = orderDetails.get(i);
            Products product = service.findProductById(orderDetail.getProductID());
            HBox hbox = new HBox(10);
            hbox.setAlignment(Pos.CENTER_LEFT);
            hbox.setPadding(new Insets(10, 10, 10, 10));

            Label productName = new Label(product.getName());
            productName.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

            Label qtyLabel = new Label("Qty: " + orderDetail.getQuantity());
            qtyLabel.setStyle("-fx-font-size: 14px;");

            Label priceLabel = new Label("Price: $" + product.getUnitPrice());
            priceLabel.setStyle("-fx-font-size: 14px;");

            Label totalLabel = new Label("Total: $" + (orderDetail.getQuantity() * product.getUnitPrice()));
            totalLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            hbox.getChildren().addAll(productName, qtyLabel, priceLabel, totalLabel);

            gridToBill.add(hbox, 0, i);
        }

        scrollPane.setContent(gridToBill);
    }
}

