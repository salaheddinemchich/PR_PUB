package com.shopsense;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

import com.shopsense.dao.AdminDA;
import com.shopsense.dao.CustomerDA;
import com.shopsense.model.Admin;
import com.shopsense.model.Customer;
import com.shopsense.model.Role;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@SpringBootApplication
@EnableAsync
@Slf4j
public class ShopSenseApplication {

	@Autowired
	private AdminDA adminDA;

	@Autowired
	private CustomerDA customerDA;

	public static void main(String[] args) {
		SpringApplication.run(ShopSenseApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> {
			log.info("################ ShopSense Server is running ################");

			// Create static admin credentials
			createStaticAdmin();

			// Create static customer credentials
			createStaticCustomer();
		};
	}

	private void createStaticAdmin() {
		try {
			// Check if admin already exists
			PreparedStatement pst = db.get().prepareStatement("SELECT COUNT(*) FROM admins WHERE email = ?");
			pst.setString(1, "admin@shopsense.com");
			ResultSet rs = pst.executeQuery();
			if (rs.next() && rs.getInt(1) == 0) {
				// Admin doesn't exist, create it
				pst = db.get().prepareStatement(
						"INSERT INTO admins (name, email, password, role) VALUES (?, ?, ?, ?)");
				pst.setString(1, "Admin");
				pst.setString(2, "admin@shopsense.com");
				pst.setString(3, "admin123");
				pst.setString(4, Role.ADMIN.name());
				pst.executeUpdate();

				log.info("Static admin credentials created: admin@shopsense.com / admin123");
			} else {
				log.info("Static admin already exists");
			}
		} catch (Exception e) {
			log.error("Error creating static admin: " + e.getMessage());
		}
	}

	private void createStaticCustomer() {
		try {
			// Check if customer already exists
			PreparedStatement pst = db.get().prepareStatement("SELECT COUNT(*) FROM customers WHERE email = ?");
			pst.setString(1, "customer@shopsense.com");
			ResultSet rs = pst.executeQuery();
			if (rs.next() && rs.getInt(1) == 0) {
				// Customer doesn't exist, create it
				pst = db.get().prepareStatement(
						"INSERT INTO customers (name, email, password, role, address, status, email_verified) VALUES (?, ?, ?, ?, ?, ?, ?)");
				pst.setString(1, "Customer");
				pst.setString(2, "customer@shopsense.com");
				pst.setString(3, "customer123");
				pst.setString(4, Role.CUSTOMER.name());
				pst.setString(5, "123 Main St");
				pst.setString(6, "Active");
				pst.setBoolean(7, true);
				pst.executeUpdate();

				log.info("Static customer credentials created: customer@shopsense.com / customer123");
			} else {
				log.info("Static customer already exists");
			}
		} catch (Exception e) {
			log.error("Error creating static customer: " + e.getMessage());
		}
	}
}
