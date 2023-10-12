package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;
import model.entities.Seller;

public class Program2 {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
				
		System.out.println("=== Test 1: department findById ===");
		Department department = departmentDao.findById(3);
		System.out.println(department);
		
		System.out.println("\n=== Test 2: department findByDepartament ===");
		List<Department> list = departmentDao.findAll();
		list.forEach(System.out::println);
		
//		System.out.println("\n=== Test 4: department insert ===");
//		Department newDepartment = new Department(null, "Cleaning");
//		departmentDao.insert(newDepartment);
//		System.out.println("Inserted! New id = " + newDepartment.getId());
		
		System.out.println("\n=== Test 5: department update ===");
		department = departmentDao.findById(8);;
		department.setName("Plastics");
		departmentDao.update(department);
		System.out.println("Update completed");
		
		System.out.println("\n=== Test 6: department delete ===");
		System.out.print("Enter seller id to be deleted: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Delete completed! ");

		DB.closeConnection();
		sc.close();
		
	}

}
