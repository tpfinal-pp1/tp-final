package com.TpFinal.ejemploHibernate.dao;

import com.TpFinal.ejemploHibernate.conexion.ConexionHibernate;
import com.TpFinal.ejemploHibernate.dto.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class EmployeeDAO extends DAOImpl<Employee> {

	// De esta forma creamos un constructor limpio, pasandole
	// Al DAO generico la clase, parametro que necesita para
	// las queries.
	public EmployeeDAO() {
		super(Employee.class);
	}

	public void listEmployees() {
		Session session = ConexionHibernate.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<Employee> employees = session.createQuery("FROM Employee", Employee.class).list();

			employees.forEach(e -> {
				System.out.print("First Name: " + e.getFirstName());
				System.out.print("  Last Name: " + e.getLastName());
				System.out.println("  Salary: " + e.getSalary());
			});

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public static void main(String[] args) {
		EmployeeDAO dao = new EmployeeDAO();
		dao.listEmployees();
		// Podemos usar los metodos del dao generico sin tener que implementar nada más.
		dao.create(new Employee("Juan", "Perez", 12300));
		dao.readAll().forEach(System.out::println);
		Employee e = dao.findById(1);
		System.out.println("\n-----------\nPersona con id 1: " + e);
		ConexionHibernate.close();
	}
}
