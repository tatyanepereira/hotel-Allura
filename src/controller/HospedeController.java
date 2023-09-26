package controller;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import dao.HospedeDAO;
import factory.ConnectionFactory;
import model.Hospedes;

public class HospedeController {
	private final HospedeDAO hospedeDAO;
	
	public HospedeController() {
		Connection connection = new ConnectionFactory().getConnection();
		this.hospedeDAO = new HospedeDAO(connection);
	}
	
	public void salvar(Hospedes hospede) {
		this.hospedeDAO.salvar(hospede);
	}
	
	public List<Hospedes> listarHospedes() {
		return this.hospedeDAO.listarHospedes();
	}
	
	public List<Hospedes> listarHospedesId(String id) {
		return this.hospedeDAO.buscarId(id);
	}
	
	public void atualizar(String nome, String sobrenome, Date dataNascimento, String nacionalidade, String telefone, Integer idReserva, Integer id) {
		this.hospedeDAO.atualizar(nome, sobrenome, dataNascimento, nacionalidade, telefone, idReserva, id);
	}
	
	public void deletar(Integer id) {
		this.hospedeDAO.deletar(id);
	}
}
