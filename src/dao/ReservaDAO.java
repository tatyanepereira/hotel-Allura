	package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import model.Reservas;

public class ReservaDAO {

	private final Connection connection;
	
	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void salvar(Reservas reserva) {
		try {
			String sql = "INSERT INTO reservas (data_entrada, data_saida, valor, forma_pagamento) VALUES (?, ?, ?, ?)";
			
			try (PreparedStatement pstm = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstm.setDate(1, reserva.getDataEntrada());
				pstm.setDate(2, reserva.getDataSaida());
				pstm.setDouble(3, reserva.getValor());
				pstm.setString(4, reserva.getFormaPagamento());
				
				pstm.execute();
				
				try (ResultSet rst = pstm.getGeneratedKeys()) {
					while (rst.next()) {
						reserva.setId(rst.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reservas> buscar() {
		List<Reservas> reservas = new ArrayList<>();
		
		try {
			String sql = "SELECT id, data_entrada, data_saida, valor, forma_pagamento FROM reservas";
			
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.execute();
				
				transformarResultSetEmReserva(reservas, pstm);
			}
			
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	public List<Reservas> buscarId(String id) {
		List<Reservas> reservas = new ArrayList<>();
		
		try {
			String sql = "SELECT id, data_entrada, data_saida, valor, forma_pagamento FROM reservas WHERE id = ?";
			
			try (PreparedStatement pstm = connection.prepareStatement(sql)) {
				pstm.setString(1, id);
				pstm.execute();
				transformarResultSetEmReserva(reservas, pstm);
			}
			
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void deletar(Integer id) {
		try (PreparedStatement pst = connection.prepareStatement("DELETE FROM reservas where id = ?")) {
			pst.setInt(1, id);
			pst.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void atualizar(Date dataEntrada, Date dataSaida, Double valor, String forma_pagamento, Integer id) {
		try (PreparedStatement pstm = connection
				.prepareStatement("UPDATE reservas SET data_entrada = ?, data_saida = ?, valor = ?, forma_pagamento = ? WHERE id = ?")) {
			pstm.setDate(1, dataEntrada);
			pstm.setDate(2, dataSaida);
			pstm.setDouble(3, valor);
			pstm.setString(4, forma_pagamento);;
			pstm.setInt(5, id);
			pstm.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void transformarResultSetEmReserva(List<Reservas> reservas, PreparedStatement pstm) throws SQLException {
		try (ResultSet rst = pstm.getResultSet()) {
			while (rst.next()) {
				Reservas reserva = new Reservas(rst.getInt(1), rst.getDate(2), rst.getDate(3), rst.getDouble(4), rst.getString(5));
				
				reservas.add(reserva);
			}
		}
	}
}
