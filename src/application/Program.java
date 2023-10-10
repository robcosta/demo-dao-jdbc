package application;

import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class Program {

	public static void main(String[] args) {
		
		SellerDao sellerDao = DaoFactory.creaateSellerDao();
		
		System.out.println("=== Test 1: seller findById ===");
		Seller seller = sellerDao.findById(1);
		System.out.println(seller);
		
		System.out.println("\n=== Test 2: seller findByDepartament ===");
		Department dpt = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartament(dpt);
		list.forEach(System.out::println);
	
		
		
		DB.closeConnection();
		
	}

}
