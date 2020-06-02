package br.edu.faculdadedelta.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.faculdadedelta.modelo.AlunoPedro;
import br.edu.faculdadedelta.modelo.GrauInstrucaoPedro;
import br.edu.faculdadedelta.util.Conexao;

public class AlunoDaoPedro {
	
	public void incluir(AlunoPedro aluno) throws Exception {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "INSERT INTO alunos (nome, idade, datadenascimento, idgraudeinstrucao) VALUES(?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		try {
			ps.setString(1, aluno.getNome().trim());
			ps.setInt(2, aluno.getIdade());
			ps.setDate(3, new java.sql.Date(aluno.getNascimento().getTime()));
			ps.setLong(4, aluno.getGrauInstitucional().getId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			Conexao.fecharConexao(conn, ps, null);
		}
		
	}

	public void alterar(AlunoPedro aluno) throws Exception {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "UPDATE alunos SET nome=?, idade=?, datadenascimento=?, idgraudeinstrucao=? WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		try {
			ps.setString(1, aluno.getNome().trim());
			ps.setInt(2, aluno.getIdade());
			ps.setDate(3, new java.sql.Date(aluno.getNascimento().getTime()));
			ps.setLong(4, aluno.getGrauInstitucional().getId());
			ps.setLong(5, aluno.getId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			Conexao.fecharConexao(conn, ps, null);
		}
		
	}

	public void excluir(AlunoPedro aluno) throws Exception {
		Connection conn = Conexao.conectarNoBancoDeDados();
		String sql = "DELETE FROM alunos WHERE id=?";
		PreparedStatement ps = conn.prepareStatement(sql);
		
		try {
			ps.setLong(1, aluno.getId());
			ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			Conexao.fecharConexao(conn, ps, null);
		}
		
	}

	public List<AlunoPedro> listar() throws Exception{
			Connection conn = Conexao.conectarNoBancoDeDados();
			String sql = "SELECT\r\n" + 
					"a.id AS idAluno," + 
					"a.nome AS nomeAluno,\r\n" + 
					"a.idade AS idade,\r\n" + 
					"a.datadenascimento AS nascimento,\r\n" + 
					"g.id AS idGrau,\r\n" + 
					"g.nome AS nomeGrau\r\n" + 
					"FROM alunos a INNER JOIN graudeinstrucao g ON a.idgraudeinstrucao = g.id;";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<AlunoPedro> listaRetorno = new ArrayList<>();
			
			try {
				while(rs.next()) {
					AlunoPedro aluno = new AlunoPedro();
					aluno.setId(rs.getLong("idAluno"));
					aluno.setNome(rs.getString("nomeAluno").trim());
					aluno.setIdade(rs.getInt("idade"));
					aluno.setNascimento(rs.getDate("nascimento"));
					
					GrauInstrucaoPedro grau = new GrauInstrucaoPedro();
					grau.setId(rs.getLong("idGrau"));
					grau.setGrau(rs.getString("nomeGrau").trim());
					
					aluno.setGrauInstitucional(grau);
					
					listaRetorno.add(aluno);		
					
				}
		
				
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(e);
			} finally {
				Conexao.fecharConexao(conn, ps, rs);
			}
			
		 return listaRetorno;
	}
}
