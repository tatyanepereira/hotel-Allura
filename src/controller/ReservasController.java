package controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import dao.ReservaDAO;
import factory.ConnectionFactory;
import model.Reservas;

public class ReservasController {
	private final ReservaDAO reservaDAO;
	
	public ReservasController() {
		Connection connection = new ConnectionFactory().getConnection();
		this.reservaDAO = new ReservaDAO(connection);
	}
	
	public void salvar(Reservas reserva) {
		this.reservaDAO.salvar(reserva);
	}
	
	public List<Reservas> buscar() {
		return this.reservaDAO.buscar();
	}
	
	public List<Reservas> buscarId(String id) {
		return this.reservaDAO.buscarId(id);
	}
	
	public void atualizar(Date dataEntrada, Date dataSaida, Double valor, String formaPagamento, Integer id) {
		this.reservaDAO.atualizar(dataEntrada, dataSaida, valor, formaPagamento, id);
	}
	
	public void deletar(Integer id) {
		this.reservaDAO.deletar(id);
	}
}
