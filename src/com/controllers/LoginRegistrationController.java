package com.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.dao.CustomerDAO;
import com.helpers.classes.PasswordAuthenticator;
import com.models.Customers;
import com.util.HibernateUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginRegistrationController implements Initializable {
	@FXML
	private TextField answerTextfield;

	@FXML
	private Button btnLogin;

	@FXML
	private Button btnLoginAccountLink;

	@FXML
	private Button btnRegister;

	@FXML
	private PasswordField confirmPasswordTextfield;

	@FXML
	private Button createAccountBtnLink;

	@FXML
	private Hyperlink linkForgotpassword;

	@FXML
	private AnchorPane loginForm;

	@FXML
	private TextField passwordTextfield;

	@FXML
	private PasswordField passwordTextfieldRegisterform;

	@FXML
	private AnchorPane registerForm;

	@FXML
	private ComboBox<String> selectorQuesiton;

	@FXML
	private TextField usernameTextfield;

	@FXML
	private TextField emailTextfiled;
	@FXML
	private TextField usernameTextfieldRegisterForm;
	private HibernateUtil hibernateConnection;
	private PasswordAuthenticator passwordAuthenicator;
	private Alert alertMessage;

	private String[] questionList = { "What is your favorite color?", "What is your favorite food?",
			"What is your birth date", "What is your favorite movie?", "Where would you like to travel?",
			"What is your dream job?", "Do you have any pets?" };

	public void addAllTheQuestionsFromArrayToComboBox() {
		List<String> listQ = new ArrayList<String>();
		for (String data : questionList) {
			listQ.add(data);
		}

		ObservableList<String> listData = FXCollections.observableArrayList(listQ);
		selectorQuesiton.setItems(listData);
	}

	public void switchForm(ActionEvent event) {

		if (event.getSource() == createAccountBtnLink) {

			loginForm.setVisible(false);
			registerForm.setVisible(true);

		} else if (event.getSource() == btnLoginAccountLink) {
			loginForm.setVisible(true);
			registerForm.setVisible(false);
		}
	}

	public void registrationBtn() {
		String username = usernameTextfieldRegisterForm.getText();
		String password = passwordTextfieldRegisterform.getText();
		String question = selectorQuesiton.getSelectionModel().getSelectedItem();
		String email = emailTextfiled.getText();
		String answer = answerTextfield.getText();
		if (username.isEmpty() || password.isEmpty() || answer.isEmpty() || email.isEmpty()) {
			alertMessage = new Alert(AlertType.ERROR);
			showMessage("Error Message", null, "Please fill all blank fields");

		} else if (!password.equals(confirmPasswordTextfield.getText())) {
			alertMessage = new Alert(AlertType.ERROR);
			showMessage("Not equal passwords", null, "Please make the right confirmation of the password");

		} else {
			String hashedPassword = passwordAuthenicator.hash(password);
			Customers newCustomerToAdd = new Customers(username, email, answer, hashedPassword, question);
			CustomerDAO<Customers> customerDAO = new CustomerDAO<Customers>(hibernateConnection.getEntityManager());

			if (customerDAO.getCustomerByEmail(email) != null) {
				alertMessage = new Alert(AlertType.ERROR);

				showMessage("Error Message", null, "Such an email exists");

			} else {

				if (customerDAO.create(newCustomerToAdd) != null) {
					alertMessage = new Alert(AlertType.INFORMATION);

					showMessage("Information Message", null, "Successfully reigistered!");

				} else {
					alertMessage = new Alert(AlertType.ERROR);

					showMessage("Information Message", null, "Something goes wrong..");
				}
				makeTheTextFieldsEmptyOfRegisterForm();

				loginForm.setVisible(true);
				registerForm.setVisible(false);
			}
		}
	}

	public void showMessage(String title, String headerText, String contentText) {
		alertMessage.setTitle(title);
		alertMessage.setHeaderText(headerText);
		alertMessage.setContentText(contentText);
		alertMessage.showAndWait();
	}

	public void makeTheTextFieldsEmptyOfRegisterForm() {
		usernameTextfieldRegisterForm.setText("");
		passwordTextfieldRegisterform.setText("");
		emailTextfiled.setText("");
		answerTextfield.setText("");
		selectorQuesiton.getSelectionModel().clearSelection();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		addAllTheQuestionsFromArrayToComboBox();
		hibernateConnection = new HibernateUtil();
		passwordAuthenicator = new PasswordAuthenticator();
		// Set destroy when we exist this application
		Platform.runLater(() -> {
			Stage stage = (Stage) loginForm.getScene().getWindow();
			stage.setOnCloseRequest(event -> {
				if (hibernateConnection != null) {
					hibernateConnection.destroy();
				}
			});
		});

	}
}
