package com.applications;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProductStoreManagementSystem extends Application {

	@Override
	public void start(Stage stage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/views/LoginView.fxml"));
			Scene scene = new Scene(loader.load());

			stage.setScene(scene);
			stage.show();
			stage.setTitle("Food store Management System");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
