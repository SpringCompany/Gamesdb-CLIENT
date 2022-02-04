module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.httpcomponents.client5.httpclient5;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.json;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}