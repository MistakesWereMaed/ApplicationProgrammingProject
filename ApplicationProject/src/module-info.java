module ApplicationProject {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
	opens application.controller to javafx.fxml;
	
	exports application.controller;
}